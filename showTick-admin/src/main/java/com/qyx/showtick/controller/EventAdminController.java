package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.dto.EventAdminDTO;
import com.qyx.showtick.service.EventAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/18/24
 */
@RestController
@RequestMapping("/admin/event")
public class EventAdminController {
    @Autowired
    private EventAdminService eventAdminService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createEvent(@Validated @RequestBody EventAdminDTO eventParam) {
        CommonResult commonResult;
        int count = eventAdminService.createEvent(eventParam);
        if(count == 1){
            commonResult = CommonResult.success(count);
        } else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Event>> getAllEvents() {
        List<Event> eventList = eventAdminService.getAllEvents();
        return CommonResult.success(eventList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult updateEventById(@PathVariable Long id, @RequestBody EventAdminDTO eventAdminDTO) {
        int count = eventAdminService.updateEvent(id, eventAdminDTO);
        if(count == 1){
            return CommonResult.success(count);
        }
        return CommonResult.failed("update failed");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getEventById(@PathVariable Long id) {
        Event event = eventAdminService.getEventById(id);
        if(event != null){
            return CommonResult.success(event);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Event>> getEventByCategoryId(@PathVariable Long id) {
        List<Event> events = eventAdminService.getEventsByField("category_id", id);
        return CommonResult.success(events);
    }

}
