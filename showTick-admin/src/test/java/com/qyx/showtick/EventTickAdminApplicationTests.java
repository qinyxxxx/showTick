package com.qyx.showtick;

import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.entity.EventCategory;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.dto.EventAdminDTO;
import com.qyx.showtick.dto.EventCategoryAdminDTO;
import com.qyx.showtick.dto.TicketAdminDTO;
import com.qyx.showtick.service.EventAdminService;
import com.qyx.showtick.service.EventCategoryAdminService;
import com.qyx.showtick.service.TicketAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = ShowTickAdminApplication.class)
class EventTickAdminApplicationTests {
    @Autowired
    private EventAdminService eventAdminService;
    @Autowired
    private EventCategoryAdminService eventCategoryAdminService;

    @Autowired
    private TicketAdminService ticketAdminService;

    @Test
    public void createData() {
//        // create 10 categories
//        for (int i = 0; i < 10; i++) {
//            EventCategoryAdminDTO eventCategoryAdminDTO = new EventCategoryAdminDTO();
//            eventCategoryAdminDTO.setName("category " + i);
//            eventCategoryAdminDTO.setDescription("description " + i);
//            eventCategoryAdminService.createEventCategory(eventCategoryAdminDTO);
//        }
//
//        // create 10 events
//        for (int i = 0; i < 10; i++) {
//            EventAdminDTO eventAdminDTO = new EventAdminDTO();
//            eventAdminDTO.setName("event " + i);
//            eventAdminDTO.setDescription("description " + i);
//            eventAdminDTO.setLocation("location " + i);
//            eventAdminDTO.setPerformer("performer " + i);
//            eventAdminDTO.setCategoryId(2L);
//            eventAdminDTO.setEndTime(LocalDateTime.now());
//            eventAdminDTO.setStartTime(LocalDateTime.now());
//            eventAdminDTO.setTotalTicket(100 + i);
//            eventAdminDTO.setRemainingTicket(100 + i);
//            eventAdminService.createEvent(eventAdminDTO);
//        }

        List<Event> events = eventAdminService.getEventsByField("name", "event 1");
        if(events.size() != 1){
            throw new IllegalArgumentException("error");
        }
        Long eventId = events.get(0).getId();
        // create 40 tickets for event 1;
        // A-1-1 ~ A-2-5
        // B-1-1 ~ B-2-5
        // C
        for (int i = 0; i < 10; i ++){
            TicketAdminDTO ticket = new TicketAdminDTO();
            ticket.setEventId(eventId);
            ticket.setSection("A");
            ticket.setSeatRow((1 + i/5) + "");
            ticket.setSeatNumber((1 + i%5) + "");
            ticket.setPrice((1+i/5)*100);
            ticketAdminService.createTicket(ticket);
        }
        for (int i = 0; i < 10; i ++){
            TicketAdminDTO ticket = new TicketAdminDTO();
            ticket.setEventId(eventId);
            ticket.setSection("B");
            ticket.setSeatRow((1 + i/5) + "");
            ticket.setSeatNumber((1 + i%5) + "");
            ticket.setPrice((1+i/5)*100);
            ticketAdminService.createTicket(ticket);
        }
        for (int i = 0; i < 10; i ++){
            TicketAdminDTO ticket = new TicketAdminDTO();
            ticket.setEventId(eventId);
            ticket.setSection("C");
            ticket.setSeatRow((1 + i/5) + "");
            ticket.setSeatNumber((1 + i%5) + "");
            ticket.setPrice((1+i/5)*100);
            ticketAdminService.createTicket(ticket);
        }
        for (int i = 0; i < 10; i ++){
            TicketAdminDTO ticket = new TicketAdminDTO();
            ticket.setEventId(eventId);
            ticket.setSection("D");
            ticket.setSeatRow((1 + i/5) + "");
            ticket.setSeatNumber((1 + i%5) + "");
            ticket.setPrice((1+i/5)*100);
            ticketAdminService.createTicket(ticket);
        }
    }
}
