package com.jtcwp.purejpa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
