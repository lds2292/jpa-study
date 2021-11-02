package ext.java.lang;

import static com.querydsl.core.types.PathMetadataFactory.*;
import java.lang.String;

import querydsl.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QString is a Querydsl query type for String
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QString extends StringPath {

    private static final long serialVersionUID = 1195259493L;

    public static final QString string = new QString("string");

    public QString(String variable) {
        super(forVariable(variable));
    }

    public QString(Path<String> path) {
        super(path.getMetadata());
    }

    public QString(PathMetadata metadata) {
        super(metadata);
    }

    public BooleanExpression isHelloStart() {
        return ExtendExpression.isHelloStart(this);
    }

}

