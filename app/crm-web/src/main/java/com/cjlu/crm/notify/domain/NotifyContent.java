package com.cjlu.crm.notify.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通知内容
 *
 * @author minghui.y
 * @create 2018-05-15 15:59
 **/
public class NotifyContent {
    /**
     * 标题
     */
    String title;
    /**
     * 基础内容
     */
    String basicContent;
    /**
     * 发送者
     */
    String sender;
    /**
     * 发送时间
     */
    Date sendTime = new Date();

    private static final String PREFIX = "~-\\|-~";

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public NotifyContent() {
    }

    public NotifyContent(String title, String basicContent, String sender) {
        this.title = title == null ? "" : title.replaceAll("\"", PREFIX);
        this.basicContent = basicContent == null ? "" : basicContent.replaceAll("\"", PREFIX);
        this.sender = sender == null ? "" : sender.replaceAll("\"", PREFIX);
    }

    public String getTitle() {
        return title;
    }

    public String getRawContent() {
        return basicContent;
    }

    public String getSender() {
        return sender;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public String getDingDingContent() {

        return "{\n" + "  \"msgtype\": \"text\",\n" + "  \"text\": {\n" + "    \"content\": \"[" +
                title.replaceAll(PREFIX, "\\\\\"") + "]\\n[通知内容] " +
                basicContent.replaceAll(PREFIX, "\\\\\"") + "\\n[通知人] " +
                sender.replaceAll(PREFIX, "\\\\\"") + "\\n[通知时间] " +
                SDF.format(sendTime) + "\\n\"\n" + "  }\n" + "}";
    }

}
