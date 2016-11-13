package sample.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Task {
	private int id;
	private User assignedTo;
	private String title;
	private String description;
	private int priority;
	private boolean finished;
	private Date finishedOn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public Date getFinishedOn() {
		return finishedOn;
	}
	public void setFinishedOn(Date finishedOn) {
		this.finishedOn = finishedOn;
	}
	
	public sample.record.Task toRecord() {
		final sample.record.Task r = new sample.record.Task();
		r.setId(id);
		r.setAssignedTo(getAssignedTo() == null ? 0 : getAssignedTo().getId());
		r.setTitle(getTitle());
		r.setDescription(getDescription());
		r.setPriority(getPriority());
		r.setFinished(isFinished());
		return r;
	}
	
	public static Task fromRecord(sample.record.Task r, User vu) {
		final Task v = new Task();
		v.setAssignedTo(vu);
		v.setDescription(r.getDescription());
		v.setFinished(r.isFinished());
		v.setFinishedOn(r.getFinishedOn());
		v.setId(r.getId());
		v.setPriority(r.getPriority());
		v.setTitle(r.getTitle());
		return v;
	}
}
