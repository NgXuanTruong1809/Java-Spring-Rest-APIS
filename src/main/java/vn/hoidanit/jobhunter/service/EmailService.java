package vn.hoidanit.jobhunter.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.repository.JobRepository;

@Service
public class EmailService {
    private final MailSender mailSender;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final JobRepository jobRepository;

    public EmailService(MailSender mailSender, JavaMailSender javaMailSender,
            SpringTemplateEngine templateEngine, JobRepository jobRepository) {
        this.mailSender = mailSender;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.jobRepository = jobRepository;
    }

    public void sendSimpleEmail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("anhdeptrai1809@gmail.com");
        msg.setSubject("Testing");
        msg.setText("Hello world from Spring");
        this.mailSender.send(msg);
    }

    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        // Prepare message using a Spring helper
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            this.javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            System.out.println("ERROR SEND EMAIL: " + e);
        }
    }

    @Async // Dat annotation de chay bat dong bo -> ham nay goi tren server vi du tao user
    // neu khong co async nguoi dung phai doi qua trinh create va mail xong -> xong
    // neu co async thi xong create la xong con day la ham gui email -> lam sau tren
    // server

    // phai su dung DTO lay data khong the sudung job.company.name -> lazy Async da
    // luong het session
    public void sendEmailFromTemplateSync(String to, String subject, String templateName, String username,
            Object value) {
        Context context = new Context();
        context.setVariable("name", username);
        context.setVariable("jobs", value);
        String content = this.templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
    }

}
