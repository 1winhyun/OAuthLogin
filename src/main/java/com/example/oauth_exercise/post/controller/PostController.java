package com.example.oauth_exercise.post.controller;

import com.example.oauth_exercise.post.dto.PostRequestDTO;
import com.example.oauth_exercise.post.dto.PostResponseDTO;
import com.example.oauth_exercise.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO>createPost(@RequestBody PostRequestDTO requestDTO, @AuthenticationPrincipal String email){
        PostResponseDTO post=postService.createPost(requestDTO,email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(post);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>>getAllPosts(){
        List<PostResponseDTO>posts=postService.findAll();
        return ResponseEntity.ok()
                .body(posts);
    }
}
