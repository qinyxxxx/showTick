package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.EventParam;
import com.qyx.showtick.service.EventAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/18/24
 */
@RestController
@RequestMapping("/event")
public class EventAdminController {
    @Autowired
    private EventAdminService eventAdminService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createEvent(@Validated @RequestBody EventParam eventParam) {
        CommonResult commonResult;
        int count = eventAdminService.createEvent(eventParam);
        if(count == 1){
            commonResult = CommonResult.success(count);
        } else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }
}
