package com.ufc.diversos.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.ClickTrackingSetting;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.TrackingSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${SENDGRID_API_KEY}") // Pega a chave do ambiente
    private String sendGridApiKey;

    @Value("${spring.mail.username}") // Seu email cadastrado no SendGrid (Sender)
    private String remetente;

    @Value("${api.url:http://localhost:8080}")
    private String apiUrl;

    @Async
    public void enviarEmailConfirmacao(String emailDestino, String token) {
        logger.info("🚀 Iniciando envio via SendGrid para: {}", emailDestino);

        // 1. Configura quem envia e quem recebe
        Email from = new Email(remetente);
        Email to = new Email(emailDestino);
        String subject = "Confirmação de Conta - UFC Diversos";

        // 2. Monta o corpo do email
        String link = apiUrl + "/auth/confirmar?token=" + token;
        String texto = """
                Olá!
                
                Seja bem-vindo à Diver.SOS.
                Para ativar sua conta, clique no link abaixo:
                
                %s
                
                Se você não solicitou, ignore este e-mail.
                """.formatted(link);

        Content content = new Content("text/plain", texto);

        // 3. Cria o objeto de e-mail do SendGrid
        Mail mail = new Mail(from, subject, to, content);

        TrackingSettings trackingSettings = new TrackingSettings();
        ClickTrackingSetting clickTrackingSetting = new ClickTrackingSetting();
        clickTrackingSetting.setEnable(false);
        clickTrackingSetting.setEnableText(false);
        trackingSettings.setClickTrackingSetting(clickTrackingSetting);
        mail.setTrackingSettings(trackingSettings);

        // 4. Prepara o envio
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            // 5. Dispara a requisição HTTP (Aqui não tem bloqueio de porta!)
            Response response = sg.api(request);

            // O SendGrid retorna status 2xx se deu certo
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                logger.info("✅ Email enviado com sucesso via SendGrid! Status: {}", response.getStatusCode());
            } else {
                logger.error("⚠️ Erro no SendGrid. Status: {} | Body: {}", response.getStatusCode(), response.getBody());
            }

        } catch (IOException ex) {
            logger.error("❌ Erro de conexão com SendGrid: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }
}