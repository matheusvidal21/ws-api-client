package com.client.ws.api.client.integration;

public interface MailIntegration {

    void send(String mailTo, String subject, String message);

}
