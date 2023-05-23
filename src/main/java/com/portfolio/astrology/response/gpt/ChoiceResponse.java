package com.portfolio.astrology.response.gpt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ChoiceResponse {
    private MessageResponse message;
}
