package xyz.spc.infra.feign.Group;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.spc.common.funcpack.page.PageRequest;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Group.interacts.PostShowVO;

@FeignClient(name = "group-app")
public interface InteractsClient {

    String BASE_URL = "/Group/interacts";

    @GetMapping(BASE_URL + "/collect/data/post")
    PageResponse<PostShowVO> getUserDataOfPost(@NotNull Long id, PageRequest pageRequest);
}
