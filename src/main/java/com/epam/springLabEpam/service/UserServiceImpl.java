package com.epam.springLabEpam.service;


import com.epam.springLabEpam.dao.UsersDao;
import com.epam.springLabEpam.dto.UserDto;
import com.epam.springLabEpam.exception.NoSuchUserException;
import com.epam.springLabEpam.model.Role;
import com.epam.springLabEpam.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UsersDao userDao;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UsersDao userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public void signUp(UserDto user, String password) {
        User newUser = User.builder().email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .role(user.getRole())
                .password(password)
                .subscription(user.getSubscription())
                .role(Role.USER).build();
        userDao.save(newUser);
    }

    @Override
    public User signIn(String email, String password) {
        User userFromDB = userDao.findByEmail(email);
        if (userFromDB.getPassword().equals(password))
        return userFromDB;
        else throw new NoSuchUserException();
    }


    @Override
    public void subscribe(UserDto user) {
        String secretWord = "secret";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(secretWord.getBytes());
        String subscriptionKey = Arrays.toString(md.digest());
        User userFromDB = userDao.findByEmail(user.getEmail());
        if (userFromDB.getSubscription().equals(subscriptionKey)){
            System.out.println("Already subscribed");
            return;
        }
        userFromDB.setSubscription(subscriptionKey);
        userDao.update(userFromDB.getId(), userFromDB.getSubscription());
    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            UserDto userDto = UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .email(user.getEmail())
                    .subscription(user.getSubscription())
                    .role(user.getRole())
                    .build();
            userDtoList.add(userDto);
        }
        return  userDtoList;
    }
}
