package com.casillega.llegaApi.service.interfac;

import com.casillega.llegaApi.dto.AppUserdto;
import com.casillega.llegaApi.dto.LoginRequest;
import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AppUserService {
    Response register(AppUser user);
    Response login(LoginRequest loginRequest);
    Response getAllUser();

    Response getUserEventHistoryy(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);
    List<String> getAllMyNotifications(long userId);
    Response updateProfileInfo(AppUser appUserdto);
}
