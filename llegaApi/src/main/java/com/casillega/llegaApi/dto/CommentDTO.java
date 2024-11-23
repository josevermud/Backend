package com.casillega.llegaApi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private int id;
    private String commentText;
    private int userId;
    private int eventId;
    private String userName;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Getters and Setters
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
