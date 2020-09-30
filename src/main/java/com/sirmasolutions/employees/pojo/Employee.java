package com.sirmasolutions.employees.pojo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sirmasolutions.employees.exceptions.DateNotParsedException;
import com.sirmasolutions.employees.utils.DateParser;

public class Employee implements Comparable<Employee>{
	
	private int empId;
	private int projectId;
	private long timeInProject;	
	private LocalDateTime dateFrom;
	private LocalDateTime dateTo;
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	
	public long getTimeInProject() {
		return timeInProject;
	}
	
	public void setTimeInProject() throws DateNotParsedException { 
		if(this.dateFrom == null || this.dateTo == null) {
			throw new DateNotParsedException();
		}
		timeInProject = ChronoUnit.DAYS.between(this.dateFrom, this.dateTo);
	}
	@JsonIgnore
	public LocalDateTime getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) throws DateNotParsedException {
		this.dateFrom =  DateParser.parseDateToLocalDateTime(dateFrom, true);
		if(this.dateFrom == null) {
			throw new DateNotParsedException(dateFrom);
		}
	}
	@JsonIgnore
	public LocalDateTime getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) throws DateNotParsedException {
		if(dateTo.equalsIgnoreCase("null")) {
			this.dateTo = LocalDateTime.now();
		}else {
			this.dateTo = DateParser.parseDateToLocalDateTime(dateTo, false);
		}
		
		if(this.dateFrom == null) {
			throw new DateNotParsedException(dateTo);
		}
	}
	@Override
	public int compareTo(Employee o) {
		return Long.compare(this.timeInProject, o.timeInProject);
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", projectId=" + projectId + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo
				+ "]";
	}
}
