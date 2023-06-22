package com.portfolio.astrology.request.gpt;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class RequestBody {
    private String model;
    private int max_tokens;
    private List<Message> messages;


}
