package com.oop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oop.response.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>{

}
