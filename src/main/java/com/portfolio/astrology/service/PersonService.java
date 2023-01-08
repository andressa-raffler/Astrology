package com.portfolio.astrology.service;


import com.portfolio.astrology.dto.request.PersonDTO;
import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.PersonNotFoundException;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.mapper.PersonMapper;
import com.portfolio.astrology.mapper.UserMapper;
import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.PersonRepository;
import com.portfolio.astrology.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {


    private PersonRepository personRepository;
    private AstrologyService astrologyService;
    private UserService userService;
    private TokenService tokenService;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public MessageResponseDTO savePerson(PersonDTO personDTO, HttpServletRequest request) throws UserNotFoundException {
        Person personToSave = personMapper.toModel(personDTO);
        personToSave.setAstrology(getAstrologyApiPath(personToSave));
        User user = userMapper.toModel(getUserDTOFromToken(request));
        user.addToUsers(personToSave);
        personToSave.setUser(user);
        Person savedPerson = personRepository.saveAndFlush(personToSave);
        return createMessageResponse("Person with id " + savedPerson.getId() + " was created!");
    }


    public MessageResponseDTO savePerson(String bearer, PersonDTO personDTO, HttpServletRequest request) throws UserNotFoundException {
        Person personToSave = personMapper.toModel(personDTO);
        personToSave.setAstrology(getAstrologyApiPath(personToSave));
        User user = userMapper.toModel(getUserDTOFromToken(bearer));
        user.addToUsers(personToSave);
        personToSave.setUser(user);
        Person savedPerson = personRepository.saveAndFlush(personToSave);
        return createMessageResponse("Person with id " + savedPerson.getId() + " was created!");
    }



    public MessageResponseDTO updatePersonById(Long personId, PersonDTO updatedDTOPerson, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        Person savedPerson = getPersonFromUser(personId, request);
        Person updatedPerson = personMapper.toModel(updatedDTOPerson);
        savedPerson.setName(updatedPerson.getName());
        savedPerson.setId(personId);
        savedPerson.setBirthDate(updatedPerson.getBirthDate());
        savedPerson.setBirthHour(updatedPerson.getBirthHour());
        savedPerson.setBirthMinute(updatedPerson.getBirthMinute());
        savedPerson.setCity(updatedPerson.getCity());
        savedPerson.setState(updatedPerson.getState());
        savedPerson.setAstrology(getAstrologyApiPath(updatedPerson));
        personRepository.saveAndFlush(savedPerson);
        return createMessageResponse("Person with id " + personId + " was updated!");
    }


    public PersonDTO findById(Long id, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        Person personSaved = getPersonFromUser(id, request);
        return personMapper.toDTO(personSaved);
    }

    public PersonDTO findByName(String name, HttpServletRequest request) throws UserNotFoundException, PersonNotFoundException {
        Person personSaved = getPersonFromUser(name, request);
        return personMapper.toDTO(personSaved);
    }


    public MessageResponseDTO deletePersonById(Long id, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        Person personSaved = verifyIfPersonExists(id, request);
        personRepository.delete(personSaved);
        return MessageResponseDTO
                .builder()
                .message("Person with id was deleted!")
                .build();
    }

    public List<String> listAllCharts(HttpServletRequest request) throws UserNotFoundException, PersonNotFoundException {
        List<Person> allPeoleByUser = getUsersPeopleFromRequest(request);
        List<String> allChartsByUser = new ArrayList<>();
        for (Person person : allPeoleByUser ) {
            List<Object> chart =  getZodiacChart(person.getName(), request);
            allChartsByUser.add(chart.toString());
        }
        return allChartsByUser;
    }






    public List<Object> getZodiacChart(String personName, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        Person personSaved = getPersonFromUser(personName, request);
        Zodiac[] zodiacList = Zodiac.values();
        List<Planet> planets;
        List<House> houses;
        List<Object> zodiacChart = new ArrayList<>();
        planets = personSaved.getAstrology().getPlanets();
        for (Planet planet : planets) {
            for (Zodiac zodiac : zodiacList) {
                if (planet.getLongitude() >= zodiac.getMinLongitude() && planet.getLongitude() < zodiac.getMaxLongitude()) {
                    zodiacChart.add(planet);
                }
            }
        }
        houses = personSaved.getAstrology().getHouses();
        for (House house : houses) {
            for (Zodiac zodiac : zodiacList) {
                if (house.getLongitude() >= zodiac.getMinLongitude() && house.getLongitude() < zodiac.getMaxLongitude()) {
                    zodiacChart.add(house);
                }
            }
        }

        return zodiacChart;
    }




    public List<String> getZodiacChart2(String personName, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        Person personSaved = getPersonFromUser(personName, request);
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
    private Person verifyIfPersonExists(Long id, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        List<Person> personList = getUsersPeopleFromRequest(request);
        return personRepository.findById(id).orElseThrow(()->new PersonNotFoundException(id));
    }

    public List<Person> getUsersPeopleFromRequest(HttpServletRequest request) throws UserNotFoundException {
        UserDTO userDTO = getUserDTOFromToken(request);
        return personRepository.findAllPersonFromOneUser(userDTO.getEmail()).stream().toList();
    }


    private Person getPersonFromUser(String name, HttpServletRequest request) throws UserNotFoundException, PersonNotFoundException {
        List<Person> personList = getUsersPeopleFromRequest(request);
        for (Person person : personList) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        throw new PersonNotFoundException(name);
    }

    private Person getPersonFromUser(long id, HttpServletRequest request) throws UserNotFoundException, PersonNotFoundException {
        List<Person> personList = getUsersPeopleFromRequest(request);
        for (Person person: personList) {
            if (person.getId() == id){
                return person;
            }
        }
        throw new PersonNotFoundException(id);
    }


    private UserDTO getUserDTOFromToken(HttpServletRequest request) throws UserNotFoundException {
        String token = tokenService.getTokenFromRequest(request);
        String email =  tokenService.getEmailLogado(token);
        User userSaved = userService.verifyIfUserExists(email);
        return userMapper.toDTO(userSaved);
    }

    private UserDTO getUserDTOFromToken(String bearer) throws UserNotFoundException {
        String email =  tokenService.getEmailLogado(bearer);
        User userSaved = userService.verifyIfUserExists(email);
        return userMapper.toDTO(userSaved);
    }

}
