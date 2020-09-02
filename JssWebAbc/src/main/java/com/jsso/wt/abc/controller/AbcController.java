package com.jsso.wt.abc.controller;

import com.jsso.wt.common.util.CustomUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AbcController {
    @RequestMapping("/Abc/showAbc")
    @ResponseBody
    public Object showAbc() {
        return CustomUtil.ofMap("msg", "访问受保护资源ABC");
    }

    @RequestMapping("/Abc/showAbcNoPass")
    @ResponseBody
    public Object showAbcNoPass() {
        return CustomUtil.ofMap("msg", "访问公开资源ABC");
    }
}
