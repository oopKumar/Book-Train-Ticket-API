package com.oop.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Passenger {
	private String name;
	private String lname;
	private String fromm;
	private String tto;
	private LocalDate doj;
	private String trainNum;

}
