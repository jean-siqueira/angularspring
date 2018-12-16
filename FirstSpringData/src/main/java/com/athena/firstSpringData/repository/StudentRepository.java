package com.athena.firstSpringData.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.athena.firstSpringData.entity.Student;

public interface StudentRepository extends MongoRepository<Student, String>{

	public List<Student> findByNameLikeIgnoreCase(String name);
	
}
