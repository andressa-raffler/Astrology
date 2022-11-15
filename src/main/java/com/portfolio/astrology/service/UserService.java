package com.portfolio.astrology.service;


import com.portfolio.astrology.dto.request.UserDTO;
import com.portfolio.astrology.dto.response.MessageResponseDTO;
import com.portfolio.astrology.exception.UserNotFoundException;
import com.portfolio.astrology.mapper.UserMapper;
import com.portfolio.astrology.model.*;
import com.portfolio.astrology.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {


        private UserRepository userRepository;
        private AstrologyService astrologyService;

        private final UserMapper userMapper = UserMapper.INSTANCE;

        public MessageResponseDTO saveUser(UserDTO userDTO){
                User userToSave = userMapper.toModel(userDTO);
                userToSave.setAstrology(getAstrologyApiPath(userToSave));
                User savedUser = userRepository.saveAndFlush(userToSave);
                return createMessageRespose("Person with id " + savedUser.getId() + " was created!");
        }


        public MessageResponseDTO updateUserById(Long id, UserDTO updatedUser) throws UserNotFoundException {
                verifyIfUserExists(id);
                User userToUpdate = userMapper.toModel(updatedUser);
                User updatedPerson = userRepository.saveAndFlush(userToUpdate);
                return createMessageRespose("User with id " + id + " was updated!");
        }


        public UserDTO findById(Long id) throws UserNotFoundException {
                User userSaved = verifyIfUserExists(id);
                return userMapper.toDTO(userSaved);
        }

        public MessageResponseDTO deleteUserById(Long id) throws UserNotFoundException {
                User userSaved = verifyIfUserExists(id);
                userRepository.delete(userSaved);
                return MessageResponseDTO
                        .builder()
                        .message("User with id was deleted!")
                        .build();
        }

        public List<UserDTO> listAllUsers() {
                List<User> allUsers = userRepository.findAll();
                return allUsers.stream()
                        .map(userMapper::toDTO)
                        .collect(Collectors.toList());
        }


        public List<String> getZodiacChart(Long id) throws UserNotFoundException {
                User userSaved = verifyIfUserExists(id);
                Zodiac[] zodiacList = Zodiac.values();
                List<Planet> planets;
                List<House> houses;
                List<String> zodiacChart = new ArrayList<>();
                zodiacChart.add("Zodiac Chart from user: "+userSaved.getName());
                planets = userSaved.getAstrology().getPlanets();
                for (Planet planet : planets) {
                        for (Zodiac zodiac : zodiacList) {
                                if (planet.getLongitude() >= zodiac.getMinLongitude() && planet.getLongitude() < zodiac.getMaxLongitude()) {
                                        zodiacChart.add(planet.getName() + ": " + zodiac.getDescription());
                                }
                        }
                }
                houses = userSaved.getAstrology().getHouses();
                for (House house : houses) {
                        for (Zodiac zodiac : zodiacList) {
                                if (house.getLongitude() >= zodiac.getMinLongitude() && house.getLongitude() < zodiac.getMaxLongitude()) {
                                        zodiacChart.add(house.getName() + ": " + zodiac.getDescription());
                                }
                        }
                }

                return zodiacChart;
        }


        private Astrology getAstrologyApiPath(User userToSave) {
                return astrologyService.getChartByDate(userToSave.getBirthYear(userToSave.getBirthDate()), userToSave.getBirthMonth(userToSave.getBirthDate()), userToSave.getBirthDay(userToSave.getBirthDate()), userToSave.getBirthHour(), userToSave.getBirthMinute(),
                        userToSave.getCity() + " " + userToSave.getState(), 15);
        }
        private MessageResponseDTO createMessageRespose(String message){
                return MessageResponseDTO
                        .builder()
                        .message(message)
                        .build();
        }
        private User verifyIfUserExists(Long id) throws UserNotFoundException {
                return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        }

}
