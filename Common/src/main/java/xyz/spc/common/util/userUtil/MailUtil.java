package xyz.spc.common.util.userUtil;

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Properties;

/**
 * 邮箱工具类
 */
@Slf4j
public class MailUtil {
    // 发件人的邮箱账号, 如 xxx@163.com
    public static String sendEmailAccount = "spadekx@gmail.com";
    // 发件人的邮箱的授权码 (自己在邮箱服务器中开启并设置)
    public static String sendEmailPassword = "XXXXXXX";
    // 发件人邮箱的SMTP服务器地址 如 smtp.163.com
    public static String sendEmailSMTPHost = "smtp.163.com";
    // 收件人的邮箱账号
    public static String receiveMailAccount = "";

    /**
     * 创建邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,
                                                String mailContent, String sender, String title, String receiver) throws Exception {
        // 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 设置发件人姓名和编码格式
        message.setFrom(new InternetAddress(sendMail, sender, "UTF-8"));

        // 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiver, "UTF-8"));

        // 设置邮件主题
        message.setSubject(title, "UTF-8");

        // 设置邮件正文
        message.setContent(mailContent, "text/html;charset=UTF-8");

        // 设置发件时间
        message.setSentDate(new Date());

        message.saveChanges();
        return message;
    }

    /**
     * 参数设置
     */
    @NotNull
    private static Properties getProperties() {
        Properties props = new Properties();
        // 设置使用SMTP协议
        props.setProperty("mail.transport.protocol", "smtp");
        // 设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", sendEmailSMTPHost);
        // 设置需要验证
        props.setProperty("mail.smtp.auth", "true");

        //Linux端口25没有开放，放弃25端口，使用ssl加密并改用465端口--此举可以兼顾window和Linux
        //使用ssl加密
        props.setProperty("mail.smtp.ssl.enable", "true");
        //设置端口
        props.setProperty("mail.smtp.port", "465");
        return props;
    }

    /**
     * 发送邮件
     */
    public void sendMail(String receiveMailAccount, String mailContent, String sender, String title, String receiver) {
        // 创建用于连接邮件服务器的参数配置
        Properties props = getProperties();

        // 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置debug模式，便于查看发送过程所产生的日志
        session.setDebug(true);

        try {
            // 创建一封邮件
            MimeMessage message = createMimeMessage(session, sendEmailAccount, receiveMailAccount, mailContent, sender, title, receiver);

            // 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            transport.connect(sendEmailAccount, sendEmailPassword);

            // 发送邮件, 发到所有的收件地址
            transport.sendMessage(message, message.getAllRecipients()); //通过message.getAllRecipients() 可以获取到在创建邮件对象时添加的所有收件人
            transport.close();

        } catch (Exception e) {
            log.error("邮件发送失败", e);
        }
    }


}
