package com.esrx.services.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto extends BaseDto implements Serializable, Comparable<StatusDto> {
    private String value;
    private Date effectiveDateTime;
    private Date expirationDateTime;

    @Override
    public int compareTo(StatusDto statusDto) {

        if (this.getEffectiveDateTime() == null || statusDto.getEffectiveDateTime() == null){
            return 0;
        }
        return this.getEffectiveDateTime().compareTo(statusDto.getEffectiveDateTime());
    }
}
