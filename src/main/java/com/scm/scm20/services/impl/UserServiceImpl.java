package com.scm.scm20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.helper.exceptions.ResourceNotFoundException;
import com.scm.scm20.repositories.UserRepo;
import com.scm.scm20.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {

        //Random id generate for user 
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info("User saved successfully");
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        
        logger.info("User with " + id + " fetched successfully");
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        
       User existingUser= userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not Found"));
        
       existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setPhoneNumberVerified(user.getPhoneNumberVerified());
        existingUser.setEmailVerified(user.getEmailVerified());
        existingUser.setProfilePic(user.getProfilePic());
        existingUser.setGender(user.getGender());
        existingUser.setProvider(user.getProvider());
        existingUser.setProviderId(user.getProviderId());
        existingUser.setEnabled(user.isEnabled());

        User save = userRepo.save(existingUser);

        logger.info("User with " + user.getUserId() + " updated successfully");

        return Optional.ofNullable(save);

       
     
    }

    @Override
    public void deleteUser(String id) {
       
        userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not Found"));
        userRepo.deleteById(id);
        logger.info("User with " + id + " Deleted sucessfully");
        
    }

    @Override
    public boolean isUserExist(String id) {

        return userRepo.findById(id).orElse(null)!=null ? true : false;
    
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        
        return userRepo.findByEmail(email).orElse(null)!=null ? true : false;
        
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching All Users");
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getUserByProviderId(String providerId) {
        logger.info("Fetching data from provider Id {}",providerId);
        return userRepo.findByProviderId(providerId);
    }

    @Override
    public Optional<User> getuserByEmailId(String email) {

        User userEmail = userRepo.findByEmail(email).orElse(null);
         return Optional.ofNullable(userEmail);
    }

}
