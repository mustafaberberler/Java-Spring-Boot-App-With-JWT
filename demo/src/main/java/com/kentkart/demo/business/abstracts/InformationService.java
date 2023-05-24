package com.kentkart.demo.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kentkart.demo.core.utilities.results.DataResult;
import com.kentkart.demo.core.utilities.results.Result;
import com.kentkart.demo.entities.concretes.Information;

@Service
public interface InformationService {
	
	
	DataResult<List<Information>> getAll();
	
	Information add(Information information);
	
	Result delete(String id);
	
	Result update(Information information, String id) throws Exception;
	
	
	
}
