package cn.stronger.we;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class RunApplication.class
 * @department Platform R&D
 * @date 2025/2/24
 * @desc do what?
 */
@MapperScan("cn.stronger.we.mapper")
@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
    }
}
