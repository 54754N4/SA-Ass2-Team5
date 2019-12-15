package rmit.team5.visiderm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmit.team5.visiderm.DTO.VisitDTO;
import rmit.team5.visiderm.Service.Interface.IVisitService;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/visit")
public class VisitController {

    private final IVisitService visitService;

    @Autowired
    public VisitController(IVisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping(path = "/summary/{patientID}")
    public ResponseEntity<?> getVisitSumByPatient (
            @PathVariable long patientID,
            @RequestParam(required = false) Integer page) {
        int pageNum = 1;
        if (page != null) pageNum = page;
        HashMap<String, Object> visitList = visitService.getVisitOfPatient(patientID, pageNum);
        if (visitList.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(visitList.get("error"));
        }else {
            return ResponseEntity.ok(visitList);
        }
    }

    @GetMapping(path = "/detail/{visitID}")
    public ResponseEntity<?> getDetailVisitByPatient (@PathVariable long visitID) {
        HashMap<String, Object> visitDetail = visitService.getVisitDetail(visitID);
        if (visitDetail.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(visitDetail.get("error"));
        }else {
            return ResponseEntity.ok(visitDetail);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewVisit (@Valid @RequestBody VisitDTO visitDTO) {
        System.out.println("add new visit");
        HashMap<String, Object> returnResponse = new HashMap<>();
        boolean addNewVisit = visitService.addNewVisit(visitDTO);
        System.out.println("check add new visit: " + addNewVisit);
        if (addNewVisit) {
            returnResponse.put("message", "success");
            return ResponseEntity.ok(returnResponse);
        }else {
           // returnResponse.put("message", "success");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/update/{visitID}")
    public ResponseEntity<?> updateVisit (@Valid @RequestBody VisitDTO visitDTO, @PathVariable Long visitID) {
        HashMap<String, Object> returnResponse = new HashMap<>();
        boolean updateVisit = visitService.updateVisit(visitDTO, visitID);
        if (updateVisit) {
            returnResponse.put("message", "success");
            return ResponseEntity.ok(returnResponse);
        }else {
            // returnResponse.put("message", "success");
            return ResponseEntity.badRequest().build();
        }
    }



}
