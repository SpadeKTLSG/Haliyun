package xyz.spc.serve.group.func.interacts;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.domain.dos.Group.interacts.PostDO;
import xyz.spc.infra.special.Group.interacts.PostsRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostFunc {


    /**
     * Repo
     */
    private final PostsRepo postsRepo;


    public PageResponse<PostDO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest) {

        Page<PostDO> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());

        //? 分页查询: 统一采用MP的分页查询, 使用 DO, 之后再去转换刷洗; 封装 PageResponse 对象
        IPage<PostDO> postPage = postsRepo.postsService.page(page, Wrappers.lambdaQuery(PostDO.class)
                .eq(PostDO::getUserId, id)
        );


        return PageResponse.<PostDO>builder()
                .current(postPage.getCurrent())
                .size(postPage.getSize())
                .total(postPage.getTotal())
                .records(postPage.getRecords())
                .build();

    }
}
