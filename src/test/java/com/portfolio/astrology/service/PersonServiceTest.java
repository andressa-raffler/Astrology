package com.portfolio.astrology.service;

import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.mapper.PersonMapper;
import com.portfolio.astrology.mapper.UserMapper;
import com.portfolio.astrology.model.Person;
import com.portfolio.astrology.model.User;
import com.portfolio.astrology.repository.PersonRepository;
import com.portfolio.astrology.security.TestConfig;
import com.portfolio.astrology.security.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import com.portfolio.astrology.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@Import({TestConfig.class})
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    AstrologyService astrologyService;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private PersonService underTest;
    private User user;

    private Person person;



    @BeforeEach
    void setUp() {
        underTest = new PersonService(personRepository, astrologyService, userService, tokenService );
        user = new User();
        user.setEmail("andressa@email.com");
        user.setName("andressa");
        user.setPassword("senha");
        person = new Person();
        person.setUser(user);
        person.setUsers(user);
        person.setName("Andressa");
        person.setCity("missal");
        person.setState("parana");
        person.setBirthDate(LocalDate.of(1994,7,16));
        person.setBirthHour(9);
        person.setBirthMinute(30);
    }

    @Test
    @Disabled
    void shouldSavePersonTest() throws UserNotFoundException {
//
//        given(underTest.getUserDTOFromToken(request)).willReturn(userMapper.toDTO(user));
//        underTest.savePerson(personMapper.toDTO(person),request);
//        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
//        verify(personRepository).saveAndFlush(personArgumentCaptor.capture());
//        Person capturedPerson = personArgumentCaptor.getValue();
//
//        assertThat(person.getName()).isEqualTo(capturedPerson.getName());
//        assertThat(person.getUsers()).isEqualTo(capturedPerson.getUsers());
//        assertThat(person.getCity()).isEqualTo(capturedPerson.getCity());
//        assertThat(person.getState()).isEqualTo(capturedPerson.getState());
//        assertThat(person.getBirthDate()).isEqualTo(capturedPerson.getBirthDate());
//        assertThat(person.getBirthHour()).isEqualTo(capturedPerson.getBirthHour());
//        assertThat(person.getBirthMinute()).isEqualTo(capturedPerson.getBirthMinute());
    }


    @Test
    @Disabled
    void updatePersonById() {
    }

    @Test
    @Disabled
    void findById() {
    }

    @Test
    @Disabled
    void findByName() {
    }

    @Test
    @Disabled
    void deletePersonById() {
    }

    @Test
    @Disabled
    void listAllCharts() {
    }

    @Test
    @Disabled
    void getZodiacChart() {
    }

    @Test
    @Disabled
    void getUsersPeopleFromRequest() {
    }
}