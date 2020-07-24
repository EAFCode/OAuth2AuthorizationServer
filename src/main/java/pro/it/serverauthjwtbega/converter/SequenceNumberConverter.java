package pro.it.serverauthjwtbega.converter;

import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import ao.security.key.RSAConfigurer;
import lombok.extern.slf4j.Slf4j;
import pro.it.serverauthjwtbega.configuration.RSACredentials;

@Slf4j
@Convert
public class SequenceNumberConverter implements AttributeConverter<String,String> {

    @Autowired
    private RSACredentials rsaCredentials;

    @PostConstruct
    public void init(){
        log.info("Converter Sequence Number Initializ");
        log.info("Credential public : " + rsaCredentials.getPublicKey());
        log.info("Credential private : " + rsaCredentials.getPrivateKey());
    }

    @Override
    public String convertToDatabaseColumn(String s) {
//        System.out.println("convertendo Number Account : " + s);
        if( Strings.isBlank(s)) return null;
        try {
            // if(  !s.matches("E?\\d+"))
            //     throw new NumberFormatException(s);

           // String data =  Base64.getEncoder().encodeToString( RSAConfigurer.encrypt(s.getBytes(),rsaCredentials.getPrivateKey()) );
//            System.out.println(data);
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(String s) {
//        System.out.println("Retornando  Account number: " + s);
        if( Strings.isBlank(s)) return null;
        try {
         //   String numberAccount = new String( RSAConfigurer.descript(Base64.getDecoder().decode(s),rsaCredentials.getPublicKey()) );

            // if(  !numberAccount.matches("\\d+") )
            // if(  !s.matches("E?\\d+"))
            //     throw new NumberFormatException(s);

            return  s;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
