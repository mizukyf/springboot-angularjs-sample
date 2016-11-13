package sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.record.LimitOffsetClause;
import sample.record.OrderByClause;
import sample.record.User;

public interface UserMapper {
	int selectSequenceNextVal();
	User selectOneById(@Param("id") int id);
	User selectOneByName(@Param("name") String name);
	List<User> selectAll(@Param("ob") OrderByClause ob, @Param("lo") LimitOffsetClause lo);
	int selectCountAll();
	int insert(@Param("u") User user);
	int update(@Param("u") User user);
	int deleteById(@Param("id") int id);
}
