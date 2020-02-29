package pro.it.serverauthjwtbega.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import pro.it.serverauthjwtbega.service.ServiceUserDetails;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private ServiceUserDetails sud;
    // @Value("${signature.jwt.auth}") private String value;

    @Autowired
    public OAuth2AuthorizationServer(DataSource dataSource, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            ServiceUserDetails sud) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.sud = sud;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.jdbc(dataSource);
    }

    
    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    // @Bean
    // public JwtAccessTokenConverter accessTokenConverter() {
    //     JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    //     converter.setSigningKey(value);
    //     return converter;
    // }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            converter.setKeyPair(keyPair);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return converter;
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        .authenticationManager(authenticationManager)
        .tokenStore(jwtTokenStore())
        .userDetailsService(sud)
        .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
       // security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("permitAll()");
        security.passwordEncoder(passwordEncoder);
    }

    //responsavel por registrar os clientes auth
    @Bean
    public ClientRegistrationService clientRegistrationService() {
        return new JdbcClientDetailsService(dataSource);
    }

}
