package sample.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserAuthorityMapper {
	int insert(@Param("uid") int uid, @Param("aid") int aid);
	int deleteByUserId(@Param("uid") int uid);
}
