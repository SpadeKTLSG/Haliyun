package xyz.spc.domain.repo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("xyz.spc.infra.mapper")
@SpringBootApplication(scanBasePackages = "xyz.spc")
public class RepoCompo {
}
