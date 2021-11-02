package cascade.savedelete;

import javax.persistence.*;

@Entity
public class Child {
    @Id @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    private String name;

    public Long getId() {
        return id;
    }

    public Parent getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
    }
}
