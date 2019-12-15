package rmit.team5.visiderm.Model.PatientInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "streetAddress")
    private String streetAddress;

    @Column(name = "suburd")
    private String suburd;

    @Column(name = "country")
    private String country;

    @MapsId
    @OneToOne
    @JoinColumn(name = "patientID")
    private Patient patient;

    public Address () {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Patient getPatient() {
        return patient;
    }

    @JsonIgnore
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return streetAddress + " " + suburd + " " + country;
    }
}
