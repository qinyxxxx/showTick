package com.qyx.showtick.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * Created by Yuxin Qin on 7/12/24
 */
@Data
@EqualsAndHashCode
public class EventParam {
    @NotEmpty
    private String name;

    private String description;

    @NotEmpty
    private LocalDateTime startTime;

    @NotEmpty
    private LocalDateTime endTime;

    @NotEmpty
    private String location;

    @NotEmpty
    @Min(value = 0)
    private float price;

    private String performer;

    private int status;
    private Long categoryId;
}
