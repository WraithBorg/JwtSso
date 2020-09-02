package com.jsso.wt.mno.controller;

import com.jsso.wt.common.util.CustomUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MnoController {

    @RequestMapping("/Mno/accessMno")
    @ResponseBody
    public Object accessMno(){
        return CustomUtil.ofMap("id", System.currentTimeMillis());
    }

}
