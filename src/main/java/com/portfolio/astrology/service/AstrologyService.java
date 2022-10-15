package com.portfolio.astrology.service;

import com.portfolio.astrology.model.Astrology;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Getter
@Setter
@Service
public class AstrologyService {
    public Astrology getByDate(LocalDate birthDate) {
        return new Astrology(); //IMPLEMENTAR CHAMADO E CONSUMO DA API APARTIR DA DATA DE NASCIMENTO
    }
}

//83eef92f793aea413bbc692454f1de97f6b992d1568e0a8a30c24a46
//API Key

//https://br.astrologico.org/software/api#examples