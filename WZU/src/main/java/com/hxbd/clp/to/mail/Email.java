package com.hxbd.clp.to.mail;

import java.io.File;
import java.util.Map;

public class Email {
	// 收件人
    private String[] toEmails;

    // 抄送人
    private String[] ccEmails;

    // 密送人
    private String[] bccEmails;

    // 邮件主题
    private String subject;

    // 邮件内容
    private String content;

    // 附件
    private String[] attachments;

    // 内嵌图片
    private Map<String, String> imagesMap;

    // ----------- getter/setter -----------
    public String[] getToEmails() {
        return toEmails;
    }

    public void setToEmails(String[] toEmails) {
        this.toEmails = toEmails;
    }

    public String[] getCcEmails() {
        return ccEmails;
    }

    public void setCcEmails(String[] ccEmails) {
        this.ccEmails = ccEmails;
    }

    public String[] getBccEmails() {
        return bccEmails;
    }

    public void setBccEmails(String[] bccEmails) {
        this.bccEmails = bccEmails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

    public Map<String, String> getImagesMap() {
        return imagesMap;
    }

    public void setImagesMap(Map<String, String> imagesMap) {
        this.imagesMap = imagesMap;
    }
}
