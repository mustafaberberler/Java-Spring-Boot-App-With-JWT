package com.kentkart.demo.dataAccess.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.kentkart.demo.entities.concretes.Information;

@Repository
public interface InformationDao extends JpaRepository<Information, String> {
	
	
	
	Information getByNameAndSurname(String name, String surname);
	
	List<Information> getByIdIn(List<String>id);
	
	
	@Query("From Information where name=:name and id=:id")  // JPQL
	List<Information> getByNameAndId(String name, String id);
	
	
	List<Information> getByNameOrId(String name, String id);
	
	Optional<Information > findByEmail(String email);
	
}
