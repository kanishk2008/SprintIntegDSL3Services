package com.hcsc.sprnl.services.integration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.hcsc.sprnl.services.domain.RestClientBean;
import com.hcsc.sprnl.services.exception.MyResponseErrorHandler;

@Configuration
public class InitiateRenewalFlow {

	 @Bean
	    public MessageChannel responseChannel() {
	        return new DirectChannel() ;
	    }
	 /*@ServiceActivator
     public String handle(String payload) {
            return payload ;
     }*/
	 @Splitter
     public String[] split(String payload) {
          return StringUtils.commaDelimitedListToStringArray(payload);
     }

	@Bean
	public IntegrationFlow initiateFlow() {
		System.out.println("**********in flow1");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory());
		//restTemplate.setErrorHandler(new MyResponseErrorHandler());
		return IntegrationFlows.from("initiateRenewal")
				.log("IN  GATEWAY 1!")
				.transform(Transformers.toJson())
				.handle(Http.outboundGateway("http://localhost:8200/country-details/from/EUR/to/INR",restTemplate).charset("UTF-8")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class))
				        .transform(new  JsonToObjectTransformer(RestClientBean.class))
				    .log("after the rest api call1 ")
				.gateway("initiateRenewal1").get();
				//.handle(t -> System.out.println("AFTER REST CALL"+t.toString()+t)).get();
	}
	

	@Bean
	public IntegrationFlow initiateFlow1() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory());
		System.out.println("**********in flow2");
		return IntegrationFlows.from("initiateRenewal1")
				.transform(Transformers.toJson())
				.log("IN  GATEWAY 2 !")
				.handle(Http.outboundGateway("http://localhost:8000/currency-exchange/from/EUR/to/INR",restTemplate).charset("UTF-8")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class).errorHandler(new MyResponseErrorHandler()))
				.log("after the rest api call2 ")
				.transform(new  JsonToObjectTransformer(RestClientBean.class))
				.gateway("initiateRenewal2").get();
	}
	
	@Bean
	public IntegrationFlow initiateFlow2() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory());
		System.out.println("**********in flow3");
		return IntegrationFlows.from("initiateRenewal2")
				.transform(Transformers.toJson())
				.log("IN  GATEWAY 3 !")
				.handle(Http.outboundGateway("http://localhost:9500/currency-converter/from/EUR/to/INR/quantity/5000",restTemplate).charset("UTF-8")
                        .httpMethod(HttpMethod.GET)
                        .expectedResponseType(String.class).errorHandler(new MyResponseErrorHandler()))
				.log("after the rest api call3 ")
				.transform(new  JsonToObjectTransformer(RestClientBean.class))
				.get();
	}
	
	
	public SimpleClientHttpRequestFactory requestFactory() {
		// Disable auto redirect on 3xx HTTP responses
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		// set this value as low as possible, since we're connecting locally 
		simpleClientHttpRequestFactory.setConnectTimeout(500);
		simpleClientHttpRequestFactory.setReadTimeout(2000);
		
		return simpleClientHttpRequestFactory;
		
	}
	
	
	
	 @Bean
     public IntegrationFlow errorResponse() {
         return IntegrationFlows.from(renewalErrorChannel()).log("ERROR FLOW TRIGGERED")
                    /* .<MessagingException, Message<?>>transform(MessagingException::getFailedMessage,
                             e -> e.poller(p -> p.fixedDelay(100)))*/
                     .get();
     }

	    @Bean
	    public IntegrationFlow type1() {
	            return f -> f
	                    .enrichHeaders(h -> h.header("ABCDEF", "ABCDEF", true))
	                    .handle((p, h) -> { throw new RuntimeException("intentional"); });
	    }

	    @Bean
        public PollableChannel renewalErrorChannel() {
            return new QueueChannel();
        }
	
}
