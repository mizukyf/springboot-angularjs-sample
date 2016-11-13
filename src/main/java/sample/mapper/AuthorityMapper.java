package sample.mapper;

import java.util.List;

import sample.record.Authority;

public interface AuthorityMapper {
	Authority selectOneByName(String name);
	List<Authority> selectByUserId(int id);
}
