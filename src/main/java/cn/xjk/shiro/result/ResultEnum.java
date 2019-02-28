package cn.xjk.shiro.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xjk
 * @date 2019/2/17 -  13:35
 **/
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200,"success"),
    /**
     * 失败
     */
    ERROR(404,"error");
    
    /**
     *状态码,200成功,404失败
     */
    private Integer code ;
    
    /**
     *返回信息 success error
     */
    private String message ;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
