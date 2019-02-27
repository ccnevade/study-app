package fat.cc.study.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Course implements Serializable {

    private Integer id;

    private Date createTime;

    //所属教师id
    private Integer teacherId;

    private String name;

    //简介
    private String introduction;

    //本课程学费
    private BigDecimal fee;

    private List<CourseFiles> courseFilesList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<CourseFiles> getCourseFilesList() {
        return courseFilesList;
    }

    public void setCourseFilesList(List<CourseFiles> courseFilesList) {
        this.courseFilesList = courseFilesList;
    }
}




