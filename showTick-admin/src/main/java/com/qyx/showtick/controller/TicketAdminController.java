package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.dto.TicketAdminDTO;
import com.qyx.showtick.service.TicketAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/24/24
 */
@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {
    @Autowired
    private TicketAdminService ticketAdminService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createTicket(@Validated @RequestBody TicketAdminDTO ticketAdminDTO) {
        CommonResult commonResult;
        int count = ticketAdminService.createTicket(ticketAdminDTO);
        if(count == 1){
            commonResult = CommonResult.success(count);
        } else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Ticket>> getAllTickets() {
        List<Ticket> ticketList = ticketAdminService.getAllTickets();
        return CommonResult.success(ticketList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult updateTicketById(@PathVariable Long id, @RequestBody TicketAdminDTO ticketAdminDTO) {
        int count = ticketAdminService.updateTicket(id, ticketAdminDTO);
        if(count == 1){
            return CommonResult.success(count);
        }
        return CommonResult.failed("update failed");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketAdminService.getTicketById(id);
        if(ticket != null){
            return CommonResult.success(ticket);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Ticket>> getTicketsByEventId(@PathVariable Long id) {
        List<Ticket> tickets = ticketAdminService.getTicketsByField("event_id", id);
        return CommonResult.success(tickets);
    }

}
