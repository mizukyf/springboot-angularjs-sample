package sample.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import sample.auth.SampleAppGrantedAuthority;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Login {
	private String username;
	private char[] password;
	private Collection<GrantedAuthority> authorities;
	private boolean loggedIn;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public char[] getPassword() {
		return password;
	}
	@JsonProperty("password")
	public void setPassword(char[] password) {
		this.password = password;
	}
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public boolean isAnnonymous() {
		return authorities == null || authorities.isEmpty();
	}
	public boolean isAdmin(){
		if (authorities == null) {
			return false;
		}
		for (final GrantedAuthority a : authorities) {
			if (a.getAuthority().equals(SampleAppGrantedAuthority.NAME_ADMINISTRATOR)) {
				return true;
			}
		}
		return false;
	}
}
