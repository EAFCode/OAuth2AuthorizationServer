package pro.it.serverauthjwtbega.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pro.it.serverauthjwtbega.constants.Genre;
import pro.it.serverauthjwtbega.constants.Role;
import pro.it.serverauthjwtbega.constants.StatusUser;
import pro.it.serverauthjwtbega.model.User;
import pro.it.serverauthjwtbega.repository.UserRepository;
@Service
public class ServiceUserDetails implements UserDetailsService {

    private UserRepository ur;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ServiceUserDetails(UserRepository ur,PasswordEncoder passwordEncoder) {
        this.ur = ur;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Bean
    public CommandLineRunner init(){
        return args->{
            if( ur.count()==0) {
                User user = new User();
                user.setName("Esaldino Fonseca");
                user.setBi("006050175LA049");
                user.setGenre(Genre.M);
                user.setBirthday(LocalDate.of(1995,12,14));
                user.setEmail("esaldinofonseca@gmail.com");
                user.setRole(Role.ROOT);
                user.setMobile("943553169");
                user.setPassword(passwordEncoder.encode("Gajinho45@"));
                user.setStatus(StatusUser.ACTIVE);
                ur.save(user);
 
            }
        };
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
