package com.kentkart.demo.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kentkart.demo.business.abstracts.InformationService;
import com.kentkart.demo.core.utilities.results.DataResult;
import com.kentkart.demo.core.utilities.results.Result;
import com.kentkart.demo.core.utilities.results.SuccessDataResult;
import com.kentkart.demo.core.utilities.results.SuccessResult;
import com.kentkart.demo.dataAccess.abstracts.InformationDao;
import com.kentkart.demo.entities.concretes.Information;

@Component
public class InformationManager implements InformationService {
	
	private InformationDao informationdao;
	
	@Autowired
	public InformationManager(InformationDao informationdao) {
		super();
		this.informationdao = informationdao;
	}


	@Override
	public DataResult<List<Information>> getAll() {
		return new SuccessDataResult<List<Information>>(this.informationdao.findAll(), "Data listelendi");
	}


	@Override
	public Information add(Information information) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(information.getPassword());
		information.setPassword(encodedPassword);
		
		Information saveInfo = this.informationdao.save(information);
		
		return saveInfo;
	}
	
	@Override
	public Result delete(String id) {
		this.informationdao.deleteById(id);
		return new SuccessResult("Kisi silindi");
	}

	@Override
	public Result update(Information information, String id) throws Exception{
		Optional<Information> maybeInfo = informationdao.findById(id);
		if(!maybeInfo.isPresent()) {throw new NotFoundException();}
		
		Information oldInformation = maybeInfo.get();
		if(information.getName() != null) {
			oldInformation.setName(information.getName());
		}
		if(information.getSurname() != null) {
			oldInformation.setSurname(information.getSurname());
		}
		if(information.getEmail() != null) {
			oldInformation.setEmail(information.getEmail());
		}
		if(information.getPassword() != null) {
			oldInformation.setPassword(information.getPassword());
		}
		if(information.getDescription() != null) {
			oldInformation.setDescription(information.getDescription());
		}
		
		this.informationdao.save(oldInformation);
		return new SuccessDataResult<>(oldInformation);
	}
	
	

	

	
	
}
