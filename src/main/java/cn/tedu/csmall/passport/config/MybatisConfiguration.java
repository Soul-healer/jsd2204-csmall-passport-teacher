package cn.tedu.csmall.passport.config;

import cn.tedu.csmall.passport.interceptor.InsertUpdateTimeInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@MapperScan("cn.tedu.csmall.passport.mapper")
public class MybatisConfiguration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addInterceptor() {
        InsertUpdateTimeInterceptor interceptor = new InsertUpdateTimeInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }

}
