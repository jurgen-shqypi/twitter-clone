package com.example.twitterclonebe.service;

import com.example.twitterclonebe.dao.RoleRepository;
import com.example.twitterclonebe.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public Role getRole(String s){
        Role r = roleRepository.getRole(s);
        if(r == null) {
            Role newRole = new Role();
            newRole.setName(s);
            roleRepository.save(newRole);
            r = roleRepository.getRole(s);
        }
        return r;
    }

}
