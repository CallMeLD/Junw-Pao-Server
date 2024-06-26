package my.junw.pao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("my.junw.pao.dao")
public class JunwPaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunwPaoApplication.class, args);
    }

}
