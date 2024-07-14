package com.qyx.showtick.common.controller;


import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.EventParam;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.service.EventService;
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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createEvent(@Validated @RequestBody EventParam eventParam) {
        CommonResult commonResult;
        int count = eventService.createEvent(eventParam);
        if(count == 1){
            commonResult = CommonResult.success(count);
        } else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Event> getItem(@PathVariable("id") Long id) {
        return CommonResult.success(eventService.getEventById(id));
    }
}
