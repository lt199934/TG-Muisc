package net.ltbk.music.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Program: TG-Music
 * @ClassName Admin
 * @Author: liutao
 * @Description: 管理员
 * @Create: 2023-11-14 21:52
 * @Version 1.0
 **/
@Data
@TableName("admin")
public class Admin {
    private Integer superId;
    private String userName;
    private String nickName;
    private String pwd;
}
