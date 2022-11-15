package com.portfolio.astrology.dto.request;

import com.portfolio.astrology.model.Astrology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    private LocalDate birthDate;

    private int birthHour;

    private int birthMinute;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String city;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String state;

    private Astrology astrology;

}
