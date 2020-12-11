package com.resume.api.utils;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.*;

import javax.activation.*;
/**
 * @author lz
 */
public class EmailUtil {

    private int count=1;

    Transport transport=null;

    /**   * 发送邮件

     * @param smtp服务器  例:smtp.exmail.qq.com

     * @param 用户名

     * @param 密码

     * @param 发件人邮箱地址

     * @param 收信人邮箱地址

     * @param 邮件标题

     * @param 邮件正文

     * @param 附件地址

     */

    public boolean sendMail(String smtpServer, String name, String password,    String meMail, String toMail, String mailTitle, String mailText,String fileName) {
        long begin=System.currentTimeMillis();
        // 设置smtp服务器
        Properties props = System.getProperties();
        // 现在的大部分smpt都需要验证了
        props.setProperty("mail.smtp.host", smtpServer);
        props.put("mail.smtp.auth", "true");
        // 为了查看运行时的信息
        Session s = Session.getInstance(props);
        // 由邮件会话新建一个消息对象
        MimeMessage message = new MimeMessage(s);
        try {
            // 发件人
            InternetAddress from = new InternetAddress(meMail);
            message.setFrom(from);
            // 收件人
            InternetAddress to = new InternetAddress(toMail);
            message.setRecipient(Message.RecipientType.TO, to);
            // 邮件标题
            message.setSubject(mailTitle);
            // String content = "测试内容";
            // 邮件内容,也可以使纯文本"text/plain"
            // message.setContent(content, "text/html;charset=GBK");
            // 下面代码是发送附件
            // String fileName = "d://hello.txt";
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailText);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            //上传文件
            if (null != fileName) {
                DataSource source = new FileDataSource(fileName);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName.substring(fileName.lastIndexOf("/")+1));
                multipart.addBodyPart(messageBodyPart);

            }
            message.setContent(multipart);
            message.saveChanges();
            // smtp验证，就是你用来发邮件的邮箱用户名密码
            transport = s.getTransport("smtp");
            // 发送
            transport.connect(smtpServer, name, password);
            transport.sendMessage(message, message.getAllRecipients());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        return false;

    }

    public static void main(String[] args) {

        EmailUtil mail = new EmailUtil();
        mail.sendMail("smtp.exmail.qq.com", "674039309@qq.com", "acxvhawzgpwlbcbd", "674039309@qq.com", "312462719@qq.com", "标题", "内容","E://demo.html");
    }
}
