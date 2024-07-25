package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.entity.EventCategory;
import com.qyx.showtick.dto.EventAdminDTO;
import com.qyx.showtick.dto.EventCategoryAdminDTO;
import com.qyx.showtick.service.EventAdminService;
import com.qyx.showtick.service.EventCategoryAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/24/24
 */
@RestController
@RequestMapping("/admin/event-category")
public class EventCategoryController {
    @Autowired
    private EventCategoryAdminService eventCategoryAdminService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createEventCategory(@Validated @RequestBody EventCategoryAdminDTO eventCategoryAdminDTO) {
        CommonResult commonResult;
        int count = eventCategoryAdminService.createEventCategory(eventCategoryAdminDTO);
        if(count == 1){
            commonResult = CommonResult.success(count);
        } else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<EventCategory>> getAllEventCategories() {
        List<EventCategory> eventCategories = eventCategoryAdminService.getAllEventCategory();
        return CommonResult.success(eventCategories);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult updateEventCategoryById(@PathVariable Long id, @RequestBody EventCategoryAdminDTO eventCategoryAdminDTO) {
        int count = eventCategoryAdminService.updateEventCategory(id, eventCategoryAdminDTO);
        if(count == 1){
            return CommonResult.success(count);
        }
        return CommonResult.failed("update failed");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getEventCategoryById(@PathVariable Long id) {
        EventCategory eventCategory = eventCategoryAdminService.getEventCategoryById(id);
        if(eventCategory != null){
            return CommonResult.success(eventCategory);
        }
        return CommonResult.failed();
    }
}
