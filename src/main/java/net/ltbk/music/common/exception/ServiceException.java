package net.ltbk.music.common.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Program: admin
 * @ClassName ServiceException
 * @Author: liutao
 * @Description:
 * @Create: 2023-03-14 03:52
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Getter
public class ServiceException extends RuntimeException{
    private final Integer code;
    public ServiceException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
