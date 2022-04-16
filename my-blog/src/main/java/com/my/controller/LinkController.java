package com.my.controller;

import com.my.domain.ResponseResult;
import com.my.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jhp
 * @create 2022-04-12 15:44
 */
@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
