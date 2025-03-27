package xyz.spc.infra.feign.Group;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "group-app")
public interface GroupsClient {


    List<String> getGroupNamesByIds(List<Long> groupIds);
}
