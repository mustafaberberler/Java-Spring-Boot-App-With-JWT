package com.kentkart.demo.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kentkart.demo.business.abstracts.InformationService;
import com.kentkart.demo.core.utilities.results.DataResult;
import com.kentkart.demo.core.utilities.results.ErrorResult;
import com.kentkart.demo.core.utilities.results.Result;
import com.kentkart.demo.entities.concretes.Information;


@RestController
@RequestMapping("/api/informations")
@Component
public class InformationsController {
	
	private InformationService informationservice;
	 
	
	@Autowired
	public InformationsController(InformationService informationservice) {
		super();
		this.informationservice = informationservice;
	}

	@GetMapping("/getall")
	public DataResult<List<Information>> getAll(){
		return this.informationservice.getAll();
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody Information information) {
		Information savedInformation = this.informationservice.add(information);
		URI informationURI = URI.create("/api/informations/" + savedInformation.getId());
		
		return ResponseEntity.created(informationURI).body(savedInformation);
		
	}
	
	@RequestMapping(path = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody Information information) {
		Information savedInformation = this.informationservice.add(information);
		URI informationURI = URI.create("/api/informations/" + savedInformation.getId());
		
		return ResponseEntity.created(informationURI).body(savedInformation);
		
	}
	
	//@DeleteMapping("/delete/{id}")
	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("id") String id) {
		try {
			this.informationservice.delete(id);
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().build();
	}
	
	//@PutMapping("/update/{id}")
	@RequestMapping(path = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Result update(@RequestBody Information information, @PathVariable String id) {
		try {
			return this.informationservice.update(information, id);
		} catch (Exception e) {
			return new ErrorResult("Error");
		}
		
	}
	
	
}
