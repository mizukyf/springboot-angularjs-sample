package sample.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.mapper.TaskMapper;
import sample.record.LimitOffsetClause;
import sample.record.OrderByClause;
import sample.vo.Paginated;
import sample.vo.Pagination;
import sample.vo.Task;
import sample.vo.User;

@Service
public class TaskService {
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private UserService userService;
	
	public Paginated<Task> findByUserId(int id, Pagination p) {
		final OrderByClause ob = OrderByClause.of("priority");
		final LimitOffsetClause lo = LimitOffsetClause.of(p);
		
		final List<sample.record.Task> rts = taskMapper.selectByUserId(id, ob, lo);
		final User vu = userService.findOneById(id);
		
		final List<Task> vts = new LinkedList<Task>();
		for (final sample.record.Task rt : rts) {
			vts.add(Task.fromRecord(rt, vu));
		}
		
		return p.bindTotalCount(taskMapper.selectCountByUserId(id))
				.andFetchedItems(vts);
	}
	
	public Task findOneByTaskIdAndUserId(final int taskId, final int userId) {
		final sample.record.Task rt = taskMapper.selectOneByTaskIdAndUserId(taskId, userId);
		if (rt == null) {
			return null;
		}
		return Task.fromRecord(rt, userService.findOneById(rt.getAssignedTo()));
	}
	
	public void modify(final Task vt) {
		if (vt.getId() == 0 || taskMapper.update(vt.toRecord()) == 0) {
			throw new IllegalArgumentException("Target not found.");
		}
	}
	
	public void register(final Task vt) {
		final sample.record.Task rt = vt.toRecord();
		rt.setId(taskMapper.selectSequenceNextVal());
		taskMapper.insert(rt);
	}
	
	public void removeById(final int id) {
		if (id == 0 || taskMapper.deleteById(id) == 0) {
			throw new IllegalArgumentException("Target not found.");
		}
	}
}
