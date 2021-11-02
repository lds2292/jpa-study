package jpql;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewItemSub is a Querydsl query type for NewItemSub
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNewItemSub extends EntityPathBase<NewItemSub> {

    private static final long serialVersionUID = 1713978042L;

    public static final QNewItemSub newItemSub = new QNewItemSub("newItemSub");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    // custom
    public final ext.java.lang.QString name = new ext.java.lang.QString(forProperty("name"));

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QNewItemSub(String variable) {
        super(NewItemSub.class, forVariable(variable));
    }

    public QNewItemSub(Path<? extends NewItemSub> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewItemSub(PathMetadata metadata) {
        super(NewItemSub.class, metadata);
    }

}

