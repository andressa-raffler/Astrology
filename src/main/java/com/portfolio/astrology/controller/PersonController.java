package com.portfolio.astrology.controller;


import com.portfolio.astrology.dto.request.PersonDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.PersonNotFoundException;
import com.portfolio.astrology.service.AstrologyService;
import com.portfolio.astrology.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    private HttpServletRequest request;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO findPersonById(@PathVariable("id") Long id) throws PersonNotFoundException {
        return personService.findById(id);
    }

    @GetMapping
    public List<PersonDTO> listAllPeople() {
        return personService.listAllPeople(request);
    }

    @GetMapping("/zodiac-chart/{id}")
    public  List<String> getZodiacChart(@PathVariable("id") Long id) throws PersonNotFoundException {
       return personService.getZodiacChart(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
     public MessageResponseDTO saveNewPerson(@RequestBody @Valid PersonDTO personDTO) {
        return personService.savePerson(personDTO, request);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updatePersonById(@PathVariable("id") Long id, @Valid @RequestBody PersonDTO personDTO) throws PersonNotFoundException {
        return personService.updatePersonById(id, personDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageResponseDTO deletePersonById(@PathVariable("id") Long id) throws PersonNotFoundException {
        return personService.deletePersonById(id);
    }


}
