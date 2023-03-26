package com.oop.response;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Ticket {

	@Id
	@GeneratedValue
	private Integer ticketId;
	private String name;
	private String lname;
	private String fromm;
	private String tto;
	private String trainNum;
	private LocalDate doj;
	private Double ticketCost;
	private String ticketStatus;

}
