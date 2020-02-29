package pro.it.serverauthjwtbega.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;
import java.util.stream.Collectors;

public class ClientAPP implements ClientDetails {
    private String clientId;
    private String clientSecret;
    private ClientType clientType;
    private Set<String> resourceIds = new HashSet<>();
    private Set<String> scope = new HashSet<>();
    private Set<String> webServerRedirectUri = new HashSet<>();
    private int accessTokenValidity;
    private Map<String, Object> additionalInformation = new
            HashMap<>();
    private Set<String> grantTypes;

    public void setName(String name) {
        additionalInformation.put("name", name);
    }

    public void setEmail(String email){additionalInformation.put("email",email);}

    public void setClientType(ClientType clientType) {
        additionalInformation.put("client_type", clientType.name());
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public void setAccessTokenValidity(int accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public void setGrantTypes(Set<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    public void addRedirectUri(String redirectUri) {
        this.webServerRedirectUri.add(redirectUri);
    }
    public void addScope(String scope) {
        this.scope.add(scope);
    }
    public void addResourceId(String resourceId) {
        this.resourceIds.add(resourceId);
    }

    public String getClientId()
    { return clientId; }
    public Set<String> getResourceIds()
    { return resourceIds; }
    public boolean isSecretRequired()
    { return clientType == ClientType.CONFIDENTIAL; }
    public String getClientSecret()
    { return clientSecret; }
    public boolean isScoped()
    { return scope.size() > 0; }
    public Set<String> getScope()
    { return scope; }
    public Set<String> getRegisteredRedirectUri()
    { return webServerRedirectUri; }
    public Collection<GrantedAuthority> getAuthorities()
    { return this.scope.stream().map(scope->new SimpleGrantedAuthority(scope)).collect(Collectors.toList()); }
    public Integer getAccessTokenValiditySeconds()
    { return accessTokenValidity; }
    public Integer getRefreshTokenValiditySeconds()
    { return null; }
    public boolean isAutoApprove(String scope)
    { return false; }
    public Map<String, Object> getAdditionalInformation()
    { return additionalInformation; }
    public Set<String> getAuthorizedGrantTypes(){
        return this.grantTypes;
    }

}
