package xyz.spc.gate.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy()
@EnableDiscoveryClient //开启服务发现
@ComponentScan(basePackages = "xyz.spc.gate.gateway.*")
@SpringBootApplication
@Slf4j
public class GatewayAPP {
    public static void main(String[] args) {
        SpringApplication.run(GatewayAPP.class, args);
        log.debug("网关服务启动成功");
    }
}
