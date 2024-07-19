package com.qyx.showtick.dto;

//import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Created by Yuxin Qin on 7/19/24
 */
@Data
@EqualsAndHashCode
public class EventDTO {
//    @NotEmpty
    private String name;

    private String description;

//    @NotEmpty
    private LocalDateTime startTime;

//    @NotEmpty
    private LocalDateTime endTime;

//    @NotEmpty
    private String location;

    private String performer;

    private int status;
    private Long categoryId;

    private String posterUrl;
}
