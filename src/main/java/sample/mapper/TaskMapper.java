package sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.record.LimitOffsetClause;
import sample.record.OrderByClause;
import sample.record.Task;

public interface TaskMapper {
	int selectSequenceNextVal();
	Task selectOneByTaskIdAndUserId(@Param("tid") int taskId, @Param("uid") int userId);
	List<Task> selectByUserId(@Param("id") int id, @Param("ob") OrderByClause ob, @Param("lo") LimitOffsetClause lo);
	int selectCountByUserId(@Param("id") int id);
	int insert(@Param("t") Task task);
	int update(@Param("t") Task task);
	int deleteById(@Param("id") int id);
	int deleteByUserId(@Param("id") int id);
}
