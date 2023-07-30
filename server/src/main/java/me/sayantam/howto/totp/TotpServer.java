package me.sayantam.howto.totp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TotpServer {

    public static void main(String[] args) {
        SpringApplication.run(TotpServer.class, args);
    }

}
