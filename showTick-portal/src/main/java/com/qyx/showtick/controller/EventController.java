package com.qyx.showtick.controller;


import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.service.EventService;
import com.qyx.showtick.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/12/24
 */
@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Event> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(eventService.getEventById(id));
    }

    @RequestMapping(value = "/top5", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Event>> getTop5Events() {
        return CommonResult.success(eventService.getTop5Events());
    }

    @RequestMapping(value = "/{eventId}/available-seats", method = RequestMethod.GET)
    public CommonResult<List<Ticket>> getAvailableSeatsByEventId(@PathVariable Long eventId) {
        return CommonResult.success(ticketService.getAvailableSeatsByEventId(eventId));
    }

}
