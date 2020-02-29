package pro.it.serverauthjwtbega.service;

public interface ServiceEmail {
    void sendEmail(String body,String to, String subject) throws Exception;
}
