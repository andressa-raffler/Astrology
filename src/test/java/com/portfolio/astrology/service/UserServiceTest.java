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
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


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
        user.setEmail("andressa@email.com");
        user.setName("andressa");
        user.setPassword("senha");
    }

    @Test

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
    @Disabled
    void updateUserById() throws UserNotFoundException {
        underTest.saveUser(userMapper.toDTO(user));
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.of(user));


        UserDTO updatedUser = new UserDTO();
        String email = "novo-andressa@email.com";
        String nome = "novo-andressa";
        String senha = "novo-senha";

        updatedUser.setEmail(email);
        updatedUser.setName(nome);
        updatedUser.setPassword(senha);
        underTest.updateUserById(1L, updatedUser);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).saveAndFlush(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(updatedUser.getEmail()).isEqualTo(capturedUser.getEmail());
        assertThat(updatedUser.getName()).isEqualTo(capturedUser.getName());
        assertThat(updatedUser.getId()).isEqualTo(capturedUser.getId());
        assertThat(updatedUser.getPeople()).isEqualTo(capturedUser.getPeople());
        assertThat(updatedUser.getPassword()).isNotEqualTo(capturedUser.getPassword());



    }

    @Test
    @Disabled
    void findById() {

    }

    @Test
    @Disabled
    void shouldDeleteUser() throws UserNotFoundException {
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.of(user));
        underTest.deleteUserById(id);
        ArgumentCaptor<MessageResponseDTO> messageResponseDTOArgumentCaptor = ArgumentCaptor.forClass(MessageResponseDTO.class);
        verify(userRepository).delete(user);

        MessageResponseDTO messageResponseDTO = underTest.deleteUserById(id);
      // assertThat(messageResponseDTO.getMessage().contains("User with id: "+id+" was deleted!"));
               //. ("User with id: "+id+" was deleted!"));


           //     .hasMessageContaining();

    }

    @Test
    void listAllUsers() {
        underTest.listAllUsers();
        verify(userRepository).findAll();

    }

    @Test
    @Disabled
    void verifyIfUserExists() {
    }

    @Test
    @Disabled
    void testVerifyIfUserExists() {
    }

    @Test
    @Disabled
    void generateToken() {
    }

    @Test
    @Disabled
    void getUserLogged() {
    }
}