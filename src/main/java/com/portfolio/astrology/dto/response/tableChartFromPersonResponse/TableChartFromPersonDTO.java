package com.portfolio.astrology.dto.response.tableChartFromPersonResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableChartFromPersonDTO {
    List<HTMLRow> htmlRowList = new ArrayList<>();
    String shortDescription;


    public String toJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String htmlRowListString = "";
        try {
            htmlRowListString = objectMapper.writeValueAsString(htmlRowList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return htmlRowListString;
    }
}


