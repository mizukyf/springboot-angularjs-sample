package sample.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.auth.SampleAppUserDetails;
import sample.service.TaskService;
import sample.vo.Paginated;
import sample.vo.Pagination;
import sample.vo.Task;

@RestController
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(path="/api/tasks", method=RequestMethod.GET)
	public ResponseEntity<Paginated<Task>> get(final Principal principal,
			@ModelAttribute final Pagination pagination){
		return ResponseUtils.okOrNotFound(taskService
				.findByUserId(SampleAppUserDetails.of(principal).getId(), pagination));
	}
	
	@RequestMapping(path="/api/tasks/{id}", method=RequestMethod.GET)
	public ResponseEntity<Task> get(final Principal principal, @PathVariable("id") final int id){
		return ResponseUtils.okOrNotFound(taskService.findOneByTaskIdAndUserId(id, takeUserId(principal)));
	}
	
	@RequestMapping(path="/api/tasks", method=RequestMethod.POST)
	public ResponseEntity<Task> post(@RequestBody final Task t){
		taskService.register(t);
		return ResponseUtils.okOrNotFound(t);
	}
	
	@RequestMapping(path="/api/tasks/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Task> put(@PathVariable("id") final int id, @RequestBody final Task u) {
		u.setId(id);
		taskService.register(u);
		return ResponseUtils.okOrNotFound(u);
	}
	
	@RequestMapping(path="/api/tasks/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
		taskService.removeById(id);
		return ResponseUtils.ok();
	}
	
	private int takeUserId(Principal p) {
		if (p == null || ! (p instanceof Authentication)) {
			return 0;
		}
		final Object o = ((Authentication) p).getPrincipal();
		if (o == null) {
			return 0;
		}
		return SampleAppUserDetails.of(p).getId();
	}
}
