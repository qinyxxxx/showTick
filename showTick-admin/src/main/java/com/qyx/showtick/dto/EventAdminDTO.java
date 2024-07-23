package com.qyx.showtick.dto;

//import jakarta.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * Created by Yuxin Qin on 7/12/24
 */
@Data
@EqualsAndHashCode
public class EventAdminDTO {
    @JsonIgnore
    private Long id;

//    @NotEmpty
    private String name;

    @JsonIgnore
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
