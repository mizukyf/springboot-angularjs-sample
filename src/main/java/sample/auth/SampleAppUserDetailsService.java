package sample.auth;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import sample.mapper.RoleMapper;
import sample.mapper.UserMapper;
import sample.record.Role;
import sample.record.User;

@Service
public class SampleAppUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	// 指定されたユーザ名をチェック
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("Username is empty");
        }
        
        final User ru = userMapper.selectOneByName(username);
        
        if (ru == null) {
            throw new UsernameNotFoundException(String.format("User %s is not found.", username));
        }
        
    	final LinkedList<SampleAppGrantedAuthority> vas = new LinkedList<SampleAppGrantedAuthority>();
        for (final Role rr : roleMapper.selectByUserId(ru.getId())) {
        	vas.add(SampleAppGrantedAuthority.valueOf(rr.getName()));
        }
    	return new SampleAppUserDetails(ru.getId(), ru.getName(), ru.getEncodedPassword(), vas);
    }
    
    @Bean
    public PasswordEncoder passwordEncorder() {
    	return passwordEncoder;
    }
}
