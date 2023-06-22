package com.portfolio.astrology.request.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailMessage {
    private String to;
    private String subject;
    private String message;
}
