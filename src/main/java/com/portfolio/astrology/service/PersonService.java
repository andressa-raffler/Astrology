package com.portfolio.astrology.service;


import com.portfolio.astrology.dto.request.PersonDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.PersonNotFoundException;
import com.portfolio.astrology.mapper.PersonMapper;
import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.PersonRepository;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.security.TokenService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {


        private PersonRepository personRepository;
        private AstrologyService astrologyService;
        private UserRepository userRepository;
        private UserService userService;
        private TokenService tokenService;

        private final PersonMapper personMapper = PersonMapper.INSTANCE;

        public MessageResponseDTO savePerson(PersonDTO personDTO, HttpServletRequest request){
                Person personToSave = personMapper.toModel(personDTO);
                personToSave.setAstrology(getAstrologyApiPath(personToSave));
                User user = userRepository.findByEmail(getEmailFromToken(request)).get();
                user.addToUsers(personToSave);  //VERIFICAR ESSE CÓDIGO AQUI!!!!
                personToSave.setUser(user);
                Person savedPerson = personRepository.saveAndFlush(personToSave);
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

        public List<PersonDTO> listAllPeople(HttpServletRequest request) {
                String email = getEmailFromToken(request);
                List<Person> allPeoleByUser = personRepository.findAllPersonFromOneUser(email).stream().toList();
                return allPeoleByUser.stream()
                                     .map(personMapper::toDTO)
                                     .collect(Collectors.toList());
        }

        private String getEmailFromToken(HttpServletRequest request) {
                String token = tokenService.getTokenFromRequest(request);
                return  tokenService.getEmailLogado(token);
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
