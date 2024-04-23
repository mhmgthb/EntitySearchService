package sa.qiwa.cache.search.ms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EntitySearchServiceApplication {
	//@Autowired
	//private Environment environment;
	public static void main(String[] args) {
		for(String arg:args) {
			//log.info("Command Line Argument"+arg);
			System.out.println("Command Line Argument"+arg);
		}
		SpringApplication.run(EntitySearchServiceApplication.class, args);
	}
	public void run(String... args) throws Exception {
		/*System.out.println("using environment: " + myConfig.getEnvironment());
		System.out.println("name: " + myConfig.getName());
		System.out.println("enabled:" + myConfig.isEnabled());
		System.out.println("servers: " + myConfig.getServers());*/

	}
}
