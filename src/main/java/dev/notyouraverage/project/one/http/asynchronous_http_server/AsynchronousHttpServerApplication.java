package dev.notyouraverage.project.one.http.asynchronous_http_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "dev.notyouraverage.project.base", "dev.notyouraverage.project.one" })
public class AsynchronousHttpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsynchronousHttpServerApplication.class, args);
    }

}
