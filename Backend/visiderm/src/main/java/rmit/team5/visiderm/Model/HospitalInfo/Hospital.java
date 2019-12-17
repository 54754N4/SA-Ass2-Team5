package rmit.team5.visiderm.Model.HospitalInfo;

import org.hibernate.annotations.Cascade;
import rmit.team5.visiderm.Model.CountryMapInfo.CountryMap;

import javax.persistence.*;

import static org.hibernate.annotations.CascadeType.ALL;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospitalID")
    private Long id;

    @Column(nullable = false)   // uses variable name as column name by default
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Float longitude;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private String loginScreenURL;

    @ManyToOne
    @JoinColumn(name= "mapID")
    @Cascade({ALL})
    private CountryMap map;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginScreenURL() {
        return loginScreenURL;
    }

    public void setLoginScreenURL(String loginScreenURL) {
        this.loginScreenURL = loginScreenURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public CountryMap getMap() {
        return map;
    }

    public void setMap(CountryMap map) {
        this.map = map;
    }
}
