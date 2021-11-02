package querydsl;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import jpql.Member;
import jpql.QMember;

public class ExtendExpression {

    @QueryDelegate(Member.class)
    public static BooleanExpression isOlder(QMember member, Integer age) {
        return member.age.gt(age);
    }

    @QueryDelegate(String.class)
    public static BooleanExpression isHelloStart(StringPath stringPath) {
        return stringPath.startsWith("Hello");
    }
}
