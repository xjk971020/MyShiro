package cn.xjk.shiro.result;

import lombok.Getter;
import lombok.Setter;
import sun.plugin2.message.Message;

/**
 * @author xjk
 * @date 2019/2/17 -  15:21
 * 后端返回信息
 **/
@Setter
@Getter
public class MessageResult {
    /**
     * 状态码
     */
    private int code;

    /**
     * 信息
     */
    private String message;

    public MessageResult(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    /**
     * 请求处理成功
     * @return
     */
    public static MessageResult success() {
        MessageResult messageResult = new MessageResult(ResultEnum.SUCCESS);
        return messageResult;
    }

    /**
     * 请求处理失败
     * @return
     */
    public static MessageResult error() {
        MessageResult messageResult = new MessageResult(ResultEnum.ERROR);
        return messageResult;
    }
}
