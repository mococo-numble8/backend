package com.mococo.api.post.service;

import com.mococo.api.post.controller.request.PostCreateRequest;
import com.mococo.api.post.controller.response.PostCreateResponse;
import com.mococo.api.post.controller.response.PostGetResponse;
import com.mococo.core.account.domain.entity.Account;
import com.mococo.core.account.domain.service.AccountQueryService;
import com.mococo.core.post.domain.entity.Post;
import com.mococo.core.post.domain.service.PostCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostCommandService postCommandService;
    private final AccountQueryService accountQueryService;


    public PostGetResponse get(Long postId) {
        var post = postCommandService.get(postId).get();
        return PostGetResponse.from(post);
    }

    public List<PostGetResponse> gets(Pageable pageable) {
        var posts = postCommandService.gets(pageable);

        return posts.stream()
                .map(post -> PostGetResponse.from(post))
                .collect(Collectors.toList());
    }

    public PostCreateResponse create(PostCreateRequest request) {
        Account account = accountQueryService.requireGet(request.getEmail());
        Post post = postCommandService.create((request.toCommand(account)));
        return PostCreateResponse.from(post);
    }
}
