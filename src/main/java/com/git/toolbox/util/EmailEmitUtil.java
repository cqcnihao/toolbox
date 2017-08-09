package com.git.toolbox.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by poan on 2017/08/02.
 */
public class EmailEmitUtil {

    private static Logger logger = LoggerFactory.getLogger(EmailEmitUtil.class);

    @Value("${spring.mail.username}")
    private String userName;

    @Value("${spring.mail.password}")
    private String passWord;

    @Value("${spring.mail.username}")
    private String from;

    private static JavaMailSender sender;

    @Autowired
    private void setJavaMailSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public static void sendMimeMailMessage(String from, String to, String fileName, String path) {


        try {
            // 为防止文件名过长，需要设置该项
            System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
            MimeMessage message = sender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE) + "体检名单-康健德");
            helper.setText("请下载附件");
            FileSystemResource file = new FileSystemResource(new File(path));
            helper.addAttachment(fileName, file);
            sender.send(message);
        } catch (MessagingException e) {
            logger.error("发送预约邮件给[{}]失败", to);
        }


    }
}
