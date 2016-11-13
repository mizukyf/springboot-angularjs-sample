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

import sample.mapper.AuthorityMapper;
import sample.mapper.UserMapper;
import sample.record.Authority;
import sample.record.User;

/**
 * ユーザ認証・認可プロセスで利用されるユーザ情報のためのDAO.
 * <p>このオブジェクトの{@link #loadUserByUsername(String)}が返したユーザ情報をもとに
 * Spring Securityによる認証・認可プロセスが行われる。
 * このサンプル・アプリケーションではユーザ情報はDBからロードするつくりにしてある。</p>
 */
@Service
public class SampleAppUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AuthorityMapper authorityMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	// 指定されたユーザ名をチェック
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("Username is empty.");
        }
        
        final User ru = userMapper.selectOneByName(username);
        
        if (ru == null) {
            throw new UsernameNotFoundException(String.format("User %s is not found.", username));
        }
        
    	final LinkedList<SampleAppGrantedAuthority> vas = new LinkedList<SampleAppGrantedAuthority>();
        for (final Authority ra : authorityMapper.selectByUserId(ru.getId())) {
        	vas.add(SampleAppGrantedAuthority.fromRecord(ra));
        }
    	return new SampleAppUserDetails(ru.getId(), ru.getName(), ru.getEncodedPassword(), vas);
    }
    
    @Bean
    public PasswordEncoder passwordEncorder() {
    	return passwordEncoder;
    }
}
