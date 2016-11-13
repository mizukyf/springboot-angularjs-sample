package sample.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
	int insert(@Param("uid") int uid, @Param("rid") int rid);
	int deleteByUserId(@Param("uid") int uid);
}
