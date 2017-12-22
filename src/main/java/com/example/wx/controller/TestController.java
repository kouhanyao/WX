package com.example.wx.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by 寇含尧 on 2017/11/11.
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    @RequestMapping(value = "")
    public String test(@RequestBody String xml){
        return "success test:"+ xml;
    }

    @RequestMapping(value = "test1")
    public String test1(String xml){
        return "success test:"+ xml;
    }

    @RequestMapping(value = "test2")
    public String test2(String xml){
        return "success test2:"+ xml;
    }

}
