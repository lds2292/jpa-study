package jpql;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewItem is a Querydsl query type for NewItem
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNewItem extends EntityPathBase<NewItem> {

    private static final long serialVersionUID = 814473638L;

    public static final QNewItem newItem = new QNewItem("newItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // custom
    public final ext.java.lang.QString name = new ext.java.lang.QString(forProperty("name"));

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QNewItem(String variable) {
        super(NewItem.class, forVariable(variable));
    }

    public QNewItem(Path<? extends NewItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewItem(PathMetadata metadata) {
        super(NewItem.class, metadata);
    }

}

