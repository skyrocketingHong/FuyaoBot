package ninja.skyrocketing.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 15:28:47
 * @Version 1.0
 */

@Configuration
public class DruidConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DruidDataSource druid() {
		return new DruidDataSource();
	}
}
