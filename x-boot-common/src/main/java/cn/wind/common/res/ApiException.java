package cn.wind.common.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiException extends RuntimeException{
    private Integer code;
    public ApiException(Integer code, String message){
        super(message);
        this.code=code;
    }
    public ApiException(String message){
        super(message);
    }
}