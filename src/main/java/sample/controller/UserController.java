package sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.service.UserService;
import sample.vo.Paginated;
import sample.vo.Pagination;
import sample.vo.User;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(path="/api/users", method=RequestMethod.GET)
	public ResponseEntity<Paginated<User>> get(@ModelAttribute final Pagination p){
		return ResponseUtils.okOrNotFound(userService.findAll(p));
	}
	
	@RequestMapping(path="/api/users/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> get(@PathVariable("id") final int id){
		return ResponseUtils.okOrNotFound(userService.findOneById(id));
	}
	
	@RequestMapping(path="/api/users", method=RequestMethod.POST)
	public ResponseEntity<User> post(@RequestBody final User u){
		userService.register(u);
		return ResponseUtils.okOrNotFound(u);
	}
	
	@RequestMapping(path="/api/users/{id}", method=RequestMethod.PUT)
	public ResponseEntity<User> put(@PathVariable("id") final int id, @RequestBody final User u) {
		u.setId(id);
		userService.register(u);
		return ResponseUtils.okOrNotFound(u);
	}
	
	@RequestMapping(path="/api/users/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
		userService.removeById(id);
		return ResponseUtils.ok();
	}
}
