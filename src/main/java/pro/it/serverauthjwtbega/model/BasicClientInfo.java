package pro.it.serverauthjwtbega.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Set;

@Data
public class BasicClientInfo {

    @NotBlank
    private String name;

    private String redirectUri;

    @NotBlank
    private String clientType;

    @Email
    private String email;

    @Size(min = 1,max = 4)
    private Set<String> grantType;

    @NotBlank
    private String scope;

    @Override
    public String toString() {
        return "BasicClientInfo{" +
                "name='" + name + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", clientType='" + clientType + '\'' +
                ", grantType=" + grantType +
                ", scope=" + scope +
                '}';
    }

}
