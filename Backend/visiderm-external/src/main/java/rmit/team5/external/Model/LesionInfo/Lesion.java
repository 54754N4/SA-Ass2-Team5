package rmit.team5.visiderm.Model.LesionInfo;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import rmit.team5.visiderm.Model.VisitInfo.Visit;

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

   // private List<Long> lesionHistory;  //why do we need long here ? should we have the class in lesion history

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "visitID", referencedColumnName ="visitID")
    private Visit visit;

    @OneToMany(mappedBy = "lesion", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE})
    private List<LesionHistory> historyList;

    public Lesion () {
        super();
        historyList = new ArrayList<>();
    }

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

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }
}
