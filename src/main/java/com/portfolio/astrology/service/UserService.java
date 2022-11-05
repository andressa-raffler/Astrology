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
                        getUserFromOptionalUser(updatedUser, optionalUser);
                        return this.userRepository.saveAndFlush(updatedUser);
                }
           return null; //melhorar esse retorno
        }


        public User updateUserByName(String name, User updatedUser) {
                Optional<User> optionalUser = this.userRepository.findByName(name);
                if(optionalUser.isPresent()){
                        getUserFromOptionalUser(updatedUser, optionalUser);
                        return this.userRepository.save(updatedUser);
                }
                return null; //melhorar esse retorno
        }

        public Optional<User> findById(Long id){
                return this.userRepository.findById(id);
        }

        public Optional<User> findByName(String name) {
                return this.userRepository.findByName(name);
        }
        public void deleteUserById(Long id) {
                this.userRepository.deleteById(id);
        }

        public List<User> listAllUsers() {
                return this.userRepository.findAll();
        }

        private void getUserFromOptionalUser(User updatedUser, Optional<User> optionalUser) {
                User user = optionalUser.get();
                updatedUser.setId(user.getId());
                updatedUser.setName(user.getName());
                updatedUser.setBirthDate(user.getBirthDate());
                updatedUser.setBirthHour(user.getBirthHour());
                updatedUser.setBirthMinute(user.getBirthMinute());
                updatedUser.setCity(user.getCity());
                updatedUser.setState(user.getState());
                updatedUser.setAstrology(astrologyService.getChartByDate(user.getBirthYear(user.getBirthDate()),user.getBirthMonth(user.getBirthDate()),user.getBirthDay(user.getBirthDate()), user.getBirthHour(), user.getBirthMinute(),
                        user.getCity()+" "+ user.getState(), 15));
        }
}
