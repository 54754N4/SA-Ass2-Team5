/* This class is marked as Data Transfer Object
* it has the same attributes with the patient class. This class is used to validate attributes
*
*/
package rmit.team5.visiderm.DTO;

import rmit.team5.visiderm.Validator.*;

import javax.validation.constraints.*;
import java.util.Date;

public class PatientDTO {
    // patient information
    @NotBlank(message = "Patient's first name should not be blank")
    @Pattern(regexp = "[A-Z][a-z]+", message = "Invalid last name")
    private String lastName;

    @NotBlank(message = "Patient's first name should not be blank")
    @Pattern(regexp = "[A-Z][a-z]+", message = "Invalid last name")
    private String firstName;

    @NotBlank(message = "Occupation should not be blank")
    private String occupation;

    @NotBlank(message = "Email should not be blank")
    @ValidEmail(message = "Patient's email is invalid")
    private String email;

    @NotBlank(message = "Email should not be blank")
    @ValidTitle(message = "Invalid title. Title should be Mr, Mrs or Ms")
    private String title;

    @NotNull(message = "Birthday should not be empty")
    private Date birthDay;

    @NotNull(message = "Gender should not be empty")
    @ValidGender(message = "Gender should be M (male) or F (female)")
    private char gender;

    @NotNull(message = "Marry status should not be null")
    private boolean married;


    // patient address information
    @NotBlank(message = "Street address is required")
    private String streetAddress;
    @NotBlank(message = "suburd is required")
    private String suburd;
    @NotBlank(message = "country is required")
    private String country;

    // contact information
    @ValidPhone(message = "Home phone is invalid")
    private String homePhone;
    @ValidPhone(message = "Office phone is invalid")
    private String officePhone;
    @ValidPhone(message = "Mobile phone is invalid")
    private String mobilePhone;
    @ValidFaxNumber(message = "Fax number is invalid")
    private String faxNumber;

    // next to kin information
    @Pattern(regexp = "^[A-Z][a-zA-Z]+(?: [A-Z][a-zA-Z]*)*$", message = "Invalid last name")
    private String ntkName;
    @ValidPhone(message = "Phone number is invalid")
    private String ntkContactInfo;

    // constructors
    public PatientDTO () {}

    // getters and setters
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSuburd() {
        return suburd;
    }

    public void setSuburd(String suburd) {
        this.suburd = suburd;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getNtkName() {
        return ntkName;
    }

    public void setNtkName(String ntkName) {
        this.ntkName = ntkName;
    }

    public String getNtkContactInfo() {
        return ntkContactInfo;
    }

    public void setNtkContactInfo(String ntkContactInfo) {
        this.ntkContactInfo = ntkContactInfo;
    }
}
