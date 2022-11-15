package com.portfolio.astrology.dto.request;

import com.portfolio.astrology.model.Astrology;
import com.portfolio.astrology.model.Planet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AstrologyDTO {


    private Long id;

    @Valid
    private List<PlanetDTO> planets;

}
