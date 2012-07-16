package com.cecere.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.cecere.dlna.domain.Content;
import com.cecere.mediacloud.config.ClingConfig;
import com.cecere.mediacloud.config.ControllerConfig;
import com.cecere.mediacloud.config.RepositoryConfig;
import com.cecere.mediacloud.config.ServiceConfig;


@EnableWebMvc
@Configuration
@Import({ControllerConfig.class,ServiceConfig.class,RepositoryConfig.class,ClingConfig.class,PersistenceConfig.class})
//@ImportResource( "classpath*:*springDataConfig.xml")
public class WebConfig extends WebMvcConfigurerAdapter {
	  private static final Class<?>[] JAXB_CLASSES_TO_BE_BOUND = {Content.class};

	  @Bean
	  public RequestMappingHandlerAdapter getHandlerAdapter() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new StringHttpMessageConverter());

	    Jaxb2Marshaller m = new Jaxb2Marshaller();
	    m.setClassesToBeBound(JAXB_CLASSES_TO_BE_BOUND);

	    MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();

	    converter.setMarshaller(m);
	    converter.setUnmarshaller(m);

	    converters.add(converter);
	    converters.add(new MappingJacksonHttpMessageConverter());

	    RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();

	    adapter.setMessageConverters(converters);
	    return adapter;
	  }

	  @Override
	  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	    configurer.enable();
	  }
}
