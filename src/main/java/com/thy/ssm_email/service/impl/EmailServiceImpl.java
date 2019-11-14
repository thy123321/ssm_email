package com.thy.ssm_email.service.impl;

import com.thy.ssm_email.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数
    @Value("${spring.mail.receiver}")
    private String Receiver;
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public String sendImageMail(String subject, String content, String imgPath, String imgId) throws MessagingException {
        //创建message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //发件人
            helper.setFrom(Sender);
            //收件人
            helper.setTo(Receiver);
            //标题
            helper.setSubject(subject);
            //true指的是html邮件，false指的是普通文本
            helper.setText(content, true);
            //添加图片
            FileSystemResource file = new FileSystemResource(new File(imgPath));
            helper.addAttachment(imgId, file);
            //发送邮件
            try {
                mailSender.send(message);
                log.info("图片附件文件成功发送");
            } catch (Exception e) {
                log.error("图片附件文件发送异常，请重试");
            }
            return "success";
        }

    @Override
    public String sendTxtMail(String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);
        message.setTo(Receiver);
        message.setSubject(subject);// 标题
        message.setText(content);// 内容
        try {
            mailSender.send(message);
            log.info("文本邮件发送成功！");
            return "success";
        } catch (Exception e) {
            log.error("文本邮件发送异常！", e);
            return "failure";
        }
    }

    @Override
    public String sendHtmlMail(String subject, String content) {
       // String content = "<html><body><h3>hello world ! --->Html邮件</h3></body></html>";
        MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(Receiver);
            helper.setSubject(subject);
            helper.setText(content, true);

          mailSender.send(message);
            log.info("Html邮件发送成功！");
            return "success";
        } catch (MessagingException e) {
            log.error("Html邮件发送异常！", e);
            return "failure";
        }


    }

    @Override
    public String sendFileMail(String filePath, String subject, String content) {
       // String filePath = "F:\\select_from_user.sql";
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(Receiver);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            log.info("附件邮件发送成功！");
            return "success";
        } catch (MessagingException e) {
            log.error("附件邮件发送异常！", e);
            return "failure";
        }

    }
}
