package com.casillega.llegaApi.controllers;

import com.casillega.llegaApi.dto.LoginRequest;
import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.service.interfac.AppUserService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private AppUserService appUserService;
    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @PostMapping("/register")
    public ResponseEntity<Response> register(@Valid @RequestBody AppUser user) {
        Response response = appUserService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Valid @RequestBody LoginRequest loginRequest) {
        Response response = appUserService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
