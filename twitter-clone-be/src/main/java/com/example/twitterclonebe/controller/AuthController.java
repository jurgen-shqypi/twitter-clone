package com.example.twitterclonebe.controller;

import com.example.twitterclonebe.dto.LoginDTO;
import com.example.twitterclonebe.dto.PasswordChangeRequestDTO;
import com.example.twitterclonebe.dto.SignupDTO;
import com.example.twitterclonebe.dto.TokenDTO;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.mapper.UserMapper;
import com.example.twitterclonebe.security.KeyUtils;
import com.example.twitterclonebe.security.TokenGenerator;
import com.example.twitterclonebe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AppUserService appUserService;
    @Autowired
    TokenGenerator tokenGenerator;
    @Autowired
    DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    KeyUtils keyUtils;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider;
    @Autowired
    UserMapper userMapper;


    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignupDTO signupDTO){
        if(appUserService.findByEmail(signupDTO.getEmail()) != null) {
            return new ResponseEntity(
                    "User with this email already exists", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else if(appUserService.userExists(signupDTO.getUsername())){
            return new ResponseEntity(
                    "User with this username already exists", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        AppUser user = userMapper.signupDTOToUser(signupDTO);
        appUserService.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), user.getAuthorities());
        TokenDTO tokenDTO = tokenGenerator.createToken(authentication);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = null;
        try {
            authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (AuthenticationException e) {
            return new ResponseEntity(
                    "Credentials wrong!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        TokenDTO tokenDTO = tokenGenerator.createToken(authentication);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/token")
    public ResponseEntity token(@RequestBody TokenDTO tokenDTO) {
        Authentication authentication = refreshTokenAuthProvider.authenticate(new BearerTokenAuthenticationToken(tokenDTO.getRefreshToken()));
        return ResponseEntity.ok(tokenGenerator.createToken(authentication));
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@AuthenticationPrincipal AppUser user, @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO){
        if(user.getUsername().equals("test")){
            return new ResponseEntity(
                    "You cant change password of the test account!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        try {
            appUserService.changePassword(passwordChangeRequestDTO.getOldPassword(), passwordChangeRequestDTO.getNewPassword());
        } catch (Exception e) {
            return new ResponseEntity(
                    "Credentials wrong!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

}
