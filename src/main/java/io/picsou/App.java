package io.picsou;

import io.picsou.config.FillDatabaseComponent;
import io.picsou.service.CatalogueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

	@Bean
	@ConditionalOnProperty(prefix = "picsou.database.", value = "filldev")
	CommandLineRunner runner(final FillDatabaseComponent fillDatabaseComponent) {
		return new CommandLineRunner() {

			public void run(String... args) throws Exception {
				fillDatabaseComponent.fill();
				for(int i=0;i<10;i++){
					fillDatabaseComponent.fillObjects(100, 100, 100);
				}
			}
		};
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "picsou.database.", value = "init")
	CommandLineRunner runner1(final FillDatabaseComponent fillDatabaseComponent) {
		return new CommandLineRunner() {
			@Autowired
			CatalogueService catalogueService;
			
			public void run(String... args) throws Exception {
				if(catalogueService.getAllTypesInCatalogue().size()>0){
					System.out.println("NO FILL");
				}else{
					System.out.println("FILL");
				fillDatabaseComponent.fill();
				}
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
