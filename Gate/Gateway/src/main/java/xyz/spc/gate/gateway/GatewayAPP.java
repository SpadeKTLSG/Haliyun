package xyz.spc.gate.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient //开启服务发现
@SpringBootApplication
@Slf4j
public class GatewayAPP {
    public static void main(String[] args) {
        SpringApplication.run(GatewayAPP.class, args);
        log.debug("网关服务启动成功");
    }
}
