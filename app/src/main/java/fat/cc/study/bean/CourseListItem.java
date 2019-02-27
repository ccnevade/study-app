package fat.cc.study.bean;

import java.math.BigDecimal;

public class CourseListItem {

    private Integer courseId;

    private String courseName;

    private String introduction;

    private BigDecimal fee;

    public CourseListItem() {
    }

    public CourseListItem(Integer courseId, String courseName, String introduction, BigDecimal fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.introduction = introduction;
        this.fee = fee;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
