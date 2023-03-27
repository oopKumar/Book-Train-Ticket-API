
package com.oop.service;

import java.awt.Color;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.oop.repository.TicketRepository;
import com.oop.request.Passenger;
import com.oop.response.Ticket;

@Service
public class BookTicketServiceImpl implements BookTicketService {

	private TicketRepository ticketRepository;

	public BookTicketServiceImpl(TicketRepository ticketRepository) {
		super();
		this.ticketRepository = ticketRepository;
	}

	public Ticket bookTicket(Passenger passenger) {

		Ticket ticket = new Ticket();
		BeanUtils.copyProperties(passenger, ticket);
		ticket.setTicketStatus("Confirmed");
		ticket.setTicketCost(1200.00);
		ticketRepository.save(ticket);

		return ticket;

	}

	@SuppressWarnings("unchecked")
	public <T> T checkTicketStatusById(Integer ticketId) {
		Optional<Ticket> ticket = ticketRepository.findById(ticketId);
		if (ticket.isPresent()) {
			return (T) ticket.get();
		} else {
			return (T) "Not Found";
		}
	}

	public String cancelTicket(Integer ticketId) {

		Optional<Ticket> findById = ticketRepository.findById(ticketId);
		if (findById.isPresent()) {
			ticketRepository.deleteById(ticketId);
			return "Ticket Canceled Successfully";

		} else {
			return "Ticket Id Not Found";

		}
	}

	public String downloadTicketById(HttpServletResponse response, Integer ticketId) throws Exception {
		Document document = new Document(PageSize.A4);
		PdfPTable table = new PdfPTable(9);
		Optional<Ticket> findById = ticketRepository.findById(ticketId);
		if (findById.isPresent()) {
			PdfWriter.getInstance(document, response.getOutputStream());

			document.open();

			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			font.setSize(18);
			font.setColor(Color.BLUE);

			Paragraph p = new Paragraph("Ticket", font);
			p.setAlignment(Paragraph.ALIGN_CENTER);

			document.add(p);

			table.setWidthPercentage(100f);
			table.setWidths(new float[] { 1.0f, 3.5f, 2.5f, 2.0f, 1.5f, 3.0f, 3.5f, 3.5f, 3.5f });
			table.setSpacingBefore(10);

			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(Color.BLUE);
			cell.setPadding(5);

			font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setColor(Color.WHITE);

			cell.setPhrase(new Phrase("Id", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Name", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Lname", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("From", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("To", font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("TrainNum", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("DOJ", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TicketCost", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase("TicketStatus", font));
			table.addCell(cell);

			Ticket ticket = findById.get();
			table.addCell(String.valueOf(ticket.getTicketId()));
			table.addCell(ticket.getName());
			table.addCell(ticket.getLname());
			table.addCell(ticket.getFromm());
			table.addCell(ticket.getTto());
			table.addCell(ticket.getTrainNum());
			table.addCell(String.valueOf(ticket.getDoj()));
			table.addCell(String.valueOf(ticket.getTicketCost()));
			table.addCell(ticket.getTicketStatus());
			document.add(table);
			document.close();
			return "Ticket Downloaded SuccessFully";

		} else {
			return "PNR Not Available";
		}

	}

}
