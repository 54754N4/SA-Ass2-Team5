package rmit.team5.visiderm.Model.VisitInfo;

import javax.persistence.*;

@Entity
@Table(name = "LesionCoordinates")
public class LesionCoordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "xCor")
    private int xCor;
    @Column(name = "yCor")
    private int yCor;

    public LesionCoordinates () {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getxCor() {
        return xCor;
    }

    public void setxCor(int xCor) {
        this.xCor = xCor;
    }

    public int getyCor() {
        return yCor;
    }

    public void setyCor(int yCor) {
        this.yCor = yCor;
    }
}
