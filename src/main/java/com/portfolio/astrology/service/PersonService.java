package com.portfolio.astrology.service;


import com.portfolio.astrology.dto.request.PersonDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.PersonNotFoundException;
import com.portfolio.astrology.mapper.PersonMapper;
import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {


        private PersonRepository personRepository;
        private AstrologyService astrologyService;

        private final PersonMapper personMapper = PersonMapper.INSTANCE;

        public MessageResponseDTO savePerson(PersonDTO personDTO){
                Person personToSave = personMapper.toModel(personDTO);
                personToSave.setAstrology(getAstrologyApiPath(personToSave));
                Person savedPerson = personRepository.saveAndFlush(personToSave);
                User user = new User();
                user.addToUsers(personToSave);  //VERIFICAR ESSE CÓDIGO AQUI!!!!
                return createMessageResponse("Person with id " + savedPerson.getId() + " was created!");
        }


        public MessageResponseDTO updatePersonById(Long id, PersonDTO updatedDTOPerson) throws PersonNotFoundException {
                verifyIfPersonExists(id);
                Person personToUpdate = personMapper.toModel(updatedDTOPerson);
                Person updatedPerson = personRepository.saveAndFlush(personToUpdate);
                return createMessageResponse("Person with id " + id + " was updated!");
        }


        public PersonDTO findById(Long id) throws PersonNotFoundException {
                Person personSaved = verifyIfPersonExists(id);
                return personMapper.toDTO(personSaved);
        }

        public MessageResponseDTO deletePersonById(Long id) throws PersonNotFoundException {
                Person personSaved = verifyIfPersonExists(id);
                personRepository.delete(personSaved);
                return MessageResponseDTO
                        .builder()
                        .message("Person with id was deleted!")
                        .build();
        }

        public List<PersonDTO> listAllPeople() {
//                List<Person> allPeople = personRepository.findAll();
//                return allPeople.stream()
//                        .map(personMapper::toDTO)
//                        .collect(Collectors.toList());


                List<Person> allPeoleByUser = personRepository.findAllPersonFromOneUser("email").stream().toList();
                return allPeoleByUser.stream()
                                     .map(personMapper::toDTO)
                                     .collect(Collectors.toList());
        }


        public List<String> getZodiacChart(Long id) throws PersonNotFoundException {
                Person personSaved = verifyIfPersonExists(id);
                Zodiac[] zodiacList = Zodiac.values();
                List<Planet> planets;
                List<House> houses;
                List<String> zodiacChart = new ArrayList<>();
                zodiacChart.add("Zodiac Chart from: "+ personSaved.getName());
                planets = personSaved.getAstrology().getPlanets();
                for (Planet planet : planets) {
                        for (Zodiac zodiac : zodiacList) {
                                if (planet.getLongitude() >= zodiac.getMinLongitude() && planet.getLongitude() < zodiac.getMaxLongitude()) {
                                        zodiacChart.add(planet.getName() + ": " + zodiac.getDescription());
                                }
                        }
                }
                houses = personSaved.getAstrology().getHouses();
                for (House house : houses) {
                        for (Zodiac zodiac : zodiacList) {
                                if (house.getLongitude() >= zodiac.getMinLongitude() && house.getLongitude() < zodiac.getMaxLongitude()) {
                                        zodiacChart.add(house.getName() + ": " + zodiac.getDescription());
                                }
                        }
                }

                return zodiacChart;
        }


        private Astrology getAstrologyApiPath(Person personToSave) {
                return astrologyService.getChartByDate(personToSave.getBirthYear(personToSave.getBirthDate()), personToSave.getBirthMonth(personToSave.getBirthDate()), personToSave.getBirthDay(personToSave.getBirthDate()), personToSave.getBirthHour(), personToSave.getBirthMinute(),
                        personToSave.getCity() + " " + personToSave.getState(), 15);
        }
        private MessageResponseDTO createMessageResponse(String message){
                return MessageResponseDTO
                        .builder()
                        .message(message)
                        .build();
        }
        private Person verifyIfPersonExists(Long id) throws PersonNotFoundException {
                return personRepository.findById(id).orElseThrow(()->new PersonNotFoundException(id));
        }

}
