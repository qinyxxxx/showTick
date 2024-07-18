package com.qyx.showtick.controller;


import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.EventParam;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/12/24
 */
@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Event> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(eventService.getEventById(id));
    }


}
