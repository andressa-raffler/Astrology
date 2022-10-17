package com.portfolio.astrology.service;

import com.portfolio.astrology.controller.vo.UserVO;
import com.portfolio.astrology.model.User;
import com.portfolio.astrology.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        public User updateUserById(Long id, UserVO updatedUserVO){
                Optional<User> optionalUser = this.userRepository.findById(id);
                if(optionalUser.isPresent()){
                        User user = optionalUser.get();
                        User updatedUser = new User();
                        updatedUser.setId(user.getId());
                        updatedUser.setName(user.getName());
                        updatedUser.setBirthDate(user.getBirthDate());
                        updatedUser.setCity(user.getCity());
                        updatedUser.setState(user.getState());
                        updatedUser.setAstrology(astrologyService.getByDate(user.getBirthDate()));
                        return this.userRepository.save(updatedUser);
                }
           return null; //melhorar esse retorno
        }

        public Optional<User> findById(Long id){
                return this.userRepository.findById(id);
        }


        public void deleteUserById(Long id) {
                this.userRepository.deleteById(id);
        }

        public List<User> listAllUsers() {
                return this.userRepository.findAll();
        }

        public Integer getDay(LocalDate birthDate){
                return birthDate.getDayOfYear();
        }

        public Integer getMonth(LocalDate birthDate){
                return birthDate.getMonthValue();
        }

        public Integer getYear(LocalDate birthDate){
                return birthDate.getYear();
        }


}
