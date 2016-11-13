package sample.auth;

import org.springframework.security.core.GrantedAuthority;

public enum SampleAppGrantedAuthority implements GrantedAuthority {
	ADMINISTRATOR(0),
	OPERATOR(1);
	
	@SuppressWarnings("unused")
	private final int bit;
	
	private SampleAppGrantedAuthority(int bit) {
		this.bit = bit;
	}
	
	@Override
	public String getAuthority() {
		return toString();
	}
}
