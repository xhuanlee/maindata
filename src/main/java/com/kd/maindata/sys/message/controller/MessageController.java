package com.kd.maindata.sys.message.controller;

import com.kd.maindata.base.controller.BaseController;
import com.kd.maindata.constants.WebSocketConstant;
import com.kd.maindata.sys.message.model.Message;
import com.kd.maindata.sys.message.service.MessageService;
import com.kd.maindata.websocket.config.SystemWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xhuanlee on 2017/3/18.
 */
@Controller
@RequestMapping(value = "message")
public class MessageController extends BaseController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "get/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object queryById(@PathVariable(value = "id") String id) {
        Map<String, Object> data = new HashMap<String, Object>();
        Message message = messageService.queryById(id);
        data.put("entry", message);
        this.setResult(data, "恭喜你，数据获取成功", SUCCESS);

        return this.getResult();
    }

    @RequestMapping(value = "chat", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView chat() {
        this.setViewName("chat/chatroom");
        String userName = (String)this.getSession().getAttribute(WebSocketConstant.WEBSOCKET_USER);
        this.getModelAndView().addObject("", userName);

        return this.getModelAndView();
    }

    @RequestMapping(value = "send", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object send(String message) {
        systemWebSocketHandler().sendMessageToAllUser(new TextMessage(message));
        this.setResult(null, "sennd success", SUCCESS);

        return this.getResult();
    }

    @Bean
    private SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
}
