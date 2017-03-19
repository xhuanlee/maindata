package com.kd.maindata.quartz.test;

import com.kd.maindata.websocket.config.SystemWebSocketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;

/**
 * Created by xhuanlee on 2017/3/19.
 */
public class TestJob {

    private static Logger logger = LogManager.getLogger(TestJob.class);

    private static int count = 0;

    public void runJob() {
        systemWebSocketHandler().sendMessageToAllUser(new TextMessage("quartz job: " + count));
        logger.info("quartz job: " + count);
        count++;
    }

    public SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
}
