package com.kd.maindata.sys.login.controller;

import com.kd.maindata.base.controller.BaseController;
import com.kd.maindata.constants.WebSocketConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xhuanlee on 2017/3/19.
 */
@Controller
@RequestMapping(value = "login")
public class LoginController extends BaseController {

    @RequestMapping(value = "in", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object in(String userName) {
        this.getSession().setAttribute(WebSocketConstant.WEBSOCKET_USER, userName);
        this.setResult(null, "login success", SUCCESS);

        return this.getResult();
    }
}
