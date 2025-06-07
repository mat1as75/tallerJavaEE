package org.tallerJava.transferModule.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.tallerJava.transferModule.domain.DateRange;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class DateRangeDTO {
    //@JsonProperty("start")
    private LocalDate start;
    //@JsonProperty("end")
    private LocalDate end;

    public DateRange buildDateRange() {
        return new DateRange(start, end);
    }
}
