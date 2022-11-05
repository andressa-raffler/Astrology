package com.portfolio.astrology.service;


import com.portfolio.astrology.model.User;
import com.portfolio.astrology.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private AstrologyService astrologyService;

        public User saveUser(User user){
                return this.userRepository.saveAndFlush(user);
        }

        public User updateUserById(Long id, User updatedUser){
                Optional<User> optionalUser = this.userRepository.findById(id);
                if(optionalUser.isPresent()){
                        User savedUser = getUserFromOptinalSavedUser(updatedUser, optionalUser);
                        return this.userRepository.save(savedUser);
                }
           return null; //melhorar esse retorno
        }


        public User updateUserByName(String name, User updatedUser) {
                Optional<User> optionalSavedUser = this.userRepository.findTopByName(name);
                if(optionalSavedUser.isPresent()){
                        User savedUser = getUserFromOptinalSavedUser(updatedUser, optionalSavedUser);
                        return this.userRepository.save(savedUser);
                }
                return null; //melhorar esse retorno
        }


        public Optional<User> findById(Long id){
                return this.userRepository.findById(id);
        }

        public Optional<User> findByName(String name) {
                return this.userRepository.findTopByName(name);
        }

        public void deleteUserByName(String name) {
                this.userRepository.deleteByName(name);
        }

        public void deleteUserById(Long id) {
                this.userRepository.deleteById(id);
        }

        public List<User> listAllUsers() {
                return this.userRepository.findAll();
        }



        private static User getUserFromOptinalSavedUser(User updatedUser, Optional<User> optionalSavedUser) {
                User savedUser = optionalSavedUser.get();
                savedUser.setName(updatedUser.getName());
                savedUser.setBirthDate(updatedUser.getBirthDate());
                savedUser.setBirthHour(updatedUser.getBirthHour());
                savedUser.setBirthMinute(updatedUser.getBirthMinute());
                savedUser.setCity(updatedUser.getCity());
                savedUser.setState(updatedUser.getState());
                return savedUser;
        }
}
