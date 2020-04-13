package com.twelvet.framework.core.controller;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

/**
 * @author twelvet
 * @WebSite www.twelvet.cn
 * @Description: Controller通用数据处理基类
 */
public class Controller {
    protected final Logger logger = LoggerFactory.getLogger(Controller.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                try {
                    setValue(DateUtils.parseDate(text));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
