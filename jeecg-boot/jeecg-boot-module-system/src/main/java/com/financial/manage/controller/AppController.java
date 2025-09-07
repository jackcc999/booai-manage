package com.financial.manage.controller;

import cn.com.dgwl.tools.v2.domain.SysStatus;
import cn.com.dgwl.tools.v2.net.Ipv4;
import cn.com.dgwl.tools.v2.servlet.HttpServlet;
import cn.com.dgwl.tools.v2.util.BeanUtil;
import cn.com.dgwl.tools.v2.util.DateUtil;
import cn.com.dgwl.tools.v2.util.SystemUtil;
import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/app")
public class AppController
{
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object info(HttpServletRequest request)
    {
        Map<String, String> header = HttpServlet.getRequestHeader(request);
        SysStatus sysStatus = SystemUtil.getSysStatusInfo();

        Map<String, Object> data = new HashMap<>();
        data.put("header", header);
        data.put("sysStatus", sysStatus);
        data.put("remoteIp", Ipv4.getRemoteIp());
        data.put("dateTime", DateUtil.format());

        return data;
    }
}
