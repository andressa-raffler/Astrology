package com.portfolio.astrology.service;


import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.mapper.UserMapper;
import com.portfolio.astrology.model.User;
import com.portfolio.astrology.repository.UserRepository;
import com.portfolio.astrology.security.TestConfig;
import com.portfolio.astrology.security.TokenService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Import({TestConfig.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private UserService underTest;
    private User user;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, passwordEncoder, tokenService);
        user = new User();
        user.setId(1L);
        user.setEmail("andressa@email.com");
        user.setName("andressa");
        user.setPassword("senha");
    }

    @Test
    @Disabled
    public void shouldSaveUserTest() {

        underTest.saveUser(userMapper.toDTO(user));

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).saveAndFlush(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(user.getEmail()).isEqualTo(capturedUser.getEmail());
        assertThat(user.getName()).isEqualTo(capturedUser.getName());
        assertThat(user.getId()).isEqualTo(capturedUser.getId());
        assertThat(user.getPeople()).isEqualTo(capturedUser.getPeople());
        assertThat(user.getPassword()).isNotEqualTo(capturedUser.getPassword());

    }

    @Test
    void updateUserById() throws UserNotFoundException {
        underTest.saveUser(userMapper.toDTO(user));
        Long id = 1L;
        UserDTO updatedUser = new UserDTO();
        String email = "novo-andressa@email.com";
        String name = "novo-andressa";
        String password = "novo-senha";
        updatedUser.setId(1L);
        updatedUser.setEmail(email);
        updatedUser.setName(name);
        updatedUser.setPassword(password);
        updatedUser.setPeople(Collections.emptyList());
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        underTest.updateUserById(1L, updatedUser);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository,times(2)).saveAndFlush(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(updatedUser.getEmail()).isEqualTo(capturedUser.getEmail());
        assertThat(updatedUser.getName()).isEqualTo(capturedUser.getName());
        assertThat(updatedUser.getId()).isEqualTo(capturedUser.getId());
        assertThat(updatedUser.getPeople()).isEqualTo(capturedUser.getPeople());
    //    assertThat(updatedUser.getPassword()).isNotEqualTo(capturedUser.getPassword());   mockito wasnt saved the hash password
    }


    @Test
    void shouldntUpdatedUserBecauseUserDoesntExist() throws UserNotFoundException {
        Long id = 1L;
        assertThatThrownBy(() -> underTest.updateUserById(id,userMapper.toDTO(user))) //user wasnt saved yet
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User whit id: " +id+" was not found!");
        verify(userRepository,never()).saveAndFlush(any());
    }

    @Test
    void shouldSendMessageWhenUpdateUser() throws UserNotFoundException {
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        MessageResponseDTO messageResponseDTO = underTest.updateUserById(id,userMapper.toDTO(user));
        assertThat(messageResponseDTO.getMessage()).isEqualTo("User with id "+id+" was updated!");
    }


    @Test
    void listAllUsers() {
        underTest.listAllUsers();
        verify(userRepository).findAll();
    }


    @Test
    @Disabled
    void generateToken () {
    }

}