package idclass.entities;

import javax.persistence.*;

@Entity
public class Child {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1")
    @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")
    private Parent parent;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
