package pro.it.serverauthjwtbega.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import pro.it.serverauthjwtbega.model.BasicClientInfo;
import pro.it.serverauthjwtbega.model.ClientAPP;
import pro.it.serverauthjwtbega.model.ClientType;
import pro.it.serverauthjwtbega.model.User;
import pro.it.serverauthjwtbega.service.ServiceEmail;

@Controller
@RequestMapping("/oauth2")
@Slf4j
public class Oauth2ClientController {

    private ClientRegistrationService clientRegistrationService;
    private PasswordEncoder passwordEncoder;
    private ServiceEmail serviceEmail;

    @Autowired
    public Oauth2ClientController(ClientRegistrationService clientRegistrationService, PasswordEncoder passwordEncoder,
            ServiceEmail serviceEmail) {
        this.clientRegistrationService = clientRegistrationService;
        this.passwordEncoder = passwordEncoder;
        this.serviceEmail = serviceEmail;
    }

    @GetMapping("/register")
    public String register(Model model, @AuthenticationPrincipal User user) {
        String[] granttypes = { "client_credentials", "authorization_code", "password", "implicit", "refresh_token" };
        model.addAttribute("grantTypes", granttypes);
        model.addAttribute("registry", new BasicClientInfo());
        return "oauth2/register";
    }

    @PostMapping("/save")
    @Transactional
    public String save(@Valid BasicClientInfo clientDetails, Model model, BindingResult bindingResult)
            throws Exception {
        if (bindingResult.hasErrors()) {
            return "redirect:/oauth2/register";
        }

        ClientAPP app = new ClientAPP();
        System.out.println(clientDetails);

        app.setEmail(clientDetails.getEmail());
        app.setName(clientDetails.getName());
        app.addRedirectUri(clientDetails.getRedirectUri());
        app.setClientType(ClientType.valueOf(clientDetails.getClientType()));
        app.setClientId(UUID.randomUUID().toString());

        String sec = UUID.randomUUID().toString();

        System.out.println("Cliend Id : " +app.getClientId() );
        System.out.println("Cliend Secret : " + sec );
        app.setClientSecret(passwordEncoder.encode(sec));
        app.setGrantTypes(clientDetails.getGrantType());
        app.setAccessTokenValidity(86400);

        for (String scope : clientDetails.getScope().split(","))
            app.addScope(scope.trim());

        String body = "<strong>Application : </strong>" + clientDetails.getName()
                + "<br><strong>Client ID : </strong>: " + app.getClientId() + "<br><strong>Client secret : </strong>"
                + sec + "<br><strong>Authorization Grant type : </strong>" + app.getAuthorizedGrantTypes()
                + "<br><strong>Scopet : </strong>" + clientDetails.getScope();

        serviceEmail.sendEmail(body, clientDetails.getEmail(), "Chave de acesso Prowallet");

        clientRegistrationService.addClientDetails(app);
        model.addAttribute("applications", clientRegistrationService.listClientDetails());

        return "redirect:/oauth2/dashboard";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam(value = "client_id", required = false) String clientId, Model model) {
        clientRegistrationService.removeClientDetails(clientId);
        model.addAttribute("applications", clientRegistrationService.listClientDetails());
        return "redirect:/oauth2/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model mv) {
        mv.addAttribute("applications", clientRegistrationService.listClientDetails());
        return "oauth2/dashboard";
    }

}
