package jpql.type;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = 376167792L;

    public static final QBook book = new QBook("book");

    public final QItem _super = new QItem(this);

    // custom
    public final ext.java.lang.QString author = new ext.java.lang.QString(forProperty("author"));

    //inherited
    public final NumberPath<Long> id = _super.id;

    // custom
    // inherited
    public final ext.java.lang.QString name = new ext.java.lang.QString(_super.name);

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

