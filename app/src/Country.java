// This class is a part of the 4th "sprint" of the database labs
// running in Saint-Petersburg Academic University in Fall'16
//
// Author: Dmitry Barashev
// License: WTFPL

import javax.persistence.*;

/**
 * Entity object corresponding to a single row in Spacecraft table
 */
@Entity
@Table(name = "Country")
public class Country {

    @Column
    @Id
    @GeneratedValue
    private Integer id;

    @Column private String name;

    // Required by ORM
    public Country() {}

    /*
    We dont rly need this method now, but let it be
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Country {");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
