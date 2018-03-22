package org.solar.util;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.security.Security;
import java.util.Base64;
import java.util.Properties;

public class MailSender {

    private String host ; // smtp服务器
    private String from  ; // 发件人地址
    private String account ; // 用户名
    private String password ; // 密码
    private Properties props = new Properties();
    public MailSender(String host, String account, String password) {
        this( host,null,  account, account,   password,  false);
    }

    public MailSender(String host,String port, String from, String account, String password, boolean isSSL) {
        this.host = host;
        this.from = from;
        this.account = account;
        this.password = password;
        // 设置发送邮件的邮件服务器的属性
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
        if (port!=null&&!"".equals(port)){
            props.setProperty("mail.smtp.port", port);
        }
        if (isSSL){
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
        }

    }

    public boolean send(String to,String subject,String content) {
        return send(  to,  subject,  content,null);
    }
    public boolean send(String to,String subject,String content,File file) {
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(false);
        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));
            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 加载标题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);
           //  添加附件
            if (file !=null){
                BodyPart messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                //   添加附件的内容
                messageBodyPart.setDataHandler(new DataHandler(source));
                //    添加附件的标题
                //    这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                String fileName= Base64.getEncoder().encodeToString(file.getName().getBytes());
                messageBodyPart.setFileName("=?utf-8?B?"
                        + fileName + "?=");

                multipart.addBodyPart(messageBodyPart);
            }
           //  将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(host, account, password);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
//      MailSender mailSender = new MailSender("smtp.126.com", "xxx@126.com","xxx");
        MailSender mailSender = new MailSender("smtp.exmail.qq.com","465",
                "service@xxx.cn","service@xxx.cn",
                "xxxxxx",true);
        String filePath="/filePath";
        mailSender.send("xxx@xxx.com", "标题","内容");
        mailSender.send("xxx@xxx.com", "标题","内容",
                new File(filePath));
    }

}
