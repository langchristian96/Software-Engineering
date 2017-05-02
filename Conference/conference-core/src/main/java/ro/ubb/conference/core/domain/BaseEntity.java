package ro.ubb.conference.core.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import java.io.Serializable;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @TableGenerator(name = "TABLE_GENERATOR", initialValue = 0, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
    @Column(unique = true, nullable = false)
    private ID id;


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}

