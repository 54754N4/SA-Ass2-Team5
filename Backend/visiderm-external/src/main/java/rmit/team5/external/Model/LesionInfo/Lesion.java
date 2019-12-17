package rmit.team5.external.Model.LesionInfo;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lesion")
public class Lesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lesionID;

//    @Column(name = "timeTake")
//    private Date timeTaken;
//
//    @Column(name = "size")
//    private double size;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;

    @Column(name = "visitID")
    private Long visitID;

    @OneToMany(mappedBy = "lesion", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LesionHistory> historyList = new ArrayList<>();

    public long getLesionID() {
        return lesionID;
    }

    public void setLesionID(long lesionID) {
        this.lesionID = lesionID;
    }

//    public Date getTimeTaken() {
//        return timeTaken;
//    }
//
//    public void setTimeTaken(Date timeTaken) {
//        this.timeTaken = timeTaken;
//    }
//
//    public double getSize() {
//        return size;
//    }
//
//    public void setSize(double size) {
//        this.size = size;
//    }

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

//    public List<Long> getLesionHistory() {
//        return lesionHistory;
//    }
//
//    public void setLesionHistory(List<Long> lesionHistory) {
//        this.lesionHistory = lesionHistory;
//    }

    public List<LesionHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<LesionHistory> historyList) {
        this.historyList = historyList;
    }

    public Long getVisitID() {
        return visitID;
    }

    public void setVisitID(Long visitID) {
        this.visitID = visitID;
    }

    public void addLesionHistory(LesionHistory history) {
        historyList.add(history);
        history.setLesion(this);
    }

    public void removeLesionHistory(LesionHistory history) {
        historyList.remove(history);
        history.setLesion(null);
    }
}
