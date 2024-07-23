package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.SeatCategory;
import com.qyx.showtick.common.mapper.SeatCategoryMapper;
import com.qyx.showtick.dto.SeatCategoryAdminDTO;
import com.qyx.showtick.mapper.SeatCategoryAdminDTOMapper;
import com.qyx.showtick.service.SeatCategoryAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/23/24
 */
@Service
public class SeatCategoryAdminServiceImpl extends ServiceImpl<SeatCategoryMapper, SeatCategory> implements SeatCategoryAdminService {
    @Autowired
    private SeatCategoryMapper seatCategoryMapper;

    @Override
    public int createSeatCategory(SeatCategoryAdminDTO seatCategoryAdminDTO) {
        SeatCategory seatCategory = SeatCategoryAdminDTOMapper.INSTANCE.toEntity(seatCategoryAdminDTO);
        return seatCategoryMapper.insert(seatCategory);
    }

    @Override
    public SeatCategory getSeatCategoryById(Long id) {
        return seatCategoryMapper.selectById(id);
    }

    @Override
    public int updateSeatCategory(Long id, SeatCategoryAdminDTO seatCategoryAdminDTO) {
        SeatCategory seatCategory = SeatCategoryAdminDTOMapper.INSTANCE.toEntity(seatCategoryAdminDTO);
        seatCategory.setId(id);
        return seatCategoryMapper.updateById(seatCategory);
    }

    @Override
    public List<SeatCategory> getAllSeatCategory() {
        return this.list();
    }
}
