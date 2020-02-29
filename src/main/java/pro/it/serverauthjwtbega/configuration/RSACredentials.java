package pro.it.serverauthjwtbega.configuration;

import lombok.Data;

@Data
public class RSACredentials {

    private String publicKey;
    private String privateKey;

}
