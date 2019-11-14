package com.thy.ssm_email.service;

import javax.mail.MessagingException;

public interface EmailService {

    //发送图片邮件    //String to指定的邮件设置者，这里边事先默认了
     String sendImageMail( String subject, String content, String imgPath,String imgId) throws MessagingException;

     //发送文本文件
     String sendTxtMail(String subject, String content);

     //发送HTML邮件
     String sendHtmlMail(String subject, String content);

     //发送文件附件，与发送图片文件相似
     String sendFileMail(String filePath,String subject,String content);



}
