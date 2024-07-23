package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.SeatCategory;
import com.qyx.showtick.dto.SeatCategoryAdminDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/23/24
 */
public interface SeatCategoryAdminService extends IService<SeatCategory> {
    @Transactional
    int createSeatCategory(SeatCategoryAdminDTO seatCategoryAdminDTO);

    SeatCategory getSeatCategoryById(Long id);

    @Transactional
    int updateSeatCategory(Long id, SeatCategoryAdminDTO seatCategoryAdminDTO);

    List<SeatCategory> getAllSeatCategory();
}
