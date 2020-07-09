package ninja.skyrocketing.robot.entity;

public class SignIn {
    private Long userId;

    private Long groupId;

    private Integer exp;

    //private Integer expTotal;

    private String signInDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

//    public Integer getExpTotal() {
//        return expTotal;
//    }

//    public void setExpTotal(Integer expTotal) {
//        this.expTotal = expTotal;
//    }

    public String getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(String signInDate) {
        this.signInDate = signInDate;
    }
}