package com.casillega.llegaApi.controllers;

import com.casillega.llegaApi.service.impl.AssociationGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/association")
public class AssociationGroupController {

    private final AssociationGroupService associationGroupService;

    public AssociationGroupController(AssociationGroupService associationGroupService) {
        this.associationGroupService = associationGroupService;
    }

    @PostMapping("/follow/{followedUserId}")
    public ResponseEntity<String> followUser(@PathVariable Long followedUserId) {
        String response = associationGroupService.followUser(followedUserId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/unfollow/{followedUserId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followedUserId) {
        String response = associationGroupService.unfollowUser(followedUserId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followers")
    public ResponseEntity<List<String>> getFollowers() {
        List<String> followers = associationGroupService.getFollowers();
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following")
    public ResponseEntity<List<String>> getFollowing() {
        List<String> following = associationGroupService.getFollowing();
        return ResponseEntity.ok(following);
    }
}
