
package com.oop.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oop.request.Passenger;
import com.oop.response.Ticket;
import com.oop.service.BookTicketService;

@RestController
public class BookTicketController {

	BookTicketService bookTicketService;

	public BookTicketController(BookTicketService bookTicketService) {
		super();
		this.bookTicketService = bookTicketService;
	}

	@PostMapping(
			value = "/bookTicket",
			consumes = { "application/xml", "application/json" }, 
			produces = { "application/xml", "application/json" }
			)
	public ResponseEntity<Ticket> bookTicket(@RequestBody Passenger passenger) {

		Ticket bookTicket = bookTicketService.bookTicket(passenger);
		return new ResponseEntity<>(bookTicket, HttpStatus.CREATED);
	}

	@GetMapping(value = "/getTicket/{ticketId}", produces = { "application/xml", "application/json" })
	public ResponseEntity<?> getTicketById(@PathVariable Integer ticketId) {

		Object getTicket = bookTicketService.getTicketById(ticketId);
		if (getTicket.equals("Not Found")) {
			return new ResponseEntity<>("Not Ticket Found With this Id   " + ticketId, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(getTicket, HttpStatus.OK);
		}
	}
	
	
	
	@DeleteMapping("/cancelTicket/{ticketId}")
	public ResponseEntity<String> cancelTicket(@PathVariable Integer ticketId) {
		String cancelTicket = bookTicketService.cancelTicket(ticketId);
		
		return new ResponseEntity<>(cancelTicket,HttpStatus.OK);
	
	}
	
	

	@GetMapping(
			value ="/downloadTicketById/{pnr}",
			produces= {"text/plain"}
			)
	public ResponseEntity<String> pdfReport(HttpServletResponse response, @PathVariable Integer pnr) throws Exception {

		response.setContentType("application/pdf");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=ticket.pdf";

		response.setHeader(headerKey, headerValue);
		String downloadTicketById = bookTicketService.downloadTicketById(response, pnr);
		return new ResponseEntity<>(downloadTicketById, HttpStatus.OK);

	}
}