package rmit.team5.external.Model.LesionInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lesionHistory")
public class LesionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyID")
    private long historyID;

    @Column(name = "dateTaken")
    private Date dateTaken;

    @Column(name = "timeTaken")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    private Date timeTaken;

    @Column(name = "doctorNote")
    private String doctorNote;

    @Column(name = "size")
    private double size;

    @ManyToOne
    @JoinColumn(name= "lesionID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Lesion lesion;

    public long getHistoryID() {
        return historyID;
    }

    public void setHistoryID(long historyID) {
        this.historyID = historyID;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String doctorNote) {
        this.doctorNote = doctorNote;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Lesion getLesion() {
        return lesion;
    }

    public void setLesion(Lesion lesion) {
        this.lesion = lesion;
    }
}
