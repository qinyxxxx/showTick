package com.qyx.showtick.service;

import com.qyx.showtick.common.dto.EventParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/18/24
 */
public interface EventAdminService {
    @Transactional
    int createEvent(EventParam eventParam);
}
