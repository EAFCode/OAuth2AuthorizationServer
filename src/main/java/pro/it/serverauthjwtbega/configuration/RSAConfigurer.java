package pro.it.serverauthjwtbega.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
@PropertySource("classpath:encryption.properties")
public class RSAConfigurer {

    @Value("${rsa.key.public}")
    private String publicKey;

    @Value("${rsa.key.private}")
    private String privateKey;

    @Bean
    public RSACredentials initCredential(){
        RSACredentials rsaCredentials = new RSACredentials();
        rsaCredentials.setPublicKey(publicKey);
        rsaCredentials.setPrivateKey(privateKey);
        return  rsaCredentials;
    }

}
