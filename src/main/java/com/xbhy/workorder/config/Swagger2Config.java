package com.xbhy.workorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName:
 * @Package:
 * @ClassName: Swagger2Config
 * @Author:
 * @Description:
 * @Date:
 * @Version: 1.0
 */
@Configuration
@EnableSwagger2
//@ConditionalOnProperty(name = "swagger.enable",havingValue ="true")
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        // "Authorization"
        ticketPar.name("X-ZUEL-DEMO-Token").description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("token" + " ")
                .required(true)
                .build();

        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("工单系统管理平台")
                //创建人
                .contact(new Contact("author", "url", "email"))
                //版本号
                .version("1.0")
                //描述
                .description("工单系统接口管理平台")
                .build();
    }
}

