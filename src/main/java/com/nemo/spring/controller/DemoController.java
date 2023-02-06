package com.nemo.spring.controller;

import com.nemo.spring.annotation.Autowired;
import com.nemo.spring.annotation.Controller;
import com.nemo.spring.annotation.RequestMapping;
import com.nemo.spring.annotation.RequestParam;
import com.nemo.spring.v1.service.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mingLong.zhao
 */
@Controller
public class DemoController {

    @Autowired
    private IDemoService demoService;

    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam("someThing") String someThing) {
        String res = demoService.test(someThing);
        try {
            response.getWriter().write(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
