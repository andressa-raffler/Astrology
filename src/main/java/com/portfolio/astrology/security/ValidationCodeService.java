package com.portfolio.astrology.security;

import com.portfolio.astrology.dto.response.MessageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ValidationCodeService {

    @Autowired
    ValidationCode validationCode;

    public ValidationCode generateValidationCode() {
        Random random = new Random();
        validationCode.setCode(random.nextInt((int) Math.pow(10, validationCode.getVALIDATION_CODE_LENGTH())));
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(validationCode.getVALIDATION_CODE_EXPIRATION_MINUTES());
        validationCode.setExpiration(expiration);
        return validationCode;
    }

    public Boolean isValidationCodeValid(int codeInputed) {
        LocalDateTime now = LocalDateTime.now();
        if(validationCode.getCode() == codeInputed){
            return validationCode.getExpiration().isAfter(now);
        }
        return false;
    }


}
