package rmit.team5.visiderm.Model.VisitInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import rmit.team5.visiderm.Model.PatientInfo.Patient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitID")
    private long id;

    @Column(name = "doctor")
    private String doctorName;

    @Column(name = "visitDate")
    @Temporal(TemporalType.DATE)
    private Date visitDate;

    @Column(name = "endTime")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    private Date endTime;

    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    private Date startTime;

    @Column(name = "visitNote")
    private String visitNote;

    @Column(name = "reason")
    private String reason;

    @Column(name = "clinic")
    private String clinic;

    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    @JoinColumn(name = "patientID", referencedColumnName = "patientID")
    private Patient patient;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LesionCoordinates> lesionCoordinates; // use for storing lesion location on body pictures
    //private List<Lesion> listOfLesion;

    // constructors
    public Visit() {
        lesionCoordinates = new ArrayList<>();
    }

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitNote() {
        return visitNote;
    }

    public void setVisitNote(String visitNote) {
        this.visitNote = visitNote;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

//        public List<Lesion> getListOfLesion() {
//        return listOfLesion;
//    }
//
//    public void setListOfLesion(List<Lesion> listOfLesion) {
//        this.listOfLesion = listOfLesion;
//    }


    public List<LesionCoordinates> getLesionCoordinates() {
        return lesionCoordinates;
    }

    public void setLesionCoordinates(List<LesionCoordinates> lesionCoordinates) {
        this.lesionCoordinates = lesionCoordinates;
    }
}
