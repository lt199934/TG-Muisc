package net.ltbk.music.common;

/**
 * @Program: music
 * @ClassName Constants
 * @Author: liutao
 * @Description: 统一返回码封装
 * @Create: 2023-04-02 17:12
 * @Version 1.0
 **/
public interface Constants {
    // 成功
    Integer CODE_SUCCESS = 200;
    Integer CODE_ERROR = 500;
    // 系统运行错误
    Integer CODE_WARNING = 400;
    // 未授权
    Integer CODE_NOT_AUTHORIZED = 401;
    // 权限不足
    Integer CODE_NOT_FORBIDDEN = 403;
    // 页面找不到
    Integer CODE_NOT_FOUND = 404;
}
