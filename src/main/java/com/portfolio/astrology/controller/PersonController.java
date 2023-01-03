package com.portfolio.astrology.controller;


import com.portfolio.astrology.dto.request.PersonDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.PersonNotFoundException;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.service.AstrologyService;
import com.portfolio.astrology.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/astrology/v1/person")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {

    PersonService personService;
    AstrologyService astrologyService;
   // private HttpServletRequest request;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO findPersonById(@PathVariable("id") Long id, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        return personService.findById(id, request);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO findPersonById(@PathVariable("name") String name, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        return personService.findByName(name, request);
    }


    @GetMapping("/zodiac-chart")
    public List<String> listAllCharts(HttpServletRequest request) throws UserNotFoundException, PersonNotFoundException {
        return personService.listAllCharts(request);
    }

    @GetMapping("/zodiac-chart/{name}")
    public  List<String> getZodiacChart(@PathVariable("name") String name, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
       return personService.getZodiacChart(name, request);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
     public MessageResponseDTO saveNewPerson(@RequestBody @Valid PersonDTO personDTO, HttpServletRequest request) throws UserNotFoundException {
        return personService.savePerson(personDTO, request);
    }

    @PostMapping("/{bearer}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO saveNewPersonURL(@PathVariable("bearer") String bearer, @RequestBody @Valid PersonDTO personDTO, HttpServletRequest request) throws UserNotFoundException {
        return personService.savePerson(bearer, personDTO, request);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updatePersonById(@PathVariable("id") Long id, @Valid @RequestBody PersonDTO personDTO, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        return personService.updatePersonById(id, personDTO, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponseDTO deletePersonById(@PathVariable("id") Long id, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        return personService.deletePersonById(id, request);
    }


}
