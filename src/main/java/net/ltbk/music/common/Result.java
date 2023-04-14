package net.ltbk.music.common;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Program: music
 * @ClassName Result
 * @Author: liutao
 * @Description: 通用结果返回类
 * @Create: 2023-04-02 14:24
 * @Version 1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("统一返回结果")
public class Result<T> {
    @ApiModelProperty("状态码")
    private String code;
    @ApiModelProperty("提示信息")
    private String msg;
    @ApiModelProperty("返回结果")
    private Object data;

    public static<T> Result<T> success(){
        return new Result<T>(Constants.CODE_SUCCESS,"",null);
    }

    public static<T> Result<T> success(Object data){
        return new Result<T>(Constants.CODE_SUCCESS,"",data);
    }

    public static<T> Result<T> error(){
        return new Result<T>(Constants.CODE_ERROR,"",null);
    }

    public static<T> Result<T> error(String msg){
        return new Result<T>(Constants.CODE_ERROR,msg,null);
    }

}
