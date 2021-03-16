package ninja.skyrocketing.bot.fuyao.pojo.group;

import java.util.Date;

/**
 * @author skyrocketing Hong
 */

public class GroupTimelyMessage {
    private Long groupId;

    private Long userId;

    private String messageString;

    private Date sendTime;

    private Boolean enabled;

    public GroupTimelyMessage(Long groupId, Long userId, String messageString, Date sendTime, Boolean enabled) {
        this.groupId = groupId;
        this.userId = userId;
        this.messageString = messageString;
        this.sendTime = sendTime;
        this.enabled = enabled;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getMessageString() {
        return messageString;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}