package com.example.twitterclonebe.service;

import com.example.twitterclonebe.dao.AppUserRepository;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.Optional;

@Service
@Transactional
public class AppUserService implements UserDetailsManager {

    @Autowired
    RoleService roleService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;



    @Override
    public void createUser(UserDetails user) {
        AppUser appUser = (AppUser) user;
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        Role r = roleService.getRole("user");
        appUser.addRole(r);
        appUserRepository.save(appUser);

    }

    public AppUser getCurrentUser() {
       AppUser principal = (AppUser) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return appUserRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Override
    @Transactional
    @Modifying
    public void updateUser(UserDetails user) {
        AppUser userToUpdate = (AppUser) user;
        appUserRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(String username) {
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        AppUser user = getCurrentUser();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new RuntimeException("Passwords don't match!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
    }


    @Override
    public boolean userExists(String username) {
        return appUserRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format("username {0} not found", username)
                ));
    }

    @Transactional
    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }


    public AppUser findByEmail(String email){
        return appUserRepository.findByEmail(email);
    }
}
