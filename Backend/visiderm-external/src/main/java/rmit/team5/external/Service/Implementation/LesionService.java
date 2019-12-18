package rmit.team5.external.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmit.team5.external.DAO.Interface.ILesionDAO;
import rmit.team5.external.DTO.LesionDTO;
import rmit.team5.external.Model.LesionInfo.Lesion;
import rmit.team5.external.Service.Interface.ILesionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LesionService implements ILesionService {
    private static final int PAGE_LIMIT = 20;
    private final ILesionDAO lesionDAO;

    @Autowired
    public LesionService(ILesionDAO lesionDAO) {
        this.lesionDAO = lesionDAO;
    }

    @Override
    public boolean add(LesionDTO lesionDTO) {
        try {
            lesionDAO.saveAndFlush(copyFromDTO(lesionDTO, new Lesion()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(LesionDTO lesionDTO, long ID) {
        Optional<Lesion> lesionRequested = lesionDAO.findById(ID);
        if (lesionRequested.isPresent()) {
            lesionDAO.saveAndFlush(copyFromDTO(lesionDTO, lesionRequested.get()));
            return true;
        } return false;
    }

    private static Lesion copyFromDTO(LesionDTO lesionDTO, Lesion lesion) {
        lesion.setTimeTaken(lesionDTO.getTimeTaken());
        lesion.setSize(lesionDTO.getSize());
        lesion.setLocation(lesionDTO.getLocation());
        lesion.setStatus(lesionDTO.getStatus());
        lesion.setVisitID(lesionDTO.getVisitID());
        lesion.setDoctorNote(lesionDTO.getDoctorNote());
        return lesion;
    }

    @Override
    public HashMap<String, Object> getLesionsOfVisit(String visitID, int page) {
        Pageable pageable = PageRequest.of(page-1, PAGE_LIMIT);
        return createQueryResultHashMap(lesionDAO.getLesionsOfVisit(visitID, pageable));
    }

    @Override
    public HashMap<String, Object> getLesionsBySize(int page, boolean desc) {
        Pageable pageable = PageRequest.of(page-1, PAGE_LIMIT);
        Page<Lesion> pagedResults = desc ?
                lesionDAO.getLesionsBySizeDesc(pageable)
                : lesionDAO.getLesionsBySizeAsc(pageable);
        return createQueryResultHashMap(pagedResults);
    }

    @Override
    public HashMap<String, Object> getLesionsByDate(int page, boolean desc) {
        Pageable pageable = PageRequest.of(page-1, PAGE_LIMIT);
        Page<Lesion> pagedResults = desc ?
                lesionDAO.getLesionsByDateDesc(pageable)
                : lesionDAO.getLesionsByDateAsc(pageable);
        return createQueryResultHashMap(pagedResults);
    }

    @Override
    public Lesion getLesion(long lesionID) {
        return lesionDAO.findById(lesionID).orElse(null);
    }

    private static HashMap<String, Object> createLesionHashMap(Lesion lesion) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("lesionID", lesion.getLesionID());
        map.put("timeTaken", lesion.getTimeTaken());
        map.put("size", lesion.getSize());
        map.put("location", lesion.getLocation());
        map.put("status", lesion.getStatus());
        map.put("visitID", lesion.getVisitID());
        map.put("doctorNote", lesion.getDoctorNote());
        return map;
    }

    private static HashMap<String, Object> createQueryResultHashMap(List<?> data, Page<?> page) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalPage", page.getTotalPages());
        result.put("currentPage", page.getPageable().getPageNumber());
        return result;
    }

    private static HashMap<String, Object> createQueryResultHashMap(Page<Lesion> page) {
        List<HashMap<String, Object>> results = new ArrayList<>();
        for (Lesion lesion : page.getContent())
            results.add(createLesionHashMap(lesion));
        return createQueryResultHashMap(results, page);
    }

}
