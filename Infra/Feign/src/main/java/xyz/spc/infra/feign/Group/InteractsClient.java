package xyz.spc.infra.feign.Group;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Group.interacts.PostShowVO;

@FeignClient(name = "group-app")
public interface InteractsClient {

    String BASE_URL = "/Group/interacts";

    /**
     * 用户获取收藏分页数据 - Post动态
     */
    @PostMapping(BASE_URL + "/collect/data/post")
    PageResponse<PostShowVO> getUserDataOfPost(
            @RequestParam("id") @NotNull Long id,
            @RequestBody PageRequest pageRequest
    );
}
