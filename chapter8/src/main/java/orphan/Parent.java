package orphan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "parent",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST}
    )
    private List<Child> children = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Child> getChildren() {
        return children;
    }
}
