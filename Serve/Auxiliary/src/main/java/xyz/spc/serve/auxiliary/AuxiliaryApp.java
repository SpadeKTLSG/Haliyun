package xyz.spc.serve.auxiliary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("xyz.spc.infra")
@SpringBootApplication(scanBasePackages = "xyz.spc")
public class AuxiliaryApp {
}
