package net.ltbk.music.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Program: music
 * @ClassName SingerVo
 * @Author: liutao
 * @Description:
 * @Create: 2023-04-15 02:01
 * @Version 1.0
 **/

@Data
public class SingerVo implements Serializable {
    private String singerName;
    private String sex;
    private Date startDate;
    private Date endDate;
    private Integer pageNum;
    private Integer pageSize;
}
