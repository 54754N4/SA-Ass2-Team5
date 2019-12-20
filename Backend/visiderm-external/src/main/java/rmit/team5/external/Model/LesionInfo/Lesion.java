package rmit.team5.external.Model.LesionInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lesion")
public class Lesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lesionID;

    @Column(name = "timeTaken")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private Date timeTaken;

    @Column(name = "dateTaken")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "size")
    private double size;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    @Column(name = "visitID")
    private String visitID;     // actually = "<hospital_short_name>_<visit_id>"

    @Column(name = "doctorNote")
    private String doctorNote;

    public long getLesionID() {
        return lesionID;
    }

    public void setLesionID(long lesionID) {
        this.lesionID = lesionID;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String doctorNote) {
        this.doctorNote = doctorNote;
    }

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
