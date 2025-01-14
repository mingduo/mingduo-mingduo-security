package mingduosecurity.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class CloudSecurityFontApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudSecurityFontApplication.class, args);
    }

}
