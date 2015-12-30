package io.picsou.config;


import io.picsou.domain.event.PersistentEvent;
import io.picsou.repository.PersistentEventRepository;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;


@Configuration
public class CustomApplicatonEventListener implements ApplicationListener<ApplicationEvent>{

	@Autowired
	PersistentEventRepository persistentEventRepository;
	
	@Override
	public void onApplicationEvent(ApplicationEvent appEvent) {
	
		
		if (appEvent instanceof AuthenticationSuccessEvent)
	    {
	        AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
	        UserDetails userDetails = (UserDetails)event.getAuthentication().getPrincipal();
	        WebAuthenticationDetails webDetails = (WebAuthenticationDetails) event.getAuthentication().getDetails();
	        PersistentEvent persistentEvent = new PersistentEvent();
	        persistentEvent.setMessage(userDetails.getUsername()+" s'est connecté depuis "+webDetails.getRemoteAddress());
	        persistentEvent.setType("AUTHENTIFICATION");
	        persistentEvent.setDate(DateTime.now().toDate());
	        persistentEventRepository.save(persistentEvent);
	    }
		
	}

	
}
