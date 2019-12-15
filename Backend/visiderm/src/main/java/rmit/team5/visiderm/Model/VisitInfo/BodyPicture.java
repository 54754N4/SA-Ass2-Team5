package rmit.team5.visiderm.Model.VisitInfo;

import java.awt.image.BufferedImage;

public class BodyPicture {
    private long id;
    private Visit reportedVisit;
    // i did not implement the varibles for images here
    //  private BufferedImage bodyPicture;
    // private List<Point>

    // constructors
    public BodyPicture () {}

    // getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Visit getReportedVisit() {
        return reportedVisit;
    }

    public void setReportedVisit(Visit reportedVisit) {
        this.reportedVisit = reportedVisit;
    }

    // add location to body picture
    public void addLocation () {

    }
}
