package com.sirmasolutions.employees;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.sirmasolutions.employees.exceptions.DateNotParsedException;
import com.sirmasolutions.employees.pojo.Employee;

@Controller
public class FileController {
	
	@Autowired
	TextParsingService service;
	
	@PostMapping(path = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String getFile(@RequestPart(name = "file") MultipartFile file, Model model) throws IOException, DateNotParsedException{
		if(!service.areCollectionsEmpty()) {
			service.emptyCollections();
		}
		service.parseText(file);
		List<Employee> listLongestWorkingColleagues = service.getColleagesWorkingLongestTogether();
		System.out.println("Pair of colleagues working together on a project for the longest time:");
		listLongestWorkingColleagues.forEach(System.out::println);
		List<Employee> list = service.getParsedListOfAllEmployees();
		model.addAttribute("employees", list);

		return "datagrid.html"; 
		
	}
}
