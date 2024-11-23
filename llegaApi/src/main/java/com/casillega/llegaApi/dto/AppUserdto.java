package com.casillega.llegaApi.dto;

import com.casillega.llegaApi.entities.Comment;
import com.casillega.llegaApi.entities.Event;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserdto {
    private int id;
    @Column(nullable = false)

    private String full_name;

    @Column(nullable = false)

    private String user_email;

    @Column(nullable = false)

    private String username;
    private String role;

    private List<EventDTO> events;

    private List<Comment> comment;

    public AppUserdto(String fullName, String userEmail, String userName) {
        this.full_name = fullName;
        this.user_email = userEmail;
        this.username = userName;
    }
}
