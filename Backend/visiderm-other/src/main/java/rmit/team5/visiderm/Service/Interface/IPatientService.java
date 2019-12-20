package rmit.team5.visiderm.Service.Interface;

import rmit.team5.visiderm.DTO.PatientDTO;
import rmit.team5.visiderm.Model.PatientInfo.Patient;

import java.util.HashMap;

public interface IPatientService {

    // retrieve patient information
    HashMap<String, Object> getListOfPatient (int page);

    HashMap<String, Object> getPatientByName (String patientName, int page);
    HashMap<String, Object> getPatientByID (long patientID);

    Patient getPatientDetail (long patientID);

    // add new patient information
    long addNewPatient (PatientDTO patientDTO);
    boolean updateNewPatient (PatientDTO patientDTO, long ID);

}
