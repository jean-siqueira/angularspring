package com.athena.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.athena.helpdesk.api.entity.ChangeStatus;

public interface ChangeStatusRepository extends MongoRepository<ChangeStatus, String>{

	Iterable<ChangeStatus> findByTicketIdOrderByDateChangeStatusDesc(String ticketId);
	
}
