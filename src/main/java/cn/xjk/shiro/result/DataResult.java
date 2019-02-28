package cn.xjk.shiro.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xjk
 * @date 2019/2/16 -  22:22
 * 后台返回数据
 **/
@Setter
@Getter
public class DataResult<T> {
    /**
     *返回状态码
     */
    private Integer code ;

    /**
     *返回数据
     */
    private T data ;

    public DataResult(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
    }

    public static <T> DataResult<T> success(T data) {
        DataResult<T> result = new DataResult<>(ResultEnum.SUCCESS);
        result.setData(data);
        return result;
    }

}
