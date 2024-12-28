package com.example.oauth_exercise.post.service;

import com.example.oauth_exercise.post.domain.Post;
import com.example.oauth_exercise.post.dto.PostRequestDTO;
import com.example.oauth_exercise.post.dto.PostResponseDTO;
import com.example.oauth_exercise.post.repository.PostRepository;
import com.example.oauth_exercise.user.domain.User;
import com.example.oauth_exercise.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO requestDTO,String email){
        User author=userService.findByEmail(email);

        Post post=Post.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .author(author)
                .build();

        postRepository.save(post);
        return PostResponseDTO.from(post);
    }

    public List<PostResponseDTO> findAll(){
        List<Post> posts=postRepository.findAll()
                .stream()
                .collect(Collectors.toList());

        return posts.stream()
                .map(PostResponseDTO::from)
                .collect(Collectors.toList());
    }
}
