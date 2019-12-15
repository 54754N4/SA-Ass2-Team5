package rmit.team5.visiderm.Service.Interface;

import rmit.team5.visiderm.DTO.VisitDTO;
import rmit.team5.visiderm.Model.VisitInfo.Visit;

import java.util.HashMap;
import java.util.List;

public interface IVisitService {
    // retrieve visit information
    HashMap<String, Object> getVisitOfPatient (long patientID, int page);
    HashMap<String, Object> getVisitDetail (long visitID);
    boolean addNewVisit (VisitDTO visitDTO);
    boolean updateVisit (VisitDTO visitDTO, long visitID);
}
