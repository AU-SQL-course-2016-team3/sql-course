// This class is a part of the 4th "sprint" of the database labs
// running in Saint-Petersburg Academic University in Fall'16
//
// Author: Dmitry Barashev
// License: WTFPL

import javax.persistence.*;

/**
 * Entity object corresponding to a single row in Sportsman table
 */

@Entity
@Table(name = "Sportsman")
public class Sportsman {

    @Column
    @Id
    @GeneratedValue
    private Integer id;

    @Column private String name;
    @Column private String sex;
    @Column private Integer age;
    @Column private Integer weight;
    @Column private Integer height;
    @Column(name = "country_id") private Integer countryId;
    @Column(name = "home_id") private Integer homeId;
    @Column(name = "volunteer_id") private Integer volunteerId;

    // Required by ORM
    public Sportsman() {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sportsman {");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", age=").append(age);
        sb.append(", weight=").append(weight);
        sb.append(", height=").append(height);
        sb.append(", country_id=").append(countryId);
        sb.append(", home_id=").append(homeId);
        sb.append(", volunteer_id=").append(volunteerId);
        sb.append('}');
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public Integer getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }
}
