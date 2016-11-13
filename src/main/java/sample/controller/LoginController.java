package sample.controller;

import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.auth.SampleAppUserDetails;
import sample.vo.Login;

@RestController
public class LoginController {
	@Autowired
	private AuthenticationManager am;
	
	@RequestMapping(path="/api/logins", method=RequestMethod.GET)
	public ResponseEntity<Login> get(){
		return ResponseUtils.ok(authentication2Login
				(SecurityContextHolder.getContext().getAuthentication()));
	}
	
	@RequestMapping(path="/api/logins/{id}", method=RequestMethod.GET)
	public ResponseEntity<Login> get(@PathVariable("id") final int id){
		return get();
	}
	
	@RequestMapping(path="/api/logins", method=RequestMethod.POST)
	public ResponseEntity<Login> post(@RequestBody final Login login){
		
		final Authentication result = am.authenticate(login2Authentication(login));
		SecurityContextHolder.getContext().setAuthentication(result);
		return ResponseUtils.ok(authentication2Login(result));
	}
	
	@RequestMapping(path="/api/logins", method=RequestMethod.DELETE)
	public ResponseEntity<Login> delete(final HttpServletRequest req) throws ServletException{
		req.logout();
		return ResponseUtils.ok(authentication2Login
				(SecurityContextHolder.getContext().getAuthentication()));
	}
	
	@RequestMapping(path="/api/logins/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Login> delete(final HttpServletRequest req, 
			@PathVariable("id") final int id) throws ServletException{
		return delete(req);
	}
	
	private Authentication login2Authentication(final Login l) {
		return new UsernamePasswordAuthenticationToken(l.getUsername(), new String(l.getPassword()));
	}
	
	private Login authentication2Login(final Authentication a) {
		final Object p = a.getPrincipal();
		final Login l = new Login();
		if (p instanceof SampleAppUserDetails) {
			final SampleAppUserDetails d = (SampleAppUserDetails) p;
			l.setLoggedIn(true);
			l.setUsername(d.getUsername());
			l.setAuthorities(d.getAuthorities());
		}else{
			l.setLoggedIn(false);
			l.setUsername("*");
			l.setAuthorities(Collections.<GrantedAuthority>emptySet());
		}
		return l;
	}
}
