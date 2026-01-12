package com.ufc.diversos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.scheduling.annotation.Async; <--- REMOVA OU COMENTE ISSO
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    // Configura a URL do Backend (Railway) ou Frontend (Render) via variável
    // Se não tiver a variável, usa localhost como fallback, mas ideal é configurar no Railway
    @Value("${api.url:https://deploy-diversos-production.up.railway.app}")
    private String apiUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // RETIRE O @Async PARA O ERRO APARECER NA HORA
    // public void enviarEmailConfirmacao(String emailDestino, String token) {

    // Vamos tirar o try-catch DAQUI e deixar o UsuarioService tratar o erro
    // Adicione 'throws Exception' na assinatura
    public void enviarEmailConfirmacao(String emailDestino, String token) throws Exception {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(remetente);
        message.setTo(emailDestino);
        message.setSubject("Confirmação de Conta - UFC Diversos");

        // CORREÇÃO DO LINK: Agora aponta para o Railway, não localhost
        String link = apiUrl + "/auth/confirmar?token=" + token;

        String texto = """
                Olá!
                
                Seja bem-vindo à Diver.SOS.
                Para ativar sua conta e liberar seu acesso, clique no link abaixo:
                
                %s
                
                Se você não solicitou este cadastro, apenas ignore este e-mail.
                """.formatted(link);

        message.setText(texto);

        mailSender.send(message);
        logger.info("E-mail de confirmação enviado com sucesso para: {}", emailDestino);
    }
}