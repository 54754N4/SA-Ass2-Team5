package rmit.team5.visiderm.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rmit.team5.visiderm.DAO.Intereface.IPatientDAO;
import rmit.team5.visiderm.DAO.Intereface.IVisitDAO;
import rmit.team5.visiderm.DTO.VisitDTO;
import rmit.team5.visiderm.Exception.AllFieldValuedException;
import rmit.team5.visiderm.Exception.PatientNotFoundException;
import rmit.team5.visiderm.Exception.VisitNotFoundException;
import rmit.team5.visiderm.Model.PatientInfo.Patient;
import rmit.team5.visiderm.Model.VisitInfo.Visit;
import rmit.team5.visiderm.Service.Interface.IVisitService;

import java.util.*;

@Service
public class VisitService implements IVisitService {

    private final IVisitDAO visitDAO;
    private final IPatientDAO patientDAO;
    private static final int PAGE_LIMIT = 5;

    @Autowired
    public VisitService (IVisitDAO visitDAO, IPatientDAO patientDAO) {
        this.patientDAO = patientDAO;
        this.visitDAO = visitDAO;
    }

    @Override
    public HashMap<String, Object> getVisitOfPatient(long patientID, int page) {
        page = page -1; // the page request in JPA is started from 0 not 1
        Optional<Patient> patientCheck = patientDAO.findById(patientID);
        HashMap<String, Object> returnResult = new HashMap<>();
        if (patientCheck.isPresent()) {
            // get patient info
            Patient patient = patientCheck.get();
            String patientName = patient.getTitle() + " " + patient.getFirstName() + " " + patient.getLastName();
            returnResult.put("patientName", patientName);
            // query visit summary
            Pageable pageRequest = PageRequest.of(page, PAGE_LIMIT, Sort.by("visitDate").ascending());
            Page<Visit> queryResult = visitDAO.getVisitByPatientID(patientID, pageRequest);
            List<Visit> patientVisitList = queryResult.getContent(); // get query result
            List<HashMap<String, Object>> visitSumData = new ArrayList<>();
            if (patientVisitList.size() > 0) {
                // construct the date range of visit
                int listLength = patientVisitList.size();
                Date startDate = patientVisitList.get(0).getVisitDate();
                Date endDate = patientVisitList.get(listLength - 1).getVisitDate();
                String visitDateStr = startDate + " - " + endDate;
                returnResult.put("visitDateRange", visitDateStr);
                // add data of visit list
                for (Visit v : patientVisitList) {
                    visitSumData.add(constructVisitSum(v));
                }
            }
            returnResult.put("data", visitSumData);
            returnResult.put("currentPage", queryResult.getPageable().getPageNumber() + 1);
            returnResult.put("totalPage", queryResult.getTotalPages());
        }else {
            returnResult.put("error", "Patient ID not found");
        }
        return returnResult;
    }

    @Override
    public HashMap<String, Object> getVisitDetail(long visitID) {
        Optional<Visit> visitOptional = visitDAO.findById(visitID);
        HashMap<String, Object> visitDetail = new HashMap<>();
        if (visitOptional.isPresent()) {
            Visit visit = visitOptional.get();
            Patient patient = visit.getPatient();
            String patientName = patient.getTitle() + " " + patient.getFirstName() + " " + patient.getLastName();
            visitDetail = constructVisitSum(visit);
            visitDetail.put("patientName", patientName);
            visitDetail.put("patientID", patient.getPatientID());
            visitDetail.put("doctorNote", visit.getVisitNote());
        }else visitDetail.put("error", "Visit is not found");
        return visitDetail;
    }

    @Override
    public boolean addNewVisit(VisitDTO visitDTO) {
        boolean visitPatientValidator = validatePatientVisitInfo(visitDTO);
        boolean visitLesionValidator = validateLesionVisitInfo(visitDTO);
        if (visitLesionValidator && visitPatientValidator) {
            System.out.println("true validator");
            Visit newVisit = new Visit();
            getVisitInfoFromDTO(visitDTO, newVisit);
            long patientID = visitDTO.getPatientID();
            Optional<Patient> patientCheck = patientDAO.findById(patientID);
            Patient patient = patientCheck.get();
            newVisit.setPatient(patient);
//            if (!visitDTO.getLesionLocation().trim().isEmpty()) {
//                Lesion lesion = addNewLesion(visitDTO);
//                lesion.setVisit(newVisit);
//            }
            visitDAO.save(newVisit);
            return true;
        }else return false;
    }

    @Override
    public boolean updateVisit(VisitDTO visitDTO, long visitID) {
        Optional<Visit> checkVisit = visitDAO.findById(visitID);
        if (checkVisit.isPresent()) {
            boolean visitPatientValidator = validatePatientVisitInfo(visitDTO);
            if (!visitPatientValidator) {
                throw new PatientNotFoundException("Patient with ID: " + visitDTO.getPatientID() + " is not found");
            }
            boolean visitLesionValidator = validateLesionVisitInfo(visitDTO);
            if (visitLesionValidator) {
                Visit visit = checkVisit.get();
                getVisitInfoFromDTO(visitDTO, visit);
                visitDAO.save(visit);
//                if (visitDTO.getLesionLocation().trim().isEmpty()) {
//                    Lesion lesion = addNewLesion(visitDTO);
//                    lesion.setVisit(visit);
//                }
                return true;
            }else return false;
        }else {
            throw new VisitNotFoundException("Visit with ID: " + visitID + " is not found");
        }
    }

    private HashMap<String, Object> constructVisitSum (Visit v) {
        HashMap<String, Object> visitSum = new HashMap<>();
        visitSum.put("date", v.getVisitDate());
        String timeZone = TimeZone.getDefault().getDisplayName();
        String time = v.getStartTime() + " - " + v.getEndTime() + "(" + timeZone + ")";
        visitSum.put("time", time);
        visitSum.put("doctor", v.getDoctorName());
        visitSum.put("reason", v.getReason());
        visitSum.put("clinic", v.getClinic());
        visitSum.put("id", v.getId());
        return visitSum;
    }

    private void getVisitInfoFromDTO (VisitDTO visitDTO, Visit visit) {
        visit.setClinic(visitDTO.getClinic());
        visit.setReason(visitDTO.getVisitReason());
        visit.setStartTime(visitDTO.getStartTime());
        visit.setEndTime(visitDTO.getEndTime());
        visit.setDoctorName(visitDTO.getDoctorName());
        visit.setVisitDate(visitDTO.getVisitDate());
        visit.setVisitNote(visitDTO.getVisitNote());
    }

    private boolean validatePatientVisitInfo (VisitDTO visitDTO) {
        System.out.println("validate patient visit info");
        long patientID = visitDTO.getPatientID();
        Optional<Patient> patient = patientDAO.findById(patientID);
        return patient.isPresent();
    }

    private boolean validateLesionVisitInfo (VisitDTO visitDTO) {
        Date lesionDate = visitDTO.getLesionDate();
        Double lesionSize = visitDTO.getLesionSize();
        Date lesionTime = visitDTO.getLesionTime();
        String lesionLocation = visitDTO.getLesionLocation().trim();
        // check for the fields, if one is filled, all other fields should be field
        boolean allFieldNull = lesionDate == null && lesionTime == null && lesionSize == null && lesionLocation.isEmpty();
        boolean allFieldValued = lesionDate != null && lesionTime != null && lesionSize != null && !lesionLocation.isEmpty();

        if (!allFieldNull) {
            if (!allFieldValued) {
                String errorMess = "Fields: lesionDate, lesionSize, lesionTime and lesionLocation should be null or" +
                        " all of them are not null";
                throw new AllFieldValuedException(errorMess);
            }else return true;
        }else return true;
    }
//
//    private Lesion addNewLesion (VisitDTO visitDTO) {
//        Date lesionDate = visitDTO.getLesionDate();
//        Double lesionSize = visitDTO.getLesionSize();
//        Date lesionTime = visitDTO.getLesionTime();
//        String lesionLocation = visitDTO.getLesionLocation();
//        String lesionNote = visitDTO.getLesionNote();
//        // create lesion record
//        Lesion lesion = new Lesion();
//        lesion.setLocation(lesionLocation);
//        lesion.setStatus("NEW");
//        // create new lesion history and add it to the lesion history list
//        LesionHistory lesionHistory = new LesionHistory();
//        lesionHistory.setDateTaken(lesionDate);
//        lesionHistory.setTimeTaken(lesionTime);
//        lesionHistory.setSize(lesionSize);
//        lesionHistory.setDoctorNote(lesionNote);
//        // add to the lesion history
//        List<LesionHistory> lesionHistoryList = lesion.getHistoryList();
//        lesionHistoryList.add(lesionHistory);
//        lesion.setHistoryList(lesionHistoryList);
//        return lesion;
//    }
}
