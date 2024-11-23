package com.casillega.llegaApi.service.impl;

import com.casillega.llegaApi.entities.AppUser;
import com.casillega.llegaApi.entities.AssociationGroup;
import com.casillega.llegaApi.entities.AssociationGroupId;
import com.casillega.llegaApi.repositories.AppUserRepository;
import com.casillega.llegaApi.repositories.AssociationGroupRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociationGroupService {

    private final AssociationGroupRepository associationGroupRepository;
    private final AppUserRepository appUserRepository;

    public AssociationGroupService(AssociationGroupRepository associationGroupRepository, AppUserRepository appUserRepository) {
        this.associationGroupRepository = associationGroupRepository;
        this.appUserRepository = appUserRepository;
    }

    public String followUser(Long followedUserId) {
        try {
            // Get the current authenticated user
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser followingUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            // Prevent self-follow
            if (followingUser.getId() == (followedUserId)) {
                return "You cannot follow yourself.";
            }

            // Check if already following
            boolean alreadyFollowing = associationGroupRepository.existsById(new AssociationGroupId(followingUser.getId(), Math.toIntExact(followedUserId)));
            if (alreadyFollowing) {
                return "You are already following this user.";
            }

            // Get the user to be followed
            AppUser followedUser = appUserRepository.findById(followedUserId)
                    .orElseThrow(() -> new RuntimeException("User to follow not found"));

            // Create the follow relationship
            AssociationGroup associationGroup = new AssociationGroup();
            associationGroup.setFollowingUser(followingUser);
            associationGroup.setFollowedUser(followedUser);
            associationGroupRepository.save(associationGroup);

            return "Successfully followed the user.";
        } catch (Exception e) {
            throw new RuntimeException("Error while following user: " + e.getMessage());
        }
    }

    public String unfollowUser(Long followedUserId) {
        try {
            // Get the current authenticated user
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser followingUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            // Delete the follow relationship
            associationGroupRepository.deleteById(new AssociationGroupId(followingUser.getId(), Math.toIntExact(followedUserId)));
            return "Successfully unfollowed the user.";
        } catch (Exception e) {
            throw new RuntimeException("Error while unfollowing user: " + e.getMessage());
        }
    }

    public List<String> getFollowers() {
        try {
            // Get the current authenticated user
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            // Fetch followers of the current user
            return associationGroupRepository.findFollowersByUserId((long) currentUser.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving followers: " + e.getMessage());
        }
    }

    public List<String> getFollowing() {
        try {
            // Get the current authenticated user
            String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            AppUser currentUser = appUserRepository.findByUserEmail(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            // Fetch users followed by the current user
            return associationGroupRepository.findFollowedUsersByUserId((long) currentUser.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving following: " + e.getMessage());
        }
    }
}

