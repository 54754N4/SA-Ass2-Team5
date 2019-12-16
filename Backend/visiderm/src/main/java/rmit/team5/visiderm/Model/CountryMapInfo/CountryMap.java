package rmit.team5.visiderm.Model.CountryMapInfo;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import rmit.team5.visiderm.Model.HospitalInfo.Hospital;

import javax.persistence.*;
import java.util.List;

import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name = "map")
public class CountryMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapID")
    private Long id;

    @Lob
    @Column(name="mapImage", nullable=false)
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] map;

    @OneToMany(mappedBy = "map", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade({SAVE_UPDATE, MERGE})
    private List<Hospital> hospitalList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getMap() {
        return map;
    }

    public void setMap(byte[] map) {
        this.map = map;
    }

    public List<Hospital> getHospitalList() {
        return hospitalList;
    }

    public void setHospitalList(List<Hospital> hospitalList) {
        this.hospitalList = hospitalList;
    }
}
