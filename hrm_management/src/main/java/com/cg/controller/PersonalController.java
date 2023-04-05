package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/personals")
public class PersonalController {


    @GetMapping
    public String showListPersonalPage(){
        return "personal/index";
    }

}
