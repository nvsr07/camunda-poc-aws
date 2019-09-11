package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeHistory {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date when = new Date();
    private String who;
    private String channelId;
    private String applicationId;
}
