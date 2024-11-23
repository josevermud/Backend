package com.casillega.llegaApi.service;

import com.casillega.llegaApi.repositories.AppUserRepository;
import com.casillega.llegaApi.service.interfac.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private AppUserRepository appUserRepository;
    public CustomUserDetailService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUserEmail(username).orElseThrow(() -> new UsernameNotFoundException("username/email not found"));
    }
}
