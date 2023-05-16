package net.ltbk.music.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Program: music
 * @ClassName UserVo
 * @Author: liutao
 * @Description:
 * @Create: 2023-04-15 01:46
 * @Version 1.0
 **/

@Data
public class UserVo implements Serializable {
    private Integer userId;
    private String account;
    private String phone;
    private String email;
    private String sex;
    private Date startDate;
    private Date endDate;
    private Integer pageNum;
    private Integer pageSize;
}
