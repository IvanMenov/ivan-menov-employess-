package com.sirmasolutions.employees;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sirmasolutions.employees.exceptions.DateNotParsedException;
import com.sirmasolutions.employees.pojo.Employee;

@Service
public class TextParsingService {
	
	private List<Employee> listOfEmployees = new ArrayList<Employee>();
	private Map<Integer, List<Employee>> mapPrjWithEmployees = new HashMap<Integer, List<Employee>>();

	private class Reader { 
		private static final String REGEX_FOR_SPLITTING_TEXT = ", ";
		private BufferedReader br; 
  
		public Reader(InputStream stream) {
			br = new BufferedReader(new InputStreamReader(stream));
		}

		//parsing the text into Employee objects
		private void parseInput() throws DateNotParsedException, NumberFormatException, IOException {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] listOfRecords = line.split(REGEX_FOR_SPLITTING_TEXT);
				Employee employee = new Employee();

				employee.setEmpId(Integer.valueOf(listOfRecords[0]));
				employee.setProjectId(Integer.valueOf(listOfRecords[1]));
				employee.setDateFrom(listOfRecords[2]);
				employee.setDateTo(listOfRecords[3]);
				employee.setTimeInProject();
				listOfEmployees.add(employee);

				if (!mapPrjWithEmployees.containsKey(Integer.valueOf(listOfRecords[1]))) {
					List<Employee> list = new ArrayList<Employee>();
					list.add(employee);
					mapPrjWithEmployees.put(Integer.valueOf(listOfRecords[1]), list);
				} else {
					List<Employee> list = mapPrjWithEmployees.get(Integer.valueOf(listOfRecords[1]));
					list.add(employee);
					Collections.sort(list);
					mapPrjWithEmployees.put(Integer.valueOf(listOfRecords[1]), list);
				}
			}

		}
	}
	public boolean areCollectionsEmpty() {
		return mapPrjWithEmployees.isEmpty() || listOfEmployees.isEmpty();
	}
	public void emptyCollections() {
		this.mapPrjWithEmployees.clear();
		this.listOfEmployees.clear();
	}
	public List<Employee> getParsedListOfAllEmployees(){
		return this.listOfEmployees;
	}
	public List<Employee> getColleagesWorkingLongestTogether(){
		List<Employee> listLongestWorkingTogether=  new ArrayList<Employee>();
		mapPrjWithEmployees.forEach((projectId,listEmployeesForProject) ->{
			if(listEmployeesForProject.size() >=2) {
				//if the listLongestWorkingTogether is empty just add the last 2 employees, 
				//since the listEmployeesForProject is ordered according to the employee's timeInProject
				
				if(listLongestWorkingTogether.isEmpty()) {
					listLongestWorkingTogether.add(listEmployeesForProject.get(listEmployeesForProject.size()-1));
					listLongestWorkingTogether.add(listEmployeesForProject.get(listEmployeesForProject.size()-2));
				}else {
					//otherwise sum the time employees spend together and put the 
					//put the employees with the highest time spent in the list
					long time1 = listLongestWorkingTogether.get(0).getTimeInProject() 
							+listLongestWorkingTogether.get(1).getTimeInProject();
					long time2 = listEmployeesForProject.get(listEmployeesForProject.size()-1).getTimeInProject()+
							listEmployeesForProject.get(listEmployeesForProject.size()-2).getTimeInProject();
					if(time2 > time1) {
						listLongestWorkingTogether.set(0, listEmployeesForProject.get(listEmployeesForProject.size()-1));
						listLongestWorkingTogether.set(1, listEmployeesForProject.get(listEmployeesForProject.size()-2));

					}
				}
			}

		});
		return listLongestWorkingTogether;
		
	}
	public void parseText(MultipartFile file) throws IOException, DateNotParsedException {
		Reader reader = new Reader(file.getInputStream()); 
		 reader.parseInput();
		
	}
}
