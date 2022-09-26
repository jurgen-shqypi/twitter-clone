package com.example.twitterclonebe.security;


import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Autowired
    AppUserService appUserService;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        AppUser user = new AppUser();
        user = appUserService.findById(Long.valueOf(jwt.getSubject())).orElseThrow();
        return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
    }
}
