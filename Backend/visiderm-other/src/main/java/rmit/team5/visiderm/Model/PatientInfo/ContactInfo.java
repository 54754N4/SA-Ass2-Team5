package rmit.team5.visiderm.Model.PatientInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "contactInfo")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "homePhone")
    private String homePhone;

    @Column(name = "officePhone")
    private String officePhone;

    @Column(name = "mobilePhone")
    private String mobilePhone;

    @Column(name = "faxNumber")
    private String faxNumber;

    @MapsId
    @OneToOne
    @JoinColumn(name = "patientID")
    private Patient patient;

    // constructors
    public ContactInfo () {}

    public ContactInfo(long id, String homePhone, String officePhone, String mobilePhone, String faxNumber) {
        this.id = id;
        this.homePhone = homePhone;
        this.officePhone = officePhone;
        this.mobilePhone = mobilePhone;
        this.faxNumber = faxNumber;
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @JsonIgnore
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
