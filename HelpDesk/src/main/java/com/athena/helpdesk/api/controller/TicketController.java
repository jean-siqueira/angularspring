package com.athena.helpdesk.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.athena.helpdesk.api.entity.ChangeStatus;
import com.athena.helpdesk.api.entity.Ticket;
import com.athena.helpdesk.api.entity.User;
import com.athena.helpdesk.api.enums.StatusEnum;
import com.athena.helpdesk.api.response.Response;
import com.athena.helpdesk.api.security.jwt.JwtTokenUtil;
import com.athena.helpdesk.api.service.TicketService;
import com.athena.helpdesk.api.service.UserService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Ticket>> createOrUpdate(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result){
		Response<Ticket> response = new Response<>();

		try {
			validateCreateTicket(ticket, result);
			
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error->{
					response.getErrors().add(error.getDefaultMessage());
				});
				return ResponseEntity.badRequest().body(response);
			}
			ticket.setStatus(StatusEnum.getStatus("New"));
			ticket.setUser(userFromRequest(request));
			ticket.setDate(new Date());
			ticket.setNumber(generateNumber());
			
			Ticket ticketPersisted = ticketService.createOrUpdate(ticket);
			response.setData(ticketPersisted);
			
		}catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
		
	}
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}

	private User userFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String email = jwtTokenUtil.getUsernameFromToken(token);
		return userService.findByEmail(email);
	}

	private void validateCreateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getTitle() == null) {
			result.addError(new ObjectError("Ticket", "Título não informado"));
		}
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Ticket>> update(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result){
		Response<Ticket> response = new Response<>();

		try {
			validateUpdateTicket(ticket, result);
			
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error->{
					response.getErrors().add(error.getDefaultMessage());
				});
				return ResponseEntity.badRequest().body(response);
			}
			
			Ticket ticketCurrent = ticketService.findById(ticket.getId());
			ticket.setStatus(ticketCurrent.getStatus());
			ticket.setUser(ticketCurrent.getUser());
			ticket.setDate(ticketCurrent.getDate());
			ticket.setNumber(ticketCurrent.getNumber());
			if(ticket.getAssignedUser() != null) {
				ticket.setAssignedUser(ticketCurrent.getAssignedUser());
			}
			ticket.setStatus(StatusEnum.getStatus("New"));
			ticket.setUser(userFromRequest(request));
			ticket.setDate(new Date());
			ticket.setNumber(generateNumber());
			
			Ticket ticketPersisted = ticketService.createOrUpdate(ticket);
			response.setData(ticketPersisted);
			
		}catch(Exception ex) {
			response.getErrors().add(ex.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
		
	}

	private void validateUpdateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getId() == null) {
			result.addError(new ObjectError("Ticket", "Id não informado"));
		}
		
		if(ticket.getTitle() == null) {
			result.addError(new ObjectError("Ticket", "Título não informado"));
		}
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	public ResponseEntity<Response<Ticket>> findById(@PathVariable("id") String id){
		Response<Ticket> response = new Response<>();
		Ticket ticket = ticketService.findById(id);
		
		if(ticket == null) {
			response.getErrors().add("Registro não encontrado id:"+id);
			return ResponseEntity.badRequest().body(response);
		}
		List<ChangeStatus> changes = new ArrayList<>();
		Iterable<ChangeStatus> changesCurrent = ticketService.listChangeStatus(ticket.getId());
		
		for(Iterator<ChangeStatus> iterator = changesCurrent.iterator(); iterator.hasNext();) {
			ChangeStatus changeStatus = (ChangeStatus) iterator.next();
			changeStatus.setTicket(null);
			changes.add(changeStatus);
		}
		ticket.setChanges(changes);
		response.setData(ticket);
		
		return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id){
		Response<String> response = new Response<>();
		Ticket ticket = ticketService.findById(id);
		if(ticket == null) {
			response.getErrors().add("Registro não encontrado id:"+id);
			return ResponseEntity.badRequest().body(response);
		}
		ticketService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
}
