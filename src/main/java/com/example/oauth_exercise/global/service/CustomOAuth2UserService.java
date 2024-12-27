package com.example.oauth_exercise.global.service;

import com.example.oauth_exercise.user.domain.User;
import com.example.oauth_exercise.user.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> oauth=new DefaultOAuth2UserService();
        OAuth2User oAuth2User=oauth.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName=userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        Map<String,Object>attributes=oAuth2User.getAttributes();
        String email=(String)attributes.get("email");
        String name=(String)attributes.get("name");

        User user=userRepository.findByEmail(email)
                .orElseGet(()->{
                    User newUser=User.builder()
                            .email(email)
                            .name(name)
                            .build();
                    return userRepository.save(newUser);
                });
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttributeName);
    }
}
