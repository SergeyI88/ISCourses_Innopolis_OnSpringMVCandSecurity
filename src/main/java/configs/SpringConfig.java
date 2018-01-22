package configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"controllers", "db.connection", "db.dao", "controllers.filters"})
public class SpringConfig  {

}
