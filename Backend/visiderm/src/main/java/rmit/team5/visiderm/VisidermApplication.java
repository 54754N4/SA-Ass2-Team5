package rmit.team5.visiderm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rmit.team5.visiderm.DAO.Intereface.IPatientDAO;
import rmit.team5.visiderm.Model.PatientInfo.Patient;
import rmit.team5.visiderm.Service.Implementation.PatientService;

@SpringBootApplication
public class VisidermApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisidermApplication.class, args);
	}

}
