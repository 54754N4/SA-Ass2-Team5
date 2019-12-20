package rmit.team5.external.RepositoryTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import rmit.team5.external.DAO.Interface.ILesionDAO;
import rmit.team5.external.DTO.LesionDTO;
import rmit.team5.external.Model.LesionInfo.Lesion;
import rmit.team5.external.Service.Interface.ILesionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd"),
        TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
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

    @Test
    public void whenLesionAdded_thenVerify() {
        Lesion lesion = lesionDAO.getLesionsOfVisit(Default.VISIT_ID, getPageable()).getContent().get(0);
        assertThat(lesion.getDoctorNote()).isEqualTo(Default.DOCTOR_NOTE);
        assertThat(lesion.getLocation()).isEqualTo(Default.LOCATION);
        assertThat(lesion.getSize()).isEqualTo(Default.SIZE);
        assertThat(lesion.getStatus()).isEqualTo(Default.STATUS);
        assertThat(lesion.getVisitID()).isEqualTo(Default.VISIT_ID);
        assertThat(lesion.getDate().toString()).isEqualTo(DATE_FORMAT.format(Default.DATE));
        assertThat(lesion.getTimeTaken().toString()).isEqualTo(TIME_FORMAT.format(Default.TIME_TAKEN));
    }

    /* Lesion service test cases */

    @Test
    public void whenLesionExists_thenReturnNotNull() {
        List<Lesion> lesions = lesionDAO.getLesionsOfVisit(Default.VISIT_ID, getPageable()).getContent();
        assertThat(lesions).isNotNull();
        assertThat(lesions).isNotEmpty();
    }

    @Test
    public void whenLesionExists_thenCorrectMatchFinds() {
        // Search matching includes lesion size
        String keyword = ""+Default.SIZE;
        List<Lesion> lesions = lesionDAO.getLesionsMatching(keyword, getPageable()).getContent();
        assertThat(lesions).isNotNull();
        assertThat(lesions).isNotEmpty();
        boolean found = false;
        for (Lesion lesion : lesions)
            if (lesion.getSize() == Default.SIZE)
                found = true;
        assertThat(found).isTrue();
    }

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

    private static Pageable getPageable() {
        return PageRequest.of(0, 100);
    }
}
