package com.hxbd.clp.to.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.swing.Spring;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;


public class TextMessageSender {
	// 发件人的中文昵称
    private static final String nickname = "汉语言学习平台";

    // 发件人在邮箱服务器中的用户名
    private static final String username = "m13558487227@163.com";

    // 发件人在邮箱服务器中的授权码
    private static final String password = "sakura1546";

    // 邮件的发送协议
    private static final String protocol = "smtp";

    // 邮件发送的服务器
    private static final String host = "smtp.163.com";

    // 邮件的监听端口号
    private static final String port = "465";
    
    

    /**
     * 0.收件人邮箱，必填项
     * 1.抄送人邮箱，空值填null
     * 2.密送人邮箱，空值填null
     * 3.邮件的主题
     * 4.邮件的内容
     * @param args
     */
    public void email(String[] args) {
    	
        // 收件人邮箱地址
        String to = args[0];

        // 抄送人邮箱地址
        String cc = args[1];

        // 密送人邮箱地址
        String bcc = args[2];

        // 邮件的主题
        String subject = args[3];

        // 邮件正文的文本内容
        String body = args[4];

        // 封装邮件实体
        Email email = new Email();
        email.setToEmails(to.split(";"));
        email.setCcEmails(cc.split(";"));
        email.setBccEmails(bcc.split(";"));
        email.setSubject(subject);
        email.setContent(body);

        // 发送邮件
        /*if (sendEmail(email)) {
            System.out.println("邮件发送成功!");
        } else {
            System.out.println("邮件发送失败!请及时解决！");
        }*/
    }

    /**
     * 发送邮件
     * @param email 邮件信息实体
     * @return 是否发送成功 TRUE 发送成功 FALSE 发送失败
     */
    public static boolean sendEmail(Email email) {
        try {

            // 创建Session实例对象
            /*
             * Session类用于定义整个JavaMail应用程序所需要的环境信息，
             * 以及收集客户端与邮件服务器建立网络连接会话信息，如邮件
             * 服务器的主机号、端口号、采用的邮件发送和接收的协议等。
             */
            Session session = createSession();

            // 创建MimeMessage实例对象
            MimeMessage message =createMessage(session, email);

            // 发送邮件
            /*
             * send方法执行邮件发送任务时，它首先从参数Message对象中获得Session对象，
             * 然后调用Session.getTransport方法获得用于发送邮件邮件的Transport实例对象，
             * 接着使用保存在Session对象中的与网络链接相关的JavaMail属性，调用Transport
             * 对象的connect方法连接邮件服务器，然后调用Transport对象的sendMessage方法
             * 发送邮件，最后调用close方法断开与邮件服务器的连接。
             */
            Transport.send(message);

            return true;
        } catch (Exception e) {
            System.out.println("发送邮件异常==》");
            e.printStackTrace();
            return false;
        } finally {
            System.out.println("邮件发送结束...");
        }
    }

    /**
     * 创建与邮件服务器的会话对象
     * @return 服务器的会话对象
     */
    public static Session createSession() {
        // 封装属性参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", protocol); // 邮件传输的协议
        props.setProperty("mail.smtp.host", host); // 邮件的服务器
        props.setProperty("mail.smtp.port", port); // 邮件的监听端口
        props.setProperty("mail.smtp.auth", "true"); // 是否需要验证设置为TRUE，使用授权码发送邮件需要验证
        //props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable","true");
            props.put("mail.smtp.ssl.socketFactory",sf);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        // 获取与服务器的会话Session对象
        /*
         * getInstance和getDefaultInstance是Session的静态方法，都可以用来获取Session类的实例对象。
         * 两个方法的区别在于：
         * getDefaultInstance方法返回一个Session对象后，将把这个Session对象设置为默认的Session对象，
         * 以后每一次调用getDefaultInstance方法都会返回这个默认Session对象；
         * 而getInstance方法则是每次调用都会返回一个新的Session对象。
         */
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 登陆账号及密码，密码需要是第三方授权码
                /*
                 * 调用Session对象中注册的Authenticator对象，从中获取的用户认证信息后传递给邮件服务器
                 * PasswordAuthentication根据指定的用户名和密码创建实例对象
                 */
                return new PasswordAuthentication(username, password);
            }
        });

        return session;
    }

    /**
     * 创建邮件的核心内容
     * @param session 与服务器交互的会话对象
     * @param email 邮件实体
     * @return 生成的MimeMessage实例对象
     * @throws Exception
     */
    public static MimeMessage createMessage(Session session, Email email) throws Exception{
        // 创建MimeMessage实例对象：表示整封邮件
        MimeMessage message = new MimeMessage(session);

        // 设置发件人
        if (!nickname.isEmpty()) {
            // 自定义发件人昵称
            message.setFrom(new InternetAddress(MimeUtility.encodeText(nickname) + " <" + username + ">"));
        } else {
            message.setFrom(new InternetAddress(username));
        }

        // 设置收件人
        String[] toEmails = email.getToEmails();
        if (toEmails != null && toEmails.length != 0) {
            // InternetAddress类表示电子邮件的地址
            InternetAddress[] internetAddressTO = new InternetAddress[toEmails.length];
            for (int i = 0; i < toEmails.length; i++) {
                internetAddressTO[i] = new InternetAddress(toEmails[i]);
            }
            // Message.RecipientType表示收件人的类型，它是Message类中的一个静态内部类
            // TO 收件人 CC 抄送人 BCC 密送人
            message.setRecipients(Message.RecipientType.TO, internetAddressTO);
        }

        // 设置密送人
        String[] bccEmails = email.getBccEmails();
        if (bccEmails != null && bccEmails.length != 0 && bccEmails[0]!="") {
            InternetAddress[] internetAddressBCC = new InternetAddress[bccEmails.length];
            for (int i = 0; i < bccEmails.length; i++) {
                internetAddressBCC[i] = new InternetAddress(bccEmails[i]);
            }
            message.setRecipients(Message.RecipientType.BCC, internetAddressBCC);
        }

        // 设置抄送人
        String[] ccEmails = email.getCcEmails();
        if (ccEmails != null && ccEmails.length != 0 && ccEmails[0] != "") {
            InternetAddress[] internetAddressCC = new InternetAddress[ccEmails.length];
            for (int i = 0; i < ccEmails.length; i++) {
                internetAddressCC[i] = new InternetAddress(ccEmails[i]);
            }
            message.setRecipients(Message.RecipientType.CC, internetAddressCC);
        }

        // 设置发生日期
        message.setSentDate(new Date());

        // 设置邮件主题
        if(email.getSubject().equals(null)){
        	message.setSubject("汉语言学习平台");
        }else{
        	message.setSubject(email.getSubject());
        }
        

        // 设置纯文本的邮件内容
        //message.setText(email.getContent());
        message.setContent(email.getContent()+"<a href=\"http://www.hengmu-edu.com/HXCL\">点我进入学习吧</a>"+"，退订请回复T","text/html;charset=gb2312");
        // 保存并生成最终的邮件内容
        message.saveChanges();

        return message;
    }
}
