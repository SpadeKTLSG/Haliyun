package xyz.spc.serve.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "xyz.spc.infra") //开启Feign客户端
@EnableDiscoveryClient //开启服务发现
@SpringBootApplication
public class DataAPP {
    public static void main(String[] args) {
        SpringApplication.run(DataAPP.class, args);
    }
}
