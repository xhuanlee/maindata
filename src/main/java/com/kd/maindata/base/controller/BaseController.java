package com.kd.maindata.base.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xhuanlee on 2017/3/18.
 */
public class BaseController {

    HttpServletRequest request;

    HttpServletResponse response;

    private ModelAndView modelAndView;

    private Map<String, Object> dataMap;

    public static final String SUCCESS = "success";

    public static final String FAILURE = "failure";

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        // 注册自定义属性编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request,
                             HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return getRequest() == null ? null : getRequest().getSession();
    }


    public String saveFile(MultipartFile file, String path, String fileName) throws Exception {
        // 文件名前加上时间戳
        fileName = new Date().getTime() + "_" + fileName;
        String realPath = path + fileName;
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, fileName));

        return realPath;
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public void setModelAndView(ModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }

    private Map<String, Object> getDataMap() {
        if (this.dataMap == null) {
            this.dataMap = new HashMap<String, Object>();
        }

        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public void setViewName(String viewName) {
        if (this.getModelAndView() == null) {
            this.setModelAndView(new ModelAndView());
        }

        this.getModelAndView().setViewName(viewName);
    }

    public void setModel(Map<String, ?> modelMap) {
        if (this.getModelAndView() == null) {
            this.setModelAndView(new ModelAndView());
        }

        this.getModelAndView().addAllObjects(modelMap);
    }

    public void setResult(Map<String, Object> map, String mssage, String result) {
        if (this.getDataMap() == null) {
            this.setDataMap(new HashMap<String, Object>());
        }

        this.getDataMap().put("DATA", map);
        this.getDataMap().put("MSG", mssage);
        this.getDataMap().put("RESULT", result);
    }

    public Map<String, Object> getResult() {
        return this.getDataMap();
    }

}
