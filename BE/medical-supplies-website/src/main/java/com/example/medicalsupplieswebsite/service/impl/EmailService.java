package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.dto.EmailDetails;
import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.entity.CartDetail;
import com.example.medicalsupplieswebsite.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
public class EmailService implements IEmailService {
    /*
     * Author: NhatLH
     * Created: 2023-08-08
     * */

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Async
    public void emailProcess(Cart cart, long totalAmount, List<CartDetail> details) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                messageHelper.setFrom(sender);
                messageHelper.setTo(cart.getReceiverEmail());
                messageHelper.setSubject("Email xac nhan don hang");

                Locale locale = new Locale("vi", "VN");
                NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

                Context context = new Context();
                context.setVariable("receiverName", cart.getReceiverName());
                context.setVariable("receiverAddress", cart.getReceiverAddress());
                context.setVariable("totalAmount", numberFormat.format(totalAmount));
                context.setVariable("cartDetails", details);
                context.setVariable("locale", new Locale("vi", "VN"));
                String content = templateEngine.process("email-template", context);

                messageHelper.setText(content, true);
            }
        };
        javaMailSender.send(preparator);
    }
}