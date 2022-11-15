package com.portfolio.astrology.dto.request;

import com.portfolio.astrology.model.Astrology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanetDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @NotEmpty
    private Long longitude;

    @NotEmpty
    private Long longitudeSpeed;

    private Astrology astrology;

}
