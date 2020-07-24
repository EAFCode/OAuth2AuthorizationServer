package pro.it.serverauthjwtbega.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

import pro.it.serverauthjwtbega.service.ServiceUserDetails;

@Configuration
public class SecurityConfig {

    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        private ServiceUserDetails sud;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        public SecurityConfiguration(ServiceUserDetails serviceUserDetails) {
            this.sud = serviceUserDetails;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(sud).passwordEncoder(passwordEncoder);

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().cors().and().authorizeRequests().antMatchers("/login", "/logout").permitAll()
                    .antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated().and().formLogin();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4);
    }

}




