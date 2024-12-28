package com.example.oauth_exercise.post.dto;

import com.example.oauth_exercise.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String userName;

    public static PostResponseDTO from(Post post){
        return PostResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getAuthor().getId())
                .userName(post.getAuthor().getName())
                .build();
    }
}
