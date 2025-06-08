package org.tallerJava.transferModule.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DateRange {
    private LocalDate start;
    private LocalDate end;

    public DateRange() {}

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
}
