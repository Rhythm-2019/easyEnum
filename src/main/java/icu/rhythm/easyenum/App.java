package icu.rhythm.easyenum;

import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import icu.rhythm.easyenum.annotation.EnableEasyEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Rhythm-2019
 * @date 2023/2/19
 * @description
 */
@SpringBootApplication
@EnableEasyEnum(basePackages = "icu.rhythm.easyenum.example")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
