package pro.it.serverauthjwtbega.converter;

import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import ao.security.key.RSAConfigurer;
import lombok.extern.slf4j.Slf4j;
import pro.it.serverauthjwtbega.configuration.RSACredentials;

@Slf4j
@Converter
public class StringConverter implements AttributeConverter<String,String> {

    @Autowired
    private RSACredentials rsaCredentials;

    @PostConstruct
    public void init(){
        log.info("Credential public : " + rsaCredentials.getPublicKey());
        log.info("Credential private : " + rsaCredentials.getPrivateKey());
    }


    @Override
    public String convertToDatabaseColumn(String s) {
        // System.out.println("convertendo : " + s);
        if( Strings.isBlank(s)) return null;
        try {
            String data =  Base64.getEncoder().encodeToString( RSAConfigurer.encrypt(s.getBytes(),rsaCredentials.getPrivateKey()) );
//            System.out.println(data);
            return data;
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return null;
        }
    }


    @Override
    public String convertToEntityAttribute(String s) {
        // System.out.println("Retornando : " + s);
        if( Strings.isBlank(s)) return null;
        try {
    //        if(Strings.isBlank(s)) throw  new NullPointerException();
            return  new String( RSAConfigurer.descript(Base64.getDecoder().decode(s),rsaCredentials.getPublicKey()));
        } catch (Exception ex){
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
            return null;
        }

    }


}
