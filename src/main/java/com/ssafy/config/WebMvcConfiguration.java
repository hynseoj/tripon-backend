package com.ssafy.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.ssafy.interceptor.ConfirmInterceptor;

//import lombok.extern.slf4j.Slf4j;

//add~: 		기본 설정이 없는 빈들에 대하여 새로운 빈을 추가함
//configure~: 	수정자를 통해 기존의 설정을 대신하여 등록함
//extend~: 		기존의 설정을 이용하며 추가로 설정을 확장함

//@Slf4j
@Configuration
@EnableAspectJAutoProxy
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	private final List<String> patterns = Arrays.asList("/board/*", "/admin", "/user/list");

	@Autowired
	private ConfirmInterceptor confirmInterceptor;

	private final String uploadFilePath;

	public WebMvcConfiguration(@Value("${file.path.upload-files}") String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
//			.allowedOrigins("http://localhost:8080", "http://localhost:8081")
				.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
						HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
						HttpMethod.PATCH.name())
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
//				.allowedMethods("*")
				.maxAge(1800); // 1800초 동안 preflight 결과를 캐시에 저장
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(confirmInterceptor).addPathPatterns(patterns);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/file/**").addResourceLocations("file:///" + uploadFilePath + "/")
				.setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index2");
	}
	
//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry.jsp("/WEB-INF/views/", ".jsp");
//	}
	
//	@Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
//        messageConverters.add(createXmlHttpMessageConverter());
//    }
//
//    private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
//        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
//
//        XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
//        xmlConverter.setMarshaller(xstreamMarshaller);
//        xmlConverter.setUnmarshaller(xstreamMarshaller);
//
//        return xmlConverter;
//    }
	
}
