package net.ltbk.music.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (songcollect)实体类
 *
 * @author liutao
 * @since 2023-03-31 21:55:23
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("songcollect")
public class Songcollect extends Model<Songcollect> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * collectId
     */
    @TableId
	private Integer collectId;
    /**
     * songListId
     */
    private Integer songListId;
    /**
     * songId
     */
    private Integer songId;

}