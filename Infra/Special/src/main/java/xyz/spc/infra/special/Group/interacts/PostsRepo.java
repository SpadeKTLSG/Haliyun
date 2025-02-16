package xyz.spc.infra.special.Group.interacts;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.mapper.Group.interacts.PostMapper;
import xyz.spc.infra.repo.Group.interacts.PostService;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
public class PostsRepo {

    public final PostService postsService;
    public final PostMapper postsMapper;
}
