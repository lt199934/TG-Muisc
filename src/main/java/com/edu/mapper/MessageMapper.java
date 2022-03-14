package com.edu.mapper;

import com.edu.bean.Message;
import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    Message selectByPrimaryKey(Integer messageId);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
}