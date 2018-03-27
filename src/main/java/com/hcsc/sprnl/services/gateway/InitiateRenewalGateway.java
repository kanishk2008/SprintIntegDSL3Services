package com.hcsc.sprnl.services.gateway;
import com.hcsc.sprnl.services.domain.Process;
import com.hcsc.sprnl.services.domain.RestClientBean;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway(errorChannel = "renewalErrorChannel")
public interface InitiateRenewalGateway {
	@Gateway(requestChannel = "initiateRenewal")
	@Payload("new java.util.Date()")
	RestClientBean generate();
	
}
	