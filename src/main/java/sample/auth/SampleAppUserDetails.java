package sample.auth;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SampleAppUserDetails extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 2213449577870703888L;
	
	private static List<GrantedAuthority> emptyAuthorities() {
		return Collections.emptyList();
	}
	
	public static SampleAppUserDetails of(final Principal principal) {
		return (SampleAppUserDetails) ((Authentication) principal).getPrincipal();
	}
	
	public SampleAppUserDetails() {
        super("INVALID", "INVALID", false, false, false, false, emptyAuthorities());
    }
	
	public SampleAppUserDetails(final int id, final String username, 
			final String encodedPassword, final Collection<SampleAppGrantedAuthority> authorities) {
		super(username, encodedPassword, true, true, true, true, authorities);
		this.admin = authorities.contains(SampleAppGrantedAuthority.ADMINISTRATOR);
	}
	
	private int id;
	private boolean admin;
	
	public int getId() {
		return id;
	}
	public boolean isAdmin() {
		return admin;
	}
}
