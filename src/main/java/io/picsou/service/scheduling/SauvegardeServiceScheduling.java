package io.picsou.service.scheduling;

import io.picsou.App;
import io.picsou.controller.CatalogueController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(prefix = "picsou.schedule.", value = "sauvegarde")
public class SauvegardeServiceScheduling {

	private final Logger log = LoggerFactory
			.getLogger(CatalogueController.class);

	
	@Autowired
	ExecutionShellService executionShellService;
	
	@Autowired
	Environment env;
	
	//@Scheduled(cron = "0 0 0 * * *")
	@Scheduled(cron = "${picsou.schedule.sauvegarde_cron}")
	public void sauvegarde(){
		String path = "";
		try {
			path = env.getProperty("picsou.schedule.scriptPath");
			log.info(path);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executionShellService.execute(path+"/entretien.sh");
		executionShellService.execute(path+"/sauv.sh");
	}
	
}
