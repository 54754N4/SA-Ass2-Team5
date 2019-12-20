package rmit.team5.external.Service.Interface;

import rmit.team5.external.DTO.LesionDTO;
import rmit.team5.external.Model.LesionInfo.Lesion;

import java.util.HashMap;

public interface ILesionService {
    HashMap<String, Object> getLesionsOfVisit(String visitID, int page);
    HashMap<String, Object> getLesionsMatching(String keyword, Boolean dateDesc, Boolean sizeDesc, int page);
    HashMap<String, Object> getLesionHistory(String visitID, String location);
    Lesion getLesion(long lesionID);

    boolean add(LesionDTO lesionDTO);
    boolean update(LesionDTO lesionDTO, long ID);
}
