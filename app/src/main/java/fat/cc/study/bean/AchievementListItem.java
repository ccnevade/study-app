package fat.cc.study.bean;

import java.math.BigDecimal;

public class AchievementListItem {

    private String courseName;

    private BigDecimal achievement;

    public AchievementListItem() {
    }

    public AchievementListItem(String courseName, BigDecimal achievement) {
        this.courseName = courseName;
        this.achievement = achievement;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigDecimal getAchievement() {
        return achievement;
    }

    public void setAchievement(BigDecimal achievement) {
        this.achievement = achievement;
    }
}
