package name.mikkoostlund.scrath.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Myentity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    Long id;

    String name;

    public Myentity() {
    }

    public Myentity(String name) {
        this.name = name;
    }
}
