package rmit.team5.external.RepositoryTest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rmit.team5.external.DAO.Interface.ILesionDAO;
import rmit.team5.external.DTO.LesionDTO;
import rmit.team5.external.Service.Interface.ILesionService;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LesionIntegrationTest {
    private static final class Default {
        private static final double SIZE = 9.99;
        private static final int ACTUAL_VISIT_ID = 1;
        private static final String HOSPITAL_SHORTNAME = "HCM",
                VISIT_ID = String.format("%s_%d", HOSPITAL_SHORTNAME, ACTUAL_VISIT_ID),
                DOCTOR_NOTE = "Mild", LOCATION = "Hips", STATUS = "Critical";
        private static final Date DATE = new Date(),
                TIME_TAKEN = new Date();
    }

    private static boolean createdDefaultLesion = false;

    @Autowired
    private ILesionDAO lesionDAO;
    @Autowired
    private ILesionService lesionService;

    @Before
    public void verifyDefaultLesionCreated() {
        // create and store new lesion
        if (!createdDefaultLesion) {
            lesionService.add(createDefaultLesion());
            createdDefaultLesion = true;
        }
    }

    /* Lesion repository test cases */


    /* Lesion service test cases */

    

    /* Convenience methods */

    private static LesionDTO createDefaultLesion() {
        LesionDTO lesionDTO = new LesionDTO();
        lesionDTO.setDate(Default.DATE);
        lesionDTO.setDoctorNote(Default.DOCTOR_NOTE);
        lesionDTO.setLocation(Default.LOCATION);
        lesionDTO.setSize(Default.SIZE);
        lesionDTO.setStatus(Default.STATUS);
        lesionDTO.setTimeTaken(Default.TIME_TAKEN);
        lesionDTO.setVisitID(Default.VISIT_ID);
        return lesionDTO;
    }
}
