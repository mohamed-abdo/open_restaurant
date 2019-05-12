import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner
{
    private static final Logger LOGGER= LoggerFactory.getLogger(Application.class);

    public static void main(String... args){
        LOGGER.info("application is started...");
        SpringApplication.run(Application.class,args);
        LOGGER.info("application is finished.");
    }
    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("executing command line with params: {}",String.join(", ",args));
    }
}
