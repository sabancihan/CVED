package com.sabancihan.registerservice.controller;

import com.sabancihan.registerservice.dto.RegisterRequest;
import com.sabancihan.registerservice.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;


    @PostMapping
    public void register(@Valid RegisterRequest registerRequest) {

        registerService.register(registerRequest);


    }


}
