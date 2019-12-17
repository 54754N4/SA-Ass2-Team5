package rmit.team5.visiderm.Model.PatientInfo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientID")
    private long patientID;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "gender")
    private char gender;

    @Column(name = "title")
    private String title;

    @Column(name = "birthDay")
    private Date birthDay;

    @Column(name = "married")
    private boolean married;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "patient")
    private Address homeAddress;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "patient")
    private NextToKin nextToKin;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "patient")
    private ContactInfo contactInfo;

    // constructors
    public Patient() { }

    // getters and setters for database

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

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

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public NextToKin getNextToKin() {
        return nextToKin;
    }

    public void setNextToKin(NextToKin nextToKin) {
        this.nextToKin = nextToKin;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }


}
