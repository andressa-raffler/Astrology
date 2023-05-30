package com.portfolio.astrology.response.gpt;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.List;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class GptResponse {
    List<ChoiceResponse> choices;


}
