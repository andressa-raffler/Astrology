package com.portfolio.astrology.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
@Component
public class ValidationCode {
    private final int VALIDATION_CODE_LENGTH = 5;
    private final int VALIDATION_CODE_EXPIRATION_MINUTES = 5;
    private LocalDateTime expiration;
    private Integer code;

    public ValidationCode() {
        this.expiration = LocalDateTime.now().plusMinutes(VALIDATION_CODE_EXPIRATION_MINUTES);
    }

}
