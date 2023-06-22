package com.portfolio.astrology.controller;

import com.portfolio.astrology.request.email.EmailMessage;
import com.portfolio.astrology.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/astrology/v1/mail")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmailMessage emailMessage){
        this.emailSenderService.sendEmail(emailMessage.getTo(),emailMessage.getMessage());
        return ResponseEntity.ok("Success");
    }
}
