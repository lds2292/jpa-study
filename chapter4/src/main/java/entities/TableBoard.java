package entities;

import javax.persistence.*;

@Entity
@TableGenerator(
        name = "TABLE_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "TABLE_SEQ", allocationSize = 1
)
public class TableBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_SEQ_GENERATOR")
    private Long id;

    private String author;

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
