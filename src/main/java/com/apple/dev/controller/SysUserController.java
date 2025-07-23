package com.apple.dev.controller;

import com.apple.dev.service.ChangeBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SysUserController {

    private final ChangeBalance changeBalance;

    @RequestMapping(value = "query",method = RequestMethod.GET)
    public void query(){

    }
}
