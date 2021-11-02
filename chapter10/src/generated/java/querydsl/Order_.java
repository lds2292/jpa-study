package querydsl;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpql.Member;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, OrderItem> orderItem;
	public static volatile SingularAttribute<Order, Member> member;
	public static volatile SingularAttribute<Order, Long> id;

	public static final String ORDER_ITEM = "orderItem";
	public static final String MEMBER = "member";
	public static final String ID = "id";

}

