package com.portfolio.astrology.service;


import com.portfolio.astrology.dto.request.PersonDTO;
import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.dto.response.tableChartFromPersonResponse.TableChartFromPersonDTO;
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


import javax.validation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {


    private PersonRepository personRepository;
    private AstrologyService astrologyService;
    private UserService userService;
    private TokenService tokenService;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public MessageResponseDTO savePerson(PersonDTO personDTO, HttpServletRequest request) {
        try {
            MessageResponseDTO listOfViolationsMessageResponseDTO = inputedPersonErrorMessages(personDTO);
            if (listOfViolationsMessageResponseDTO == null) {
                Person personToSave = personMapper.toModel(personDTO);
                personToSave.setAstrology(getAstrologyApiPath(personToSave));
                User user = userMapper.toModel(getUserDTOFromToken(request));
                user.addToUsers(personToSave);
                personToSave.setUser(user);
                personToSave.getAstrology().setShortDescription( astrologyService.getAstrologyShortDescriptionFromGptResponse(personMapper.toDTO(personToSave)));
                Long idSavedPerson = saveAndFlushPerson(personToSave);
                return createMessageResponse("Person with id " + idSavedPerson + " was created!");
            }
            return listOfViolationsMessageResponseDTO;
        } catch (UserNotFoundException | PersonNotFoundException e) {
            return createMessageResponse("New person can't be saved");
        }
    }

    private Long saveAndFlushPerson(Person personToSave) {
        Person savedPerson = personRepository.saveAndFlush(personToSave);
        return savedPerson.getId();
    }


    private MessageResponseDTO inputedPersonErrorMessages(PersonDTO personDTO) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> violations = validator.validate(personMapper.toModel(personDTO));
        if (!violations.isEmpty()) {
            List<String> errorMessages = new ArrayList<>();
            for (ConstraintViolation<Person> violation : violations) {
                errorMessages.add(violation.getMessage());
            }
           return createMessageResponse(errorMessages.toString());
        }
        return null;
    }


    public MessageResponseDTO updatePersonById(Long personId, PersonDTO updatedDTOPerson, HttpServletRequest request) {
        try {
            MessageResponseDTO listOfViolationsMessageResponseDTO = inputedPersonErrorMessages(updatedDTOPerson);
            if(listOfViolationsMessageResponseDTO == null){
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
                astrologyService.saveAstrologyShortDescription(personMapper.toDTO(savedPerson));
                Long idUpdatedPerson = saveAndFlushPerson(savedPerson);
                return createMessageResponse("Person with id " + idUpdatedPerson + " was updated!");
            }
            return listOfViolationsMessageResponseDTO;
        }catch (NullPointerException | UserNotFoundException | PersonNotFoundException e){
            return createMessageResponse("Invalid Data. Please verify your information");
        }
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
            String chart =  getZodiacChart(person.getName(), request).toString();
            allChartsByUser.add(chart);
        }
        return allChartsByUser;
    }

    public TableChartFromPersonDTO getZodiacChart(String personName, HttpServletRequest request) throws PersonNotFoundException, UserNotFoundException {
        Person personSaved = getPersonFromUser(personName, request);
        return astrologyService.getTableChartWithShortDescriptionFromPerson(personMapper.toDTO(personSaved));
    }


    private Astrology getAstrologyApiPath(Person personToSave) {
        return astrologyService.getChartByDate(personToSave.getBirthYear(personToSave.getBirthDate()),
                                               personToSave.getBirthMonth(personToSave.getBirthDate()),
                                               personToSave.getBirthDay(personToSave.getBirthDate()),
                                               personToSave.getBirthHour(), personToSave.getBirthMinute(),
                                   personToSave.getCity() + " " + personToSave.getState(),
                                        15);
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


    public UserDTO getUserDTOFromToken(HttpServletRequest request) throws UserNotFoundException {
        String token = tokenService.getTokenFromRequest(request);
        String email =  tokenService.getEmailLogado(token);
        User userSaved = userService.getUserFromRepository(email);
        return userMapper.toDTO(userSaved);
    }

    private UserDTO getUserDTOFromToken(String bearer) throws UserNotFoundException {
        String email =  tokenService.getEmailLogado(bearer);
        User userSaved = userService.getUserFromRepository(email);
        return userMapper.toDTO(userSaved);
    }

    public String getAstrologyShortDescription(Long id, HttpServletRequest request) throws UserNotFoundException, PersonNotFoundException {
        PersonDTO personDTO = personMapper.toDTO(getPersonFromUser(id, request));
        return astrologyService.getAstrologyShortDescriptionFromGptResponse(personDTO);
    }


}
