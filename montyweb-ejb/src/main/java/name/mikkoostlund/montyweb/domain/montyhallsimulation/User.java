package name.mikkoostlund.montyweb.domain.montyhallsimulation;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

@Entity
@Table(name = "Principals")
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="PrincipalID", length=64, nullable = false, unique = true)
    private String name;

    @Column(name="Password", length=64, nullable = false, unique = false)
    private String password;

    public Long getId()
    {
        return this.id;
    }

    public String getName()
    {
       return this.name;
    }

    @Override
    public String toString()
    {
       String result = getClass().getSimpleName() + " ";
       if (id != null)
          result += "id: " + id;
       return result;
    }

    @Override
    public boolean equals(Object obj)
    {
       if (this == obj)
       {
          return true;
       }
       if (!(obj instanceof User))
       {
          return false;
       }
       User other = (User) obj;
       if (id != null)
       {
          if (!id.equals(other.id))
          {
             return false;
          }
       }
       return true;
    }

    @Override
    public int hashCode()
    {
       final int prime = 31;
       int result = 1;
       result = prime * result + ((id == null) ? 0 : id.hashCode());
       return result;
    }
}
