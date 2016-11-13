package sample.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.auth.SampleAppGrantedAuthority;
import sample.mapper.RoleMapper;
import sample.mapper.TaskMapper;
import sample.mapper.UserMapper;
import sample.mapper.UserRoleMapper;
import sample.record.LimitOffsetClause;
import sample.record.OrderByClause;
import sample.record.Role;
import sample.vo.Paginated;
import sample.vo.Pagination;
import sample.vo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User findOneById(final int id) {
		final sample.record.User ru = userMapper.selectOneById(id);
		if (ru == null) {
			return null;
		}
		return User.fromRecord(ru, findAuthoritiesByUserId(ru.getId()));
	}
	
	public User findOneByName(final String name) {
		final sample.record.User ru = userMapper.selectOneByName(name);
		if (ru == null) {
			return null;
		}
		return User.fromRecord(ru, findAuthoritiesByUserId(ru.getId()));
	}
	
	public Paginated<User> findAll(final Pagination p) {
		final List<sample.record.User> rus = userMapper
				.selectAll(OrderByClause.of("name"),
						LimitOffsetClause.of(p));
		final int c = userMapper.selectCountAll();
		
		final List<User> vus = new LinkedList<User>();
		for (final sample.record.User ru : rus) {
			vus.add(User.fromRecord(ru, findAuthoritiesByUserId(ru.getId())));
		}
		
		return p.bindTotalCount(c)
				.andFetchedItems(vus);
	}
	
	@Transactional
	public void register(final User vu) {
		vu.setId(userMapper.selectSequenceNextVal());
		
		final sample.record.User ru = vu.toRecord(passwordEncoder
				.encode(new String(vu.getRawPassword())));
		
		userMapper.insert(ru);
		
		for (final SampleAppGrantedAuthority va : vu.getAuthorities()) {
			final Role rr = roleMapper.selectOneByName(va.name());
			userRoleMapper.insert(vu.getId(), rr.getId());
		}
	}
	
	@Transactional
	public void modify(final User vu) {
		if (vu.getId() == 0) {
			throw new IllegalArgumentException("Target not found.");
		}
		
		final sample.record.User ru = vu.toRecord(passwordEncoder
				.encode(new String(vu.getRawPassword())));
		
		if (userMapper.update(ru) == 0) {
			throw new IllegalArgumentException("Target not found.");
		}
		userRoleMapper.deleteByUserId(ru.getId());
		
		for (final SampleAppGrantedAuthority va : vu.getAuthorities()) {
			final Role rr = roleMapper.selectOneByName(va.name());
			userRoleMapper.insert(vu.getId(), rr.getId());
		}
	}
	
	@Transactional
	public void removeById(final int id) {
		userRoleMapper.deleteByUserId(id);
		taskMapper.deleteByUserId(id);
		if (id == 0 || userMapper.deleteById(id) == 0) {
			throw new IllegalArgumentException("Target not found.");
		}
	}
	
	private List<SampleAppGrantedAuthority> findAuthoritiesByUserId(int id) {
		final List<Role> rrs = roleMapper.selectByUserId(id);
		return authorityEnums(rrs);
	}
	
	private List<SampleAppGrantedAuthority> authorityEnums(final List<Role> rrs) {
		final LinkedList<SampleAppGrantedAuthority> vrs = new LinkedList<SampleAppGrantedAuthority>();
		for (final Role rr : rrs) {
			vrs.add(SampleAppGrantedAuthority.valueOf(rr.getName()));
		}
		return vrs;
	}
}
