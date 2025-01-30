package xyz.spc.serve.guest;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "xyz.spc.infra.feign.Group") //开启Feign客户端
@EnableDiscoveryClient //开启服务发现
@SpringBootApplication
@MapperScan("xyz.spc.infra") //扫描Mapper接口
@Slf4j
public class GuestAPP {
    public static void main(String[] args) {
        SpringApplication.run(GuestAPP.class, args);
        log.debug("客户服务启动成功");
    }
}
