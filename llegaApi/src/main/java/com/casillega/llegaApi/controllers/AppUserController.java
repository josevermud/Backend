package com.casillega.llegaApi.controllers;


import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.service.impl.PdfService;
import com.casillega.llegaApi.service.interfac.AppUserService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private AppUserService appUserService;
    public AppUserController(AppUserService appUserService, PdfService pdfService) {
        this.appUserService = appUserService;
        this.pdfService = pdfService;
    }
    private final PdfService pdfService;


    @GetMapping("/profile-pdf/{userId}")
    public ResponseEntity<byte[]> getUserProfilePdf(@PathVariable Long userId) {
        byte[] pdfBytes = pdfService.generateUserProfilePdf(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("UserProfile.pdf").build());
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = appUserService.getAllUser();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId) {
        Response response = appUserService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Response> deleteUserById(@PathVariable("userId") String userId) {
        Response response = appUserService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-logged-in-profile-info")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Response> getLoggedProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = appUserService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @GetMapping("/notifications/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<String> getAllNotifications(@PathVariable("userId") long userId) {
        return appUserService.getAllMyNotifications(userId);
    }

    @PutMapping("/profile/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    ResponseEntity<Response> updateProfile(@RequestBody AppUser appUser) {
        Response response= appUserService.updateProfileInfo(appUser);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}

