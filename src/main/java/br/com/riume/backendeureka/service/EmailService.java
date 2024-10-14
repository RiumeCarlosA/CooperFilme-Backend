package br.com.riume.backendeureka.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String email, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String criarCorpoEmailStatusRoteiro(String nomeCliente, String tituloRoteiro, String novoStatus, String linkRoteiro) {
        return "<html>\n" +
                "<body>\n" +
                "    <table role=\"presentation\" border=\"0\" width=\"100%\">\n" +
                "        <tr>\n" +
                "            <td style=\"background-color: #19538d; width: 100%; height: 65px; text-align: center !important;\">\n" +
                "                <h2 style=\"color: #fff;\">Atualização do Status do Roteiro</h2>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    <div style=\"width: 650px; height: auto; margin-left: 25%;\">\n" +
                "        <p>Olá <b>" + nomeCliente + "</b>,</p>\n" +
                "        <p>O status do seu roteiro <b>\"" + tituloRoteiro + "\"</b> foi atualizado para: <b>" + novoStatus + "</b>.</p>\n" +
                "        <p>Para visualizar os detalhes e acompanhar o progresso, clique no link abaixo:</p>\n" +
                "        <p style=\"text-align: center;\">\n" +
                "            <a href=\"" + linkRoteiro + "\" target=\"_blank\" style=\"display: inline-block; margin: 10px; padding: 12px 25px; color: #ffffff; background-color: #258BE3; text-decoration: none; border-radius: 50px; font-size: 16px; font-weight: bold;\">\n" +
                "                Ver Roteiro\n" +
                "            </a>\n" +
                "        </p>\n" +
                "        <p>Se precisar de mais informações, entre em contato conosco.</p>\n" +
                "        <p>Atenciosamente,</p>\n" +
                "        <p>Equipe AQConnecta.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

}
