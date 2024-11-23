package com.casillega.llegaApi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppUser implements UserDetails {
    private String role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }


    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "full_name")
    @NotBlank(message = "Please provide your name")
    private String fullName;

    @Column(nullable = false, name = "user_email")
    @NotBlank(message = "Please provide your email")
    private String userEmail;

    @Column(nullable = false, name = "user_password")
    @NotBlank(message = "Please provide your password")
    private String userPassword;

    @Column(nullable = false, name = "username")
    @NotBlank(message = "Please provide username")
    private String userName;

    // OneToMany relationship to fetch events created by this user
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    // Constructor that initializes fields
    public AppUser(String fullName, String userEmail, String userPassword, String userName) {
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
    }
    public AppUser(String fullName, String userEmail, String userPassword, String userName,String role) {
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.role = role;
    }

    public @NotBlank(message = "Please provide username") String getUserName() {
        return userName;
    }

    public AppUser(int createdById) {
        this.id = createdById;
    }

    public void setUserName(@NotBlank(message = "Please provide username") String userName) {
        this.userName = userName;
    }


}


//LIST , EXAMPLE: LIST OF ALL THE ACTORS IN A MOVIE