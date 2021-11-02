package querydsl;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderItem.class)
public abstract class OrderItem_ {

	public static volatile SingularAttribute<OrderItem, String> itemName;
	public static volatile SingularAttribute<OrderItem, Integer> count;
	public static volatile SingularAttribute<OrderItem, Long> id;

	public static final String ITEM_NAME = "itemName";
	public static final String COUNT = "count";
	public static final String ID = "id";

}

