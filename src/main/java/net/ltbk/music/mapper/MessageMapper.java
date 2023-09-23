package net.ltbk.music.mapper;

import net.ltbk.music.bean.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    Message selectByPrimaryKey(Integer messageId);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
}