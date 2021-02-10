package pl.pozadr.ocrsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class OcrSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcrSystemApplication.class, args);
    }

}
