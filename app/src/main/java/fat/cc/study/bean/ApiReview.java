package fat.cc.study.bean;

import java.io.Serializable;

/**
 * Created by cc on 18/5/14
 */
public class ApiReview implements Serializable {

    private Integer code;

    private Object data;

    private String msg;

    public ApiReview(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public ApiReview() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
