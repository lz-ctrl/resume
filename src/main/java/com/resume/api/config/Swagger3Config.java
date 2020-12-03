package com.resume.api.config;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Swagger3 尝鲜
 *
 * @author lz
 * @version 1.0
 */
@Configuration
public class Swagger3Config {


    /**
     * 设置一个开关，生产版本为false，关闭swagger
     */
    @Value("${swagger.ebable}")
    private boolean ebable;

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.OAS_30)
                .enable(ebable)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.resume.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("resume接口文档")
                .description("文档描述")
                .version("1.0")
                .build();
    }


    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return Ordered.LOWEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                return newArrayList(newRule(resolver.resolve(Pageable.class), resolver.resolve(Page.class)));
            }
        };
    }

    @ApiModel
    static class Page {
        @ApiModelProperty("第page页,从0开始计数")
        private Integer page;

        @ApiModelProperty("每页数据数量")
        private Integer size;

        @ApiModelProperty("按属性排序格式:属性(,asc|desc)")
        private List<String> sort;


        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public List<String> getSort() {
            return sort;
        }

        public void setSort(List<String> sort) {
            this.sort = sort;
        }
    }


}