package pro.it.serverauthjwtbega.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pro.it.serverauthjwtbega.constants.Role;
import pro.it.serverauthjwtbega.model.User;
import pro.it.serverauthjwtbega.repository.UserRepository;

@Service
public class ServiceUserDetails implements UserDetailsService {

    private UserRepository ur;

    @Autowired
    public ServiceUserDetails(UserRepository ur) {
        this.ur = ur;
    }

    @Transactional(readOnly = true,propagation = Propagation.REQUIRES_NEW)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println("Find user : "+ s);
        List<User> ou = ur.findAllByEmailAndRoleIn(s, Role.getRoleEmployers());

        System.out.println("If find user : " + ou.size());

        if( ou.isEmpty() )
            throw new UsernameNotFoundException("User not found");

        User u = ou.stream().findFirst().get();
        System.out.println("user  found: " + u);
        return u;
    }

}
