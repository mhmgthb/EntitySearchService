package sa.qiwa.cache.aggregate.ms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EntityAggregateServiceApplication {
	//@Autowired
	//private Environment environment;
	public static void main(String[] args) {
		//for(String arg:args) {
			//log.info("Command Line Argument"+arg);
			//System.out.println("Command Line Argument"+arg);
		//}
		SpringApplication.run(EntityAggregateServiceApplication.class, args);
	}
	public void run(String... args) throws Exception {
		/*System.out.println("using environment: " + myConfig.getEnvironment());
		System.out.println("name: " + myConfig.getName());
		System.out.println("enabled:" + myConfig.isEnabled());
		System.out.println("servers: " + myConfig.getServers());*/

	}
}
