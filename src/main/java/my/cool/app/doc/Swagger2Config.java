package my.cool.app.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().paths(paths()).build().apiInfo(apiInfo());
        //groupName("business-api")でURLパラメータ?group=businessでアクセスできる
    }

    @SuppressWarnings("unchecked")
    private Predicate<String> paths() {
        return Predicates.or(Predicates.containsPattern("/*"));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("My API", "",
                "terms of service", "", "", "", "");
        return apiInfo;
    }
}
