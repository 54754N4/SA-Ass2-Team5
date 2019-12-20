package rmit.team5.visiderm.DTOTest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import rmit.team5.visiderm.DTO.PatientDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PatientDTOTest {

    @Parameterized.Parameter(0)
    public PatientDTO input;

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
        PatientDTO patientDTO = new PatientDTO();
        // patient DTO for last name test
        PatientDTO patientLastNameNull = patientDTOOnlyPatientLastName("");
        PatientDTO patientLastNameBlank = patientDTOOnlyPatientLastName(" ");
        PatientDTO patientLastNameOneChar = patientDTOOnlyPatientLastName("h");
        PatientDTO patientLastNameValidPattern = patientDTOOnlyPatientLastName("Dinh");
        PatientDTO patientLastNameInvalidPattern1 = patientDTOOnlyPatientLastName("dinh");
        PatientDTO patientLastNameInvalidPattern2 = patientDTOOnlyPatientLastName("@dinh");
        PatientDTO patientLastNameInvalidPattern3 = patientDTOOnlyPatientLastName("1dinh");
        PatientDTO patientLastNameInvalidPattern4 = patientDTOOnlyPatientLastName("~1dinh");

        // patient DTO for first name test
        PatientDTO patientFirstNAmeNull = patientDTOOnlyPatientFirstName("");
        PatientDTO patientFirstNAmeBlank = patientDTOOnlyPatientFirstName(" ");
        PatientDTO patientFirstNAmeOneChar = patientDTOOnlyPatientFirstName("y");
        PatientDTO patientFirstNAmeValidPattern = patientDTOOnlyPatientFirstName("Bao");
        PatientDTO patientFirstNameInvalidPattern1 = patientDTOOnlyPatientLastName("bao");
        PatientDTO patientFirstNameInvalidPattern2 = patientDTOOnlyPatientLastName("Bao1");
        PatientDTO patientFirstNameInvalidPattern3 = patientDTOOnlyPatientLastName("bao#");
        PatientDTO patientFirstNameInvalidPattern4 = patientDTOOnlyPatientLastName("b$ao1");

        // patient DTO for title test
        PatientDTO validPatientTitle1 = patientDTOforTitleTest("Mr");
        PatientDTO validPatientTitle2 = patientDTOforTitleTest("Mrs");
        PatientDTO validPatientTitle3 = patientDTOforTitleTest("Ms");
        PatientDTO invalidPatientTitle1 = patientDTOforTitleTest("MR");
        PatientDTO invalidPatientTitle2 = patientDTOforTitleTest("mRs");
        PatientDTO invalidPatientTitle3 = patientDTOforTitleTest("!3");
        PatientDTO invalidPatientTitle4 = patientDTOforTitleTest(" ");
        PatientDTO invalidPatientTitle5 = patientDTOforTitleTest("");

        // patient DTO for email test
        PatientDTO validPatientEmail1 = patientDTOForTEmailTest("ngoc12@gmail.com");
        PatientDTO validPatientEmail2 = patientDTOForTEmailTest("ngoc12@outlook.com");
        PatientDTO validPatientEmail3 = patientDTOForTEmailTest("ngoc12@apple.com");
        PatientDTO validPatientEmail4 = patientDTOForTEmailTest("ngoc12@gmail.com");
        PatientDTO validPatientEmail5 = patientDTOForTEmailTest("mailhost!username@example.org");
        PatientDTO validPatientEmail6 = patientDTOForTEmailTest("x@example.com");
        PatientDTO validPatientEmail7 = patientDTOForTEmailTest("fully-qualified-domain@example.com");
        PatientDTO invalidPatientEmail1 = patientDTOForTEmailTest("Abc.example.com");
        PatientDTO invalidPatientEmail2 = patientDTOForTEmailTest("A@b@c@example.com");
        PatientDTO invalidPatientEmail3 = patientDTOForTEmailTest("just\"not\"right@example.com");
        PatientDTO invalidPatientEmail4 = patientDTOForTEmailTest("this is\"not\\allowed@example.com");
        PatientDTO invalidPatientEmail5 = patientDTOForTEmailTest("this\\ still\\\"not\\\\allowed@example.com");
        PatientDTO invalidPatientEmail6 = patientDTOForTEmailTest("1234567890123456789012345678901234567890123456789012345678901234+x@example.com");
        PatientDTO invalidPatientEmail7 = patientDTOForTEmailTest(" ");
        PatientDTO invalidPatientEmail8 = patientDTOForTEmailTest("");
        // test birthday
        PatientDTO validBirthDay = patientDTOForBirthDayTest(new Date());
        PatientDTO invalidBirthDay = patientDTOForBirthDayTest(null);
        // test street address
        PatientDTO invalidAddress = patientDTOForStreetAddress("");
        PatientDTO invalidAddress1 = patientDTOForStreetAddress(" ");
        PatientDTO invalidAddress2 = patientDTOForStreetAddress(null);
        PatientDTO validAddress2 = patientDTOForStreetAddress("702 Nguyen Van Linh");
        // test suburb
        PatientDTO invalidSuburb = patientDTOForSuburd("");
        PatientDTO invalidSuburb1 = patientDTOForSuburd(" ");
        PatientDTO invalidSuburb2 = patientDTOForSuburd(null);
        PatientDTO validSuburb = patientDTOForSuburd("Hochiminh city");
        // test country
        PatientDTO invalidCountry = patientDTOForCountry("");
        PatientDTO invalidCountry1 = patientDTOForCountry(" ");
        PatientDTO invalidCountry2 = patientDTOForCountry(null);
        PatientDTO validCountry = patientDTOForCountry("Vietnam");
        // test faxNumber
        PatientDTO invalidFaxNumber = patientDTOForFaxNumber("1");
        PatientDTO invalidFaxNumber1 = patientDTOForFaxNumber("a");
        PatientDTO invalidFaxNumber2 = patientDTOForFaxNumber("00912312asda@");
        PatientDTO invalidFaxNumber3 = patientDTOForFaxNumber("!~!~!--08s88");
        PatientDTO validFaxNumber = patientDTOForFaxNumber("+1 323 555 1234");
        // test ntkname
        PatientDTO invalidNTK = patientDTOForNTKName("h");
        PatientDTO invalidNTK2 = patientDTOForNTKName("dinh");
        PatientDTO invalidNTK3 = patientDTOForNTKName("@dinh");
        PatientDTO invalidNTK4 = patientDTOForNTKName("1dinh");
        PatientDTO invalidNTK5 = patientDTOForNTKName("~1dinh");
        PatientDTO validNTK = patientDTOForNTKName("Dinh");
        // test ntk contact info
        PatientDTO validPhone = patientDTOForPhoneValidate("0906483591");
        PatientDTO invalidPhone = patientDTOForPhoneValidate("0906483591a");
        PatientDTO invalidPhone1 = patientDTOForPhoneValidate("0906483591+");
        PatientDTO invalidPhone2 = patientDTOForPhoneValidate("+-090648359--1");
        PatientDTO invalidPhone3 = patientDTOForPhoneValidate("0906483_91a");
        PatientDTO invalidPhone4 = patientDTOForPhoneValidate("#21321311+");
        // test occupation
        PatientDTO validOccupation = patientDTOForOccupation("Retired");
        PatientDTO invalidOccupation1 = patientDTOForOccupation("");
        PatientDTO invalidOccupation2 = patientDTOForOccupation(" ");
        PatientDTO invalidOccupation3 = patientDTOForOccupation(null);
        // test all field valid
        PatientDTO allFieldValid = allFieldValidDTO();

        Object[][] data = new Object[][]{
                // all field are null
                {patientDTO, "All field invalid",13},
                // testing last name
                {patientLastNameNull, "Last name invalid",2},
                {patientLastNameBlank, "Last name invalid",2},
                {patientLastNameOneChar, "Last name invalid",1},
                {patientLastNameInvalidPattern1, "Last name invalid",1},
                {patientLastNameInvalidPattern2, "Last name invalid",1},
                {patientLastNameInvalidPattern3, "Last name invalid",1},
                {patientLastNameInvalidPattern4, "Last name invalid",1},
                {patientLastNameValidPattern,"Last name valid", 0},
                // testing first name
                {patientFirstNAmeNull, "First name invalid",2},
                {patientFirstNAmeBlank, "First name invalid",2},
                {patientFirstNAmeOneChar, "First name invalid",1},
                {patientFirstNameInvalidPattern1, "First name invalid",1},
                {patientFirstNameInvalidPattern2, "First name invalid",1},
                {patientFirstNameInvalidPattern3, "First name invalid",1},
                {patientFirstNameInvalidPattern4, "First name invalid",1},
                {patientFirstNAmeValidPattern, "First name valid",0},
                // test title
                {validPatientTitle1, "Title valid",0},
                {validPatientTitle2, "Title valid",0},
                {validPatientTitle3, "Title valid",0},
                {invalidPatientTitle1, "Title invalid",1},
                {invalidPatientTitle2, "Title invalid",1},
                {invalidPatientTitle3, "Title invalid",1},
                {invalidPatientTitle4, "Title invalid",2},
                {invalidPatientTitle5, "Title invalid",2},
                // test email
                {validPatientEmail1, "Email valid",0},
                {validPatientEmail2, "Email valid",0},
                {validPatientEmail3, "Email valid",0},
                {validPatientEmail4, "Email valid",0},
                {validPatientEmail5, "Email valid",0},
                {validPatientEmail6, "Email valid",0},
                {validPatientEmail7, "Email valid",0},
                {invalidPatientEmail1, "Email invalid",1},
                {invalidPatientEmail2, "Email invalid",1},
                {invalidPatientEmail3, "Email invalid",1},
                {invalidPatientEmail4, "Email invalid",1},
                {invalidPatientEmail5, "Email invalid",1},
                {invalidPatientEmail6, "Email invalid",1},
                {invalidPatientEmail7, "Email invalid",2},
                {invalidPatientEmail8, "Email invalid",2},
                // test birthday
                {validBirthDay, "Birthday valid",0},
                {invalidBirthDay, "Birthday invalid",1},
                // test street address
                {invalidAddress, "Invalid address",1},
                {invalidAddress1, "Invalid address",1},
                {invalidAddress2, "Invalid address",1},
                {validAddress2, "Valid address", 0},
                // test suburb
                {invalidSuburb, "Invalid suburb", 1},
                {invalidSuburb1, "Invalid suburb", 1},
                {invalidSuburb2, "Invalid suburb", 1},
                {validSuburb, "Valid suburb", 0},
                //test country
                {invalidCountry, "Invalid country", 1},
                {invalidCountry1, "Invalid country", 1},
                {invalidCountry2, "Invalid country", 1},
                {validCountry, "Valid country", 0},
                // test fax number
                {invalidFaxNumber, "Invalid fax number", 1},
                {invalidFaxNumber1, "Invalid fax number", 1},
                {invalidFaxNumber2, "Invalid fax number", 1},
                {invalidFaxNumber3, "Invalid fax number", 1},
                {validFaxNumber, "Valid fax number", 0},
                // test ntk name
                {invalidNTK, "Invalid next to kin name",1},
                {invalidNTK4, "Invalid next to kin name",1},
                {invalidNTK2, "Invalid next to kin name",1},
                {invalidNTK3, "Invalid next to kin name",1},
                {invalidNTK5, "Invalid next to kin name",1},
                {validNTK, "Valid next to kin name",0},
                // test phone number
                {validPhone, "Valid phone number",0},
                {invalidPhone, "Invalid phone number",4},
                {invalidPhone1, "Invalid phone number",4},
                {invalidPhone2, "Invalid phone number",4},
                {invalidPhone3, "Invalid phone number",4},
                {invalidPhone4, "Invalid phone number",4},
                // test occupation
                {validOccupation, "Valid Occupation", 0},
                {invalidOccupation1, "Invalid Occupation", 1},
                {invalidOccupation2, "Invalid Occupation", 1},
                {invalidOccupation3, "Invalid Occupation", 1},
                // valid DTO
                {allFieldValid, "All Field Valid", 0}
        };
        return Arrays.asList(data);
    }


    @Test
    public void MultiplyTest() {
        assertEquals("Result", validateOutput, validateInput(input));
    }

    private static int validateInput (PatientDTO patientDTO) {
        Set<ConstraintViolation<PatientDTO>> violations = validator.validate(patientDTO);
        int invalidCounter = violations.size();
        if (invalidCounter > 0) {
            for (ConstraintViolation<PatientDTO> e : violations) {
                String mess = "Invalid: " + e.getPropertyPath().toString() + " : " + e.getMessage();
                System.out.println(mess);
            }
        }

        return violations.size();
    }

    private static PatientDTO patientDTOOnlyPatientLastName (String lastName) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setLastName(lastName);
        patientDTO.setFirstName("Bao");
        patientDTO.setCountry("Vietnam");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setGender('M');
        patientDTO.setTitle("Mr");
        patientDTO.setMarried(false);
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setPostalCode("2213");
        patientDTO.setBirthDay(new Date(System.currentTimeMillis()));
        return patientDTO;
    }

    private static PatientDTO patientDTOOnlyPatientFirstName (String firstName) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setGender('M');
        patientDTO.setTitle("Mr");
        patientDTO.setMarried(false);
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date(System.currentTimeMillis()));
        patientDTO.setFirstName(firstName);
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOforTitleTest (String title) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setTitle(title);
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setGender('M');
        patientDTO.setMarried(false);
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date(System.currentTimeMillis()));
        patientDTO.setFirstName("Bao");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }
    private static PatientDTO patientDTOForTEmailTest (String email) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail(email);
        patientDTO.setMarried(false);
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date(System.currentTimeMillis()));
        patientDTO.setFirstName("Bao");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForBirthDayTest (Date birthDay) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(birthDay);
        patientDTO.setFirstName("Bao");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForStreetAddress (String address) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress(address);
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForSuburd (String suburd) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd(suburd);
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForCountry (String country) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry(country);
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForFaxNumber (String faxNumber) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setFaxNumber(faxNumber);
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForNTKName (String NTKName) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh City");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setNtkName(NTKName);
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForPhoneValidate (String phoneNum) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh City");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setNtkName("Ly");
        patientDTO.setNtkContactInfo(phoneNum);
        patientDTO.setHomePhone(phoneNum);
        patientDTO.setMobilePhone(phoneNum);
        patientDTO.setOfficePhone(phoneNum);
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO patientDTOForOccupation (String occupation) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh city");
        patientDTO.setOccupation(occupation);
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }

    private static PatientDTO allFieldValidDTO () {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("email");
        patientDTO.setEmail("admin@gmail.com");
        patientDTO.setTitle("Mr");
        patientDTO.setLastName("Ly");
        patientDTO.setCountry("Vietnam");
        patientDTO.setGender('M');
        patientDTO.setStreetAddress("702 Nguyen Van Linh");
        patientDTO.setSuburd("Hochiminh City");
        patientDTO.setOccupation("Retired");
        patientDTO.setBirthDay(new Date());
        patientDTO.setFirstName("Bao");
        patientDTO.setNtkName("Ly");
        patientDTO.setNtkContactInfo("0906483528");
        patientDTO.setHomePhone("0906483528");
        patientDTO.setMobilePhone("0906483528");
        patientDTO.setOfficePhone("0906483528");
        patientDTO.setPostalCode("2213");

        return patientDTO;
    }
}
