package com.portfolio.astrology.service;


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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, passwordEncoder, tokenService);
    }

    @Test
    public void shouldSaveUserTest() {
        User user = new User();
        String email = "andressa@email.com";
        user.setEmail(email);
        user.setName("andressa");
        user.setPassword("senha");

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
    void updateUserById() {
    }

    @Test
    @Disabled
    void findById() {
    }

    @Test
    @Disabled
    void deleteUserById() {
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