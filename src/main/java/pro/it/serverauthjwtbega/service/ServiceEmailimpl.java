package pro.it.serverauthjwtbega.service;

import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ServiceEmailimpl implements ServiceEmail {

    @Value("${api.sendgrid.key}")
    private String sengridkey;

    @Value("${api.sendgrid.from}")
    private String sengridfrom;

    @PostConstruct
    private void init(){
        log.info("SendGrid key : " + sengridkey);
        log.info("SendGrid from : " + sengridfrom);
    }

    @Override
    public void sendEmail(String body, String toEmail, String subject) throws Exception {
        Email from = new Email(sengridfrom);
        Email to = new Email(toEmail);
        Content content = new Content("text/html", body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sengridkey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        System.out.println(response.getHeaders());
    }
    

}
