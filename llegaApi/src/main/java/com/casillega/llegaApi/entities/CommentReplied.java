package com.casillega.llegaApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_replied")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentReplied {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "ID", nullable = false)
    private Comment comment;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String commentText;

    // Getters and Setters
}
