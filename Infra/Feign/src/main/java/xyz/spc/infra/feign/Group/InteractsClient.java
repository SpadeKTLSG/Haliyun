package xyz.spc.infra.feign.Cluster;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.spc.common.funcpack.page.PageResponse;
import xyz.spc.gate.vo.Cluster.interacts.PostShowVO;

@FeignClient(name = "group-app")
public interface InteractsClient {

    String BASE_URL = "/Cluster/interacts";

    /**
     * 用户获取收藏分页数据 - Post动态
     */
    @GetMapping(BASE_URL + "/collect/data/post")
    PageResponse<PostShowVO> getUserDataOfPost(
            @RequestParam("id") @NotNull Long id,
            @RequestParam("current") Long current,
            @RequestParam("size") Long size);
}
