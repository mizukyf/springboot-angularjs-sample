package sample.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

/**
 * アプリケーションのユーザに付与される権限.
 */
public class SampleAppGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 5860262485618704214L;
	private static final Map<Integer, SampleAppGrantedAuthority> 
	knownAuthorities = new HashMap<Integer, SampleAppGrantedAuthority>();
	public static final String NAME_ADMINISTRATOR = "ADMINISTRATOR";
	public static final String NAME_OPERATORY = "OPERATOR";
	
	public static SampleAppGrantedAuthority fromRecord(sample.record.Authority ra) {
		final SampleAppGrantedAuthority va = knownAuthorities.get(ra.getId());
		if (va == null) {
			final SampleAppGrantedAuthority vaNew = 
					new SampleAppGrantedAuthority(ra.getId(), ra.getName());
			return knownAuthorities.put(ra.getId(), vaNew);
		}
		return va;
	}
	
	private final int id;
	private final String name;
	
	private SampleAppGrantedAuthority(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String getAuthority() {
		return name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SampleAppGrantedAuthority other = (SampleAppGrantedAuthority) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
