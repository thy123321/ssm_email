package com.thy.ssm_email.service.controller;

import com.thy.ssm_email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;
    @RequestMapping("/sendImage")
     public String  sendImageMail( String subject, String content, String imgPath,String imgId) throws MessagingException {
        return emailService.sendImageMail("springboot后台实现发图片"
                 ,"这是一张图片","C://Users//Administrator//Desktop//3.jpg"
                  ,"龙舟id001");
     }
}
