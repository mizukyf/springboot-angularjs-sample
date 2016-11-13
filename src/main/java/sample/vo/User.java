package sample.vo;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sample.auth.SampleAppGrantedAuthority;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User {
	private int id;
	private String name;
	private char[] rawPassword;
	private Collection<SampleAppGrantedAuthority> authorities;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public char[] getRawPassword() {
		return rawPassword;
	}
	public void setRawPassword(char[] rawPassword) {
		this.rawPassword = rawPassword;
	}
	public Collection<SampleAppGrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<SampleAppGrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public sample.record.User toRecord(final String encodedPassword) {
		final sample.record.User r = new sample.record.User();
		r.setEncodedPassword(encodedPassword);
		r.setId(getId());
		r.setName(getName());
		return r;
	}
	
	public static User fromRecord(final sample.record.User r, final Collection<SampleAppGrantedAuthority> as) {
		final User v = new User();
		v.setAuthorities(as);
		v.setId(r.getId());
		v.setName(r.getName());
		return v;
	}
}
