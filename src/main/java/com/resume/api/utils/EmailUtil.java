package com.resume.api.utils;

import com.sun.mail.util.MailSSLSocketFactory;

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

    /**
     * QQ邮箱调用接口
     * @param to 收件人邮箱
     * @param title 邮件标题
     * @param content 邮件内容
     * @param fileName 附加文件地址
     * @throws Exception
     */
    public static void sendMail(String to,String title,String content, String fileName) throws Exception{
        //设置发送邮件的主机  smtp.qq.com
        String host = "smtp.qq.com";
        //1.创建连接对象，连接到邮箱服务器
        Properties props = System.getProperties();
        //Properties 用来设置服务器地址，主机名 。。 可以省略
        //设置邮件服务器
        props.setProperty("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        //SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        //props：用来设置服务器地址，主机名；Authenticator：认证信息
        Session session = Session.getDefaultInstance(props,new Authenticator() {
            @Override
            //通过密码认证信息
            protected PasswordAuthentication getPasswordAuthentication() {
                //这个用户名密码就可以登录到邮箱服务器了,用它给别人发送邮件
                return new PasswordAuthentication("312462719@qq.com","kgbhmdktgndfbhga");
            }
        });
        try {
            Message message = new MimeMessage(session);
            //1设置发件人：
            message.setFrom(new InternetAddress("312462719@qq.com"));
            //2设置收件人 这个TO就是收件人
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            //3邮件的主题
            message.setSubject(title);
            //4设置邮件的正文 第一个参数是邮件的正文内容 第二个参数是：是文本还是html的连接
            message.setContent(content, "text/html;charset=UTF-8");
            if (null != fileName) {
                DataSource source = new FileDataSource(fileName);
                message.setDataHandler(new DataHandler(source));
                message.setFileName(fileName.substring(fileName.lastIndexOf("/")+1));
            }
            //3.发送一封激活邮件
            Transport.send(message);
        }catch(MessagingException mex){
            mex.printStackTrace();
        }
    }



    public static void main(String[] args) throws Exception {
        EmailUtil.sendMail("312462719@qq.com","言职青年","您的PDF文件已经生成","E://1000000229100586.pdf");
        //mail.sendMail("smtp.exmail.qq.com", "312462719@qq.com", "kgbhmdktgndfbhga", "312462719@qq.com", "674039309@qq.com", "标题", "内容","E://demo.html");
    }
}
