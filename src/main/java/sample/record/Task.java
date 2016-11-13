package sample.record;

import java.util.Date;

public class Task {
	private int id;
	private int assignedTo;
	private String title;
	private String description;
	private int priority;
	private boolean finished;
	private Date createdOn;
	private Date updatedOn;
	private Date finishedOn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(int assignedTo) {
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Date getFinishedOn() {
		return finishedOn;
	}
	public void setFinishedOn(Date finishedOn) {
		this.finishedOn = finishedOn;
	}
}
