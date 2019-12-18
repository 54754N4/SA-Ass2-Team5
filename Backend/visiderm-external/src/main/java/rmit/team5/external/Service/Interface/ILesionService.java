package rmit.team5.external.Service.Interface;

import rmit.team5.external.DTO.LesionDTO;
import rmit.team5.external.Model.LesionInfo.Lesion;

import java.util.HashMap;

public interface ILesionService {
    HashMap<String, Object> getLesionsBySize(int page, boolean desc);
    HashMap<String, Object> getLesionsByDate(int page, boolean desc);
    HashMap<String, Object> getLesionsOfVisit(int page);
    Lesion getLesion(long lesionID);

    boolean add(LesionDTO lesionDTO);
    boolean update(LesionDTO lesionDTO, long ID);
}
