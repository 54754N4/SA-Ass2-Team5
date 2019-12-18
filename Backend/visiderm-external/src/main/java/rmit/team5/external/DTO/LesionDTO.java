package rmit.team5.external.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import rmit.team5.external.Validator.ValidSize;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class LesionDTO {

    @NotNull(message = "Lesion analysis time is required")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private Date timeTaken;

    @NotNull(message = "Size should not be null")
    @ValidSize(message = "Size should be strictly positive")
    private double size;

    @NotNull(message = "Location should not be null")
    @NotBlank(message = "Location should not be blank")
    private String location;

    @NotNull(message = "Status should not be null")
    @NotBlank(message = "Status should not be blank")
    private String status;

    @NotNull(message = "VisitID should not be null")
    @NotBlank(message = "VisitID should not be blank")
    private String visitID;     // actually = "<hospital_short_name>_<visit_id>"

    @NotNull(message = "Doctor should not be null")
    @NotBlank(message = "Doctor's note should not be blank")
    private String doctorNote;

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

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visitID) {
        this.visitID = visitID;
    }

    public String getDoctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String doctorNote) {
        this.doctorNote = doctorNote;
    }
}
