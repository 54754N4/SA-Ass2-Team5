package rmit.team5.external.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rmit.team5.external.DTO.LesionDTO;
import rmit.team5.external.Model.LesionInfo.Lesion;
import rmit.team5.external.Service.Interface.ILesionService;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/lesion")
@CrossOrigin
public class LesionController {
    private static final String MESSAGE = "message";
    private ILesionService lesionService;

    @Autowired
    public LesionController(ILesionService lesionService) {
        this.lesionService = lesionService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addLesion(@Valid @RequestBody LesionDTO lesionDTO) {
        if (lesionService.add(lesionDTO)) return okResponse();
        return badResponse();
    }

    @PutMapping(path = "/update/{ID}")
    public ResponseEntity<?> updateLesion(
            @PathVariable Long ID,
            @Valid @RequestBody LesionDTO lesionDTO) {
        if (ID != null && lesionService.update(lesionDTO, ID)) return okResponse();
        return badResponse();
    }

    @GetMapping(path = "/visit/{visitID}")
    public ResponseEntity<?> getVisitLesions(
            @PathVariable String visitID,
            @RequestParam(required = false, defaultValue = "1") Integer page) {
        return ResponseEntity.ok(lesionService.getLesionsOfVisit(visitID, page));
    }

    @GetMapping(path = "/location")
    public ResponseEntity<?> getVisitLocation (@RequestParam String visitID, @RequestParam String location) {
        return ResponseEntity.ok(lesionService.getLesionHistory(visitID, location));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getLesions(
            @RequestParam String keyword,
            @RequestParam(required = false) Boolean sizeDesc,
            @RequestParam(required = false) Boolean dateDesc,
            @RequestParam(required = false, defaultValue = "1") Integer page) {
        return ResponseEntity.ok(lesionService.getLesionsMatching(keyword, dateDesc, sizeDesc, page));
    }

    @GetMapping
    public ResponseEntity<?> getLesionById(@RequestParam(name = "id") List<Long> ids) {
        List<Lesion> lesions = new ArrayList<>();
        for (Long id : ids)
            lesions.add(lesionService.getLesion(id));    // can add null, that way frontend knows which id doesn't exist
        return ResponseEntity.ok(lesions);
    }

    private static HashMap<String, String> createMessage(String msg) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MESSAGE, msg);
        return map;
    }

    private static HashMap<String, String> okMessage() {
        return createMessage("success");
    }

    private static ResponseEntity<?> okResponse() {
        return ResponseEntity.ok(okMessage());
    }

    private static ResponseEntity<?> badResponse() {
        return ResponseEntity.badRequest().body("Bad request");
    }
}
