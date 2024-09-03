package slvtwn.khu.toyouserver.common.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import slvtwn.khu.toyouserver.ToyouServerApplication;

@EnableFeignClients(basePackageClasses = ToyouServerApplication.class)
@Configuration
public class FeignConfig {
}
