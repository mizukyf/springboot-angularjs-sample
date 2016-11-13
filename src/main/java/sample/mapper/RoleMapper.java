package sample.mapper;

import java.util.List;

import sample.record.Role;

public interface RoleMapper {
	Role selectOneByName(String name);
	List<Role> selectByUserId(int id);
}
