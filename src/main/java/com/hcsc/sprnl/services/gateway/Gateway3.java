package com.hcsc.sprnl.services.gateway;
import com.hcsc.sprnl.services.domain.Process;
import com.hcsc.sprnl.services.domain.RestClientBean;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway(errorChannel = "gatewayErrorChannel")
public interface Gateway3{
	@Gateway(requestChannel = "initiateRenewal2",replyChannel="responseChannel")
	@Payload("new java.util.Date()")
	RestClientBean generate2();

}
