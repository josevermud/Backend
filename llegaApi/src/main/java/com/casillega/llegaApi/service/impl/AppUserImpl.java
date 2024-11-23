package com.casillega.llegaApi.service.impl;

import com.casillega.llegaApi.dto.AppUserdto;
import com.casillega.llegaApi.dto.EventDTO;
import com.casillega.llegaApi.dto.LoginRequest;
import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.entities.Event;
import com.casillega.llegaApi.exception.OurException;
import com.casillega.llegaApi.repositories.AppUserRepository;
import com.casillega.llegaApi.repositories.EventRepository;
import com.casillega.llegaApi.repositories.NotificationsRepository;
import com.casillega.llegaApi.service.interfac.AppUserService;

import com.casillega.llegaApi.service.interfac.NotificationsService;
import com.casillega.llegaApi.utils.JWTUtils;
import com.casillega.llegaApi.utils.Utils;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserImpl implements AppUserService {
    @Autowired
    private  AppUserRepository appUserRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    private NotificationsRepository notificationsRepository;
    @Autowired
    private EventRepository eventRepository;

    public AppUserImpl(AppUserRepository appUserRepository,NotificationsRepository notificationsRepository) {this.appUserRepository = appUserRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @Override
    public Response register(AppUser user) {
        Response response = new Response();
        try {
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER");
            }
            if (appUserRepository.existsByUserEmail(user.getUserEmail())) {
                throw new OurException(user.getUserEmail() + " Already Exists");
            }
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            AppUser savedUser = appUserRepository.save(user);
            AppUserdto userdto = Utils.mapUserDtoEntity(savedUser);
            response.setStatusCode(200);
            response.setUser(userdto);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration: " + e.getMessage());
        }
        return response;  // Return the response instead of null
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            // Isolate the repository call
            Optional<AppUser> userOptional = appUserRepository.findByUserEmail(loginRequest.getUsername());
            AppUser user = userOptional.orElseThrow(() -> new OurException("User not found"));

            // Proceed with token generation
            String token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Successfully logged in");

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred during user login: " + e.getMessage());
        }
        return response;
    }
    @Override
    public Response getAllUser() {
        Response response = new Response();
        try {
            // Isolate the repository call
            List<AppUser> userList = appUserRepository.findAll();

            // Map users to DTOs
            List<AppUserdto> appUserdtoList = Utils.mapUserListEntitytouserListDto(userList);

            // Prepare response
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserList(appUserdtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserEventHistoryy(String userId) {
        Response response=new Response();
        try{
            Event event = (Event) eventRepository.findByCreatedByUser(Integer.parseInt(userId));
            EventDTO eventDTO = Utils.mapeventEntitytoEntityDto(event);
            if (
                    event.getMedia() != null && !event.getMedia().isEmpty()
            ) {
                eventDTO.setUrlimage(event.getMedia().get(0).getMediaLocation());
            }
            response.setStatusCode(200);
            response.setMessage("Success");
            response.setEventDTO(eventDTO);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage()+"Error getting event by id ");
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response=new Response();
        try{
            appUserRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("User not found"));
            appUserRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("Successful");
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage()+"Error deleting user");
        }
        return response;
    }

    @Override
    public Response getUserById(String  userId) {
        Response response=new Response();
        try{
            AppUser user = appUserRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new OurException("User not found"));
            AppUserdto userdto = Utils.mapUserDtoEntity(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userdto);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage()+"Error getting user by id");
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response=new Response();
        try{
            AppUser user = appUserRepository.findByUserEmail(email).orElseThrow(()-> new OurException("User not found"));
            AppUserdto userdto = Utils.mapUserDtoEntity(user);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userdto);
        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage()+"Error getting user by id");
        }
        return response;
    }
    @Transactional
    @Override
    public List<String> getAllMyNotifications(long userId) {
        return notificationsRepository.notificationsByUserId(userId);
    }

    //@Transactional
    @Override
    @Transactional
    public Response updateProfileInfo(AppUser appUserdto) {
        Response response = new Response();
        try {
            // Fetch the current user
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            // Update fields if provided in DTO
            if (appUserdto.getFullName() != null && !appUserdto.getFullName().isEmpty()) {
                currentUser.setFullName(appUserdto.getFullName());
            }
            if (appUserdto.getUserName() != null && !appUserdto.getUserName().isEmpty()) {
                currentUser.setUserName(appUserdto.getUserName());
            }
            if (appUserdto.getUserEmail() != null && !appUserdto.getUserEmail().isEmpty()) {
                currentUser.setUserEmail(appUserdto.getUserEmail());
            }

            // Save and validate the updated entity
            AppUser savedUser = appUserRepository.saveAndFlush(currentUser);

            // Map to DTO and set in response
            AppUserdto userdto = Utils.mapUserDtoEntity(savedUser);
            response.setStatusCode(200);
            response.setMessage("Profile updated successfully.");
            response.setUser(userdto);
        } catch (ConstraintViolationException e) {
            response.setStatusCode(400);
            response.setMessage("Validation failed: " + e.getConstraintViolations().toString());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating profile: " + e.getMessage());
        }
        return response;
    }




}
