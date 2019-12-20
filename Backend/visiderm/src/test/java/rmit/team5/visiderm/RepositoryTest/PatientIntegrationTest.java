package rmit.team5.visiderm.RepositoryTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rmit.team5.visiderm.DAO.Intereface.IPatientDAO;
import rmit.team5.visiderm.DTO.PatientDTO;
import rmit.team5.visiderm.Model.PatientInfo.Address;
import rmit.team5.visiderm.Model.PatientInfo.ContactInfo;
import rmit.team5.visiderm.Model.PatientInfo.NextToKin;
import rmit.team5.visiderm.Model.PatientInfo.Patient;
import rmit.team5.visiderm.Service.Interface.IPatientService;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientIntegrationTest {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static boolean createdDefaultPatient = false;

    @Autowired
    private IPatientDAO patientDAO;
    @Autowired
    private IPatientService patientService;

    @Before
    public void verifyDefaultPatientCreated() {
        // create and store new lesion
        if (!createdDefaultPatient) {
            patientService.addNewPatient(createDefaultPatient());
            createdDefaultPatient = true;
        }
    }

    @Test
    public void whenPatientExists_thenVerifyRetrieval() {
        Patient patient = patientDAO.findAll().get(0);  // since DB starts empty during test
        assertThat(patient.getLastName()).isEqualTo(Default.LAST_NAME);
        assertThat(patient.getFirstName()).isEqualTo(Default.FIRST_NAME);
        assertThat(patient.getOccupation()).isEqualTo(Default.OCCUPATION);
        assertThat(patient.getEmail()).isEqualTo(Default.EMAIL);
        assertThat(patient.getTitle()).isEqualTo(Default.TITLE);
        assertThat(patient.getBirthDay().toString()).isEqualTo(DATE_FORMAT.format(Default.BIRTHDAY));
        assertThat(patient.getGender()).isEqualTo(Default.GENDER);
        assertThat(patient.isMarried()).isEqualTo(Default.MARRIED);
        Address address = patient.getHomeAddress();
        assertThat(address.getStreetAddress()).isEqualTo(Default.ADDRESS_STREET);
        assertThat(address.getSuburd()).isEqualTo(Default.SUBURB);
        assertThat(address.getCountry()).isEqualTo(Default.COUNTRY);
//        assertThat(address.getPostalCode()).isEqualTo(Default.POSTAL_CODE);
        ContactInfo contactInfo = patient.getContactInfo();
        assertThat(contactInfo.getHomePhone()).isEqualTo(Default.CI_HOME);
        assertThat(contactInfo.getOfficePhone()).isEqualTo(Default.CI_OFFICE);
        assertThat(contactInfo.getMobilePhone()).isEqualTo(Default.CI_MOBILE);
        assertThat(contactInfo.getFaxNumber()).isEqualTo(Default.CI_FAX);
        NextToKin nextToKin = patient.getNextToKin();
        assertThat(nextToKin.getName()).isEqualTo(Default.NTK_NAME);
        assertThat(nextToKin.getContactNum()).isEqualTo(Default.NTK_NUMBER);
    }

    /* Convenience methods */

    private PatientDTO createDefaultPatient() {
        PatientDTO patient = new PatientDTO();
        patient.setLastName(Default.LAST_NAME);
        patient.setFirstName(Default.FIRST_NAME);
        patient.setOccupation(Default.OCCUPATION);
        patient.setEmail(Default.EMAIL);
        patient.setTitle(Default.TITLE);
        patient.setBirthDay(Default.BIRTHDAY);
        patient.setGender(Default.GENDER);
        patient.setMarried(Default.MARRIED);
        patient.setStreetAddress(Default.ADDRESS_STREET);
        patient.setSuburd(Default.SUBURB);
        patient.setCountry(Default.COUNTRY);
        patient.setPostalCode(Default.POSTAL_CODE);
        patient.setHomePhone(Default.CI_HOME);
        patient.setOfficePhone(Default.CI_OFFICE);
        patient.setMobilePhone(Default.CI_MOBILE);
        patient.setFaxNumber(Default.CI_FAX);
        patient.setNtkName(Default.NTK_NAME);
        patient.setNtkContactInfo(Default.NTK_NUMBER);
        return patient;
    }

    private static final class Default {
        private static final String LAST_NAME = "doe", FIRST_NAME = "john",
                TITLE = "Student", OCCUPATION = "Assignment developer",
                EMAIL = "brain-dead@student.com",
            ADDRESS_STREET = "Nguyen Van Linh", SUBURB = "Phu My",
            COUNTRY = "Vietnam", POSTAL_CODE = "700000",
            NTK_NAME = "God", NTK_NUMBER = "0912345678",
            CI_HOME = "0912345677", CI_OFFICE = "0912345676",
            CI_MOBILE = "0912345675", CI_FAX = "0912345674";
        private static final char GENDER = 'M';
        private static final Date BIRTHDAY = new Date();
        private static final boolean MARRIED = false;

    }
}
