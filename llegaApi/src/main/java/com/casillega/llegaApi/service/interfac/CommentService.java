package com.casillega.llegaApi.service.interfac;

import com.casillega.llegaApi.dto.CommentDTO;
import com.casillega.llegaApi.dto.Response;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {
    @Transactional
    Response getAllCommentsByEventId(long eventId);

    Response addNewCommnet (Long eventId, String commentText, LocalDateTime createdDate );
    Response deleteComment ( Long commentId);

}
