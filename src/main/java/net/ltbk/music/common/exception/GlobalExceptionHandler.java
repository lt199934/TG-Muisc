package net.ltbk.music.common.exception;


import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.common.Result;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Program: admin
 * @ClassName ExceptionHandler
 * @Author: liutao
 * @Description: 自定义异常
 * @Create: 2023-03-14 03:52
 * @Version 1.0
 **/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @MethodName: handle
     * @description: 业务异常
     * @Author: LiuTao
     * @Param: [se]
     * @UpdateTime: 2023/3/14 4:00
     * @Return: com.tg.admin.common.Result
     * @Throw:
     **/
    @ExceptionHandler(value = ServiceException.class)
    public Result<T> handle(ServiceException se) {
        log.error("业务异常" + se);
        return Result.error(se.getCode(), se.getMessage());
    }


}
