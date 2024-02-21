package net.ltbk.music.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class SingerVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer singerId;
    private String singerName;
    private String sex;
    private Date startDate;
    private Date endDate;
    private Integer pageNum;
    private Integer pageSize;
}
