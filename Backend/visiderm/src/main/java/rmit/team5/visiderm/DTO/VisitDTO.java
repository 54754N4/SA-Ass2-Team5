package rmit.team5.visiderm.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class VisitDTO {

    // visit information
    @NotNull(message = "Patient ID is required")
    private long patientID;

    @NotNull(message = "Visit start time is required")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    private Date startTime;

    @NotNull(message = "Visit end time is required")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    private Date endTime;

    @NotNull(message = "Date visit is required")
    @Temporal(TemporalType.DATE)
    private Date visitDate;

    @NotBlank(message = "Doctor name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+(?: [A-Z][a-zA-Z]*)*$", message = "Invalid doctor name")
    private String doctorName;

    @NotBlank(message = "Clinic name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+(?: [A-Z][a-zA-Z]*)*$", message = "Invalid clinic name")
    private String clinic;

    @NotNull(message = "Reason of visit is required")
    @Pattern(regexp = "^\\w+( \\w+)*$", message = "Invalid visit reason")
    private String visitReason;

    private String visitNote;

    // lesion information
    private Date lesionDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "hh:mm")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    private Date lesionTime;

    private Double lesionSize;

    private String lesionLocation;

    private String lesionNote;

    public VisitDTO () {}

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getVisitNote() {
        return visitNote;
    }

    public void setVisitNote(String visitNote) {
        this.visitNote = visitNote;
    }

    public Date getLesionDate() {
        return lesionDate;
    }

    public void setLesionDate(Date lesionDate) {
        this.lesionDate = lesionDate;
    }

    public Date getLesionTime() {
        return lesionTime;
    }

    public void setLesionTime(Date lesionTime) {
        this.lesionTime = lesionTime;
    }

    public Double getLesionSize() {
        return lesionSize;
    }

    public void setLesionSize(Double lesionSize) {
        this.lesionSize = lesionSize;
    }

    public String getLesionLocation() {
        return lesionLocation;
    }

    public void setLesionLocation(String lesionLocation) {
        this.lesionLocation = lesionLocation;
    }

    public String getLesionNote() {
        return lesionNote;
    }

    public void setLesionNote(String lesionNote) {
        this.lesionNote = lesionNote;
    }
}
