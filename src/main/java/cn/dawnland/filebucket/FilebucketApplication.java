package cn.dawnland.filebucket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.dawnland.filebucket.common.mapper")
public class FilebucketApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilebucketApplication.class, args);
    }


}
