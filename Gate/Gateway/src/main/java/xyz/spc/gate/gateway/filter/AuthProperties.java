package xyz.spc.gate.gateway.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 网关权限配置
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private List<String> excludePaths;

}
