package pro.it.serverauthjwtbega.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

@Configuration
public class AccessTokenConverterConfiguration {
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public DefaultAccessTokenConverter defaultAccessTokenConverter() {
    DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    tokenConverter.setUserTokenConverter(userAuthenticationConverter());
    return tokenConverter;
    }

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter() {
        DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(userDetailsService);
        return converter;
    }

    
}