package com.ufc.diversos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    // Pega a URL do Railway definida nas variáveis de ambiente
    // Se não achar, usa localhost como padrão para testes locais
    @Value("${api.url:http://localhost:8080}")
    private String apiUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envia o email de forma ASSÍNCRONA (em outra thread).
     * O usuário não fica esperando o email ser enviado para ver a tela de sucesso.
     */
    @Async
    public void enviarEmailConfirmacao(String emailDestino, String token) {
        try {
            logger.info("Iniciando envio de e-mail assíncrono para: {}", emailDestino);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(emailDestino);
            message.setSubject("Confirmação de Conta - UFC Diversos");

            // Monta o link apontando para a sua API no Railway
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

            logger.info("✅ E-mail enviado com sucesso para: {}", emailDestino);

        } catch (Exception e) {
            // Se der erro aqui, o usuário JÁ ESTÁ SALVO no banco.
            // Apenas logamos o erro para você ver no painel do Railway.
            logger.error("❌ ERRO CRÍTICO AO ENVIAR EMAIL (Async): {}", e.getMessage());
            e.printStackTrace();
        }
    }
}