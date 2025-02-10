package xyz.spc.serve.money;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 金钱服务启动类
 */
@Slf4j
@MapperScan({"xyz.spc.infra.mapper"})
@SpringBootApplication(scanBasePackages = {
        "xyz.spc.serve.money",
        "xyz.spc.serve.auxiliary",
        "xyz.spc.infra.*",
        "xyz.spc.common.*"})
@EnableFeignClients(basePackages = "xyz.spc.infra.feign")
@EnableDiscoveryClient //开启服务发现
@EnableScheduling //开启定时任务
@EnableAspectJAutoProxy() //开启AspectJ
@EnableRetry //开启重试
public class MoneyAPP {
    public static void main(String[] args) {
        SpringApplication.run(MoneyAPP.class, args);
    }
}
