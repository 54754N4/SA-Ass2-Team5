package rmit.team5.visiderm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmit.team5.visiderm.DTO.PatientDTO;
import rmit.team5.visiderm.Model.PatientInfo.Patient;
import rmit.team5.visiderm.Service.Interface.IPatientService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/patient")
@CrossOrigin
public class PatientController {

    private IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    // use for screen 1
    @GetMapping(path = "/all")
    public ResponseEntity<?> getPatientList(@RequestParam(required = false) Integer page) {

        int pageNum = 1;
        if (page != null) pageNum = page;
        HashMap<String, Object> patientList = patientService.getListOfPatient(pageNum);
        return ResponseEntity.ok(patientList);
    }

    // use for screen 1
    @GetMapping(path = "/single")
    public ResponseEntity<?> getPatientSummaryByID(@RequestParam Long ID) {
        HashMap<String, Object> patientInfo = patientService.getPatientByID(ID);
        return ResponseEntity.ok(patientInfo);
    }

    // use for screen 1
    @GetMapping(path = "/name")
    public ResponseEntity<?> searchPatientByName(
            @RequestParam String patientName,
            @RequestParam(required = false) Integer page) {
        if (patientName == null) return ResponseEntity.badRequest().body("Bad request");
        else {
            int pageNum = 1;
            if (page != null) pageNum = page;
            HashMap<String, Object> patientInfo = patientService.getPatientByName(patientName, pageNum);
            return ResponseEntity.ok(patientInfo);
        }
    }

    // use for screen 2
    @GetMapping(path = "/detail")
    public ResponseEntity<?> getPatientDetail (@RequestParam Long ID) {
        Patient patient = patientService.getPatientDetail(ID);
        if (patient != null) return ResponseEntity.ok(patient);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewPatient (@Valid @RequestBody PatientDTO patientDTO) {
        long newPatientID = patientService.addNewPatient(patientDTO);
        HashMap<String, String> message = new HashMap<>(); // use for ajax success message
        message.put("message", "success");
        message.put("patientID", String.valueOf(newPatientID));
        return ResponseEntity.ok(message);
    }

    @PutMapping(path = "/update/{ID}")
    public ResponseEntity<?> updatePatient (@Valid @RequestBody PatientDTO patientDTO, @PathVariable Long ID) {
        boolean updatePatient = patientService.updateNewPatient(patientDTO, ID);
        HashMap<String, String> message = new HashMap<>(); // use for ajax success message
        if (updatePatient) {
            message.put("message", "success");
            return ResponseEntity.ok(message);
        }else {
            message.put("message",  "Patient ID is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

}
