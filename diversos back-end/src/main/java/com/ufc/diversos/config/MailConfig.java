package com.ufc.diversos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // CONFIGURAÇÃO MANUAL E BLINDADA
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465); // Forçando porta 465
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();

        // Protocolo SMTPS (Seguro desde o início)
        props.put("mail.transport.protocol", "smtps");

        // Autenticação e SSL
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.ssl.enable", "true");
        props.put("mail.smtps.ssl.trust", "smtp.gmail.com"); // Confia no Google

        // Timeouts aumentados para evitar queda na rede do Railway
        props.put("mail.smtps.connectiontimeout", "10000"); // 10s
        props.put("mail.smtps.timeout", "10000");          // 10s
        props.put("mail.smtps.writetimeout", "10000");     // 10s

        // Debug: vai mostrar a conversa com o Google no console se der erro
        props.put("mail.debug", "true");

        return mailSender;
    }
}