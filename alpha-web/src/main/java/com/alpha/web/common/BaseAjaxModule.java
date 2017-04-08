package com.alpha.web.common;

import com.alibaba.citrus.service.pull.PullService;
import com.alibaba.citrus.util.StringEscapeUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by taoxiang on 2017/4/6.
 */
public class BaseAjaxModule extends BaseModule {

    protected void print(Object object) {
        String callback = StringEscapeUtil.escapeHtml(request.getParameter("callback"));
        String backvar = StringEscapeUtil.escapeHtml(request.getParameter("backvar"));
        String result = JSON.toJSONString(object);
        if (callback != null) {
            result = callback + "(" + result + ");";
        }
        if (backvar != null) {
            result = backvar + "=" + result;
        }
        print(result);
    }

}
