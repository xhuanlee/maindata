package com.kd.maindata.sys.message.service.impl;

import com.kd.maindata.sys.message.dao.MessageMapper;
import com.kd.maindata.sys.message.model.Message;
import com.kd.maindata.sys.message.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xhuanlee on 2017/3/18.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper mapper;

    Logger logger = LoggerFactory.getLogger(getClass());

    public Message queryById(String id) {
        Message message = null;
        try {
            message = mapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.toString());
            throw new RuntimeException(e);
        }

        return message;
    }
}
