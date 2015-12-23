package io.picsou.service.scheduling;

import io.picsou.domain.ExecutionShell;
import io.picsou.repository.ExecutionShellRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionShellService {

	@Autowired
	ExecutionShellRepository executionShellRepository;
	
	public void execute(String command){
		ExecutionShell executionShell = new ExecutionShell();
		executionShell.setCommand(command);
		executionShell.setDateExecution(DateTime.now().toDate());
		ProcessBuilder b = new ProcessBuilder(command);
		Process p;
		String result="";
		int retour=101;
		try {
			p = b.start();
			retour = p.waitFor();
			BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			
			while ((line = is.readLine ()) != null) {
				result+=line+"\n";
			}
			executionShell.setLog(result);
			executionShell.setCodeRetour(retour);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			executionShell.setLog(result);
			executionShell.setCodeRetour(retour);
		} 
		
		executionShellRepository.save(executionShell);
		
	}
}
