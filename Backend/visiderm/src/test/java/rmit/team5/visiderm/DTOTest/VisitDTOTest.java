package rmit.team5.visiderm.DTOTest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import rmit.team5.visiderm.DTO.PatientDTO;
import rmit.team5.visiderm.DTO.VisitDTO;
import rmit.team5.visiderm.Model.VisitInfo.Visit;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class VisitDTOTest {
    @Parameterized.Parameter(0)
    public VisitDTO input;

    @Parameterized.Parameter(2)
    public int validateOutput;

    @Parameterized.Parameter(1)
    public String testType;

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // creates the test data
    @Parameterized.Parameters(name = "Run {index} (test {1}): result (number of violation) {2} ")
    public static Collection<Object[]> data() {
        // all field invalid
        VisitDTO invalidVisitDTO = new VisitDTO();
        // all field valid
        VisitDTO validVisitDTO = getValidVisitDTO();
        // doctor test
        VisitDTO invalidDoctorName = getVisitDTOForDoctorNameTest("");
        VisitDTO invalidDoctorName1 = getVisitDTOForDoctorNameTest("   ");
        VisitDTO invalidDoctorName2 = getVisitDTOForDoctorNameTest(null);
        VisitDTO invalidDoctorName3 = getVisitDTOForDoctorNameTest("a");
        VisitDTO validDoctorName = getVisitDTOForDoctorNameTest("Kai");
        VisitDTO validDoctorName1 = getVisitDTOForDoctorNameTest("Thanh Nguyen");
        // clinic test
        VisitDTO invalidClinicName = getVisitDTOForVisitClinicTest("");
        VisitDTO invalidClinicName1 = getVisitDTOForVisitClinicTest("  ");
        VisitDTO invalidClinicName2 = getVisitDTOForVisitClinicTest(null);
        VisitDTO invalidClinicName3 = getVisitDTOForVisitClinicTest("k");
        VisitDTO validClinicName = getVisitDTOForVisitClinicTest("Cho Ray");
        VisitDTO validClinicName1 = getVisitDTOForVisitClinicTest("Mayo");
        // start time test
        VisitDTO invalidStartTime = getVisitDTOForStartTimeTest (null);
        VisitDTO validStartTime = getVisitDTOForStartTimeTest (new Date());
        // end time test
        VisitDTO invalidEndTime = getVisitDTOForEndTimeTest(null);
        VisitDTO validEndTime = getVisitDTOForEndTimeTest(new Date());
        // date visit test
        VisitDTO invalidVisitDate = getVisitDTOForVisitDateTest(null);
        VisitDTO validVisitDate = getVisitDTOForVisitDateTest(new Date());
        // visit reason test
        VisitDTO validVisitReason = getVisitDTOForVisitReasonTest("headache and burned when cooking");
        VisitDTO invalidVisitReason = getVisitDTOForVisitReasonTest("");
        VisitDTO invalidVisitReason1= getVisitDTOForVisitReasonTest("");
        VisitDTO invalidVisitReason2= getVisitDTOForVisitReasonTest(" ");
        VisitDTO invalidVisitReason3= getVisitDTOForVisitReasonTest(null);

        Object[][] data = new Object[][]{
                // all field are null
                {invalidVisitDTO, "All field invalid", 6},
                {validVisitDTO, "All field valid", 0},
                // doctor field test
                {invalidDoctorName, "Invalid Doctor name", 2},
                {invalidDoctorName1, "Invalid Doctor name", 2},
                {invalidDoctorName2, "Invalid Doctor name", 1},
                {invalidDoctorName3, "Invalid Doctor name", 1},
                {validDoctorName, "Valid doctor name", 0},
                {validDoctorName1, "Valid doctor name", 0},
                // clinic field test
                {validClinicName, "Valid clinic name", 0},
                {validClinicName1, "Valid clinic name", 0},
                {invalidClinicName, "Invalid clinic name", 2},
                {invalidClinicName1, "Invalid clinic name", 2},
                {invalidClinicName2, "Invalid clinic name", 1},
                {invalidClinicName3, "Invalid clinic name", 1},
                // start time test
                {validStartTime, "Valid start time", 0},
                {invalidStartTime, "Invalid start time", 1},
                // end time test
                {validEndTime, "Valid end time", 0},
                {invalidEndTime, "Invalid end time",1},
                // visit date test
                {validVisitDate, "Valid date", 0},
                {invalidVisitDate, "Invalid date", 1},
                // visit reason test
                {validVisitReason, "Valid Reason", 0},
                {invalidVisitReason, "Invalid Reason", 1},
                {invalidVisitReason1, "Invalid Reason", 1},
                {invalidVisitReason2, "Invalid Reason", 1},
                {invalidVisitReason3, "Invalid Reason", 1},
        };
        return Arrays.asList(data);
    }

    @Test
    public void MultiplyTest() {
        assertEquals("Result", validateOutput, validateInput(input));
    }

    private static int validateInput (VisitDTO visitDTO) {
        Set<ConstraintViolation<VisitDTO>> violations = validator.validate(visitDTO);
        int invalidCounter = violations.size();
        if (invalidCounter > 0) {
            for (ConstraintViolation<VisitDTO> e : violations) {
                String mess = "Invalid: " + e.getPropertyPath().toString() + " : " + e.getMessage();
                System.out.println(mess);
            }
        }
        return violations.size();
    }

    private static VisitDTO getValidVisitDTO () {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setClinic("Mayo");
        visitDTO.setDoctorName("Dinh Minh");
        visitDTO.setStartTime(new Date());
        visitDTO.setEndTime(new Date());
        visitDTO.setVisitDate(new Date());
        visitDTO.setVisitReason("Headache");
        return visitDTO;
    }

    private static VisitDTO getVisitDTOForDoctorNameTest (String doctorName) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setClinic("Mayo");
        visitDTO.setDoctorName(doctorName);
        visitDTO.setStartTime(new Date());
        visitDTO.setEndTime(new Date());
        visitDTO.setVisitDate(new Date());
        visitDTO.setVisitReason("Headache");
        return visitDTO;
    }

    private static VisitDTO getVisitDTOForStartTimeTest (Date startTime) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setClinic("Mayo");
        visitDTO.setDoctorName("Dinh Minh");
        visitDTO.setStartTime(startTime);
        visitDTO.setEndTime(new Date());
        visitDTO.setVisitDate(new Date());
        visitDTO.setVisitReason("Headache");
        return visitDTO;
    }

    private static VisitDTO getVisitDTOForEndTimeTest (Date endTime) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setClinic("Mayo");
        visitDTO.setDoctorName("Dinh Minh");
        visitDTO.setStartTime(new Date());
        visitDTO.setEndTime(endTime);
        visitDTO.setVisitDate(new Date());
        visitDTO.setVisitReason("Headache");
        return visitDTO;
    }

    private static VisitDTO getVisitDTOForVisitDateTest (Date visitDate) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setClinic("Mayo");
        visitDTO.setDoctorName("Dinh Minh");
        visitDTO.setStartTime(new Date());
        visitDTO.setEndTime(new Date());
        visitDTO.setVisitDate(visitDate);
        visitDTO.setVisitReason("Headache");
        return visitDTO;
    }

    private static VisitDTO getVisitDTOForVisitReasonTest (String visitReason) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setClinic("Mayo");
        visitDTO.setDoctorName("Dinh Minh");
        visitDTO.setStartTime(new Date());
        visitDTO.setEndTime(new Date());
        visitDTO.setVisitDate(new Date());
        visitDTO.setVisitReason(visitReason);
        return visitDTO;
    }

    private static VisitDTO getVisitDTOForVisitClinicTest (String clinic) {
        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setClinic(clinic);
        visitDTO.setDoctorName("Dinh Minh");
        visitDTO.setStartTime(new Date());
        visitDTO.setEndTime(new Date());
        visitDTO.setVisitDate(new Date());
        visitDTO.setVisitReason("Headache");
        return visitDTO;
    }
}
