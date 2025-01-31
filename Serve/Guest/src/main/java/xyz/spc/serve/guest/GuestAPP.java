package xyz.spc.serve.guest;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 客户服务启动类
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients(basePackages = "xyz.spc.infra.feign.Group") //开启Feign客户端
@EnableDiscoveryClient //开启服务发现
@MapperScan("xyz.spc.infra") //扫描Mapper接口
@EnableScheduling //开启定时任务
@EnableAspectJAutoProxy() //开启AspectJ
public class GuestAPP {
    public static void main(String[] args) {
        SpringApplication.run(GuestAPP.class, args);
        log.debug("客户服务启动成功");
    }
}
