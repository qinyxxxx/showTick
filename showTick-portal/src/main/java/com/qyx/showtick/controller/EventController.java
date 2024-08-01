package com.qyx.showtick.controller;


import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.service.EventService;
import com.qyx.showtick.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/12/24
 */
@RestController
@RequestMapping("/events")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Event> getItem(@PathVariable("id") Long id) {
        Event event = eventService.getById(id);
        LOGGER.info("Get Event {} info: {}", id, event.toString());
        return CommonResult.success(event);
    }

    @RequestMapping(value = "/top5", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Event>> getTop5Events() {
        List<Event> events = eventService.getTop5Events();
        LOGGER.info("Get top5 events: {}", events);
        return CommonResult.success(events);
    }

    @RequestMapping(value = "/{eventId}/available-seats", method = RequestMethod.GET)
    public CommonResult<List<Ticket>> getAvailableSeatsByEventId(@PathVariable Long eventId) {
        List<Ticket> tickets = ticketService.getAvailableSeatsByEventId(eventId);
        LOGGER.info("Get available seats of events({}): {}", eventId, tickets.size());
        return CommonResult.success(tickets);
    }

}
