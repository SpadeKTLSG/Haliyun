package xyz.spc.serve.group.func.interacts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.special.Group.interacts.PostsRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostFunc {


    /**
     * Repo
     */
    private final PostsRepo postsRepo;
}
