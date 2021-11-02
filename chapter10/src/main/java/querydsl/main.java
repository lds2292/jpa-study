package querydsl;

import static jpql.QMember.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryModifiers;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import dto.ItemDTO;
import java.util.List;
import javax.naming.directory.SearchResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpql.Member;
import jpql.NewItem;
import jpql.QMember;
import jpql.QNewItem;
import jpql.QNewItemSub;
import jpql.type.Item;
import jpql.type.QItem;
import org.h2.util.StringUtils;

public class main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("chapter10");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
//            testSave(em);
//            basic(em);
//            paging(em);
//            group(em);
//            joinQuery(em);
//            subQuery(em);
//            projection(em);
//            distinct(em);
//            batchQuery(em);
//            dynamicQuery(em);
            delegateMethod(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void delegateMethod(EntityManager em) {
        QMember member = QMember.member;
        JPAQuery<Member> query = new JPAQuery<Member>(em);

        query.from(member).where(member.isOlder(10), member.userName.isHelloStart()).fetch();
    }

    private static void dynamicQuery(EntityManager em) {
        String name = "시골개발자";
        Integer age = 20;

        QMember member = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        if (name != null) {
            builder.and(member.userName.contains(name));
        }
        if (age != null) {
            builder.and(member.age.gt(age));
        }

        JPAQuery<Member> query = new JPAQuery<Member>(em);

        query.from(member).where(builder).fetch();
    }

    private static void batchQuery(EntityManager em) {
        QMember member = QMember.member;
        JPAUpdateClause updateClause = new JPAUpdateClause(em, member);
        updateClause.where(member.userName.eq("라이언"))
            .set(member.age, member.age.add(10))
            .execute();

        JPADeleteClause deleteClause = new JPADeleteClause(em, member);
        deleteClause.where(member.userName.eq("라이언킹")).execute();
    }

    private static void distinct(EntityManager em) {
        QMember member = QMember.member;
        JPAQuery<Member> query = new JPAQuery<>(em);

        query.distinct().from(member).fetch();
    }

    private static void projection(EntityManager em) {
        QItem item = QItem.item;
        JPAQuery<Item> query = new JPAQuery<>(em);
        // Single Projection
//        List<String> result = query.select(item.name).from(item).fetch();

        //Tuple
//        List<Tuple> result = query.select(item.name, item.price).from(item).fetch();
//        for (Tuple tuple : result) {
//            System.out.println("name = " + tuple.get(item.name));
//            System.out.println("price = " + tuple.get(item.price));
//        }

        query.from(item);
        // Bean
        List<ItemDTO> result =
            query.select(Projections.bean(ItemDTO.class, item.name.as("username"), item.price))
                .fetch();

        for (ItemDTO itemDTO : result) {
            System.out.println(itemDTO.toString());
        }

        // Property
        result =
            query.select(Projections.fields(ItemDTO.class, item.name.as("username"), item.price))
                .fetch();

        for (ItemDTO itemDTO : result) {
            System.out.println(itemDTO.toString());
        }

        result =
            query.select(Projections.constructor(ItemDTO.class, item.name, item.price))
                .fetch();
        for (ItemDTO itemDTO : result) {
            System.out.println(itemDTO.toString());
        }
    }

    private static void subQuery(EntityManager em) {
        QNewItem newItem = QNewItem.newItem;
        QNewItemSub newItemSub = QNewItemSub.newItemSub;

        JPAQuery<NewItem> query = new JPAQuery<>(em);
//        query.from(newItem)
//            .where(newItem.price.eq(
//                JPAExpressions.select(newItemSub.price.max()).from(newItemSub)
//            ));
//
//        System.out.println(query.toString());

        query.from(newItem)
            .where(newItem.in(
                JPAExpressions.select(newItem).from(newItemSub)
                    .where(newItem.name.eq(newItemSub.name))
            ));

        System.out.println(query.toString());
        query.fetch();
    }

    private static void joinQuery(EntityManager em) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QOrderItem orderItem = QOrderItem.orderItem;

        JPAQuery<Order> query = new JPAQuery<>(em);

//        query.from(order)
//            .join(order.member, member)
//            .leftJoin(order.orderItem, orderItem)
//            .fetch();
//
//        query.from(order)
//            .leftJoin(order.orderItem, orderItem)
//            .on(orderItem.count.gt(2))
//            .fetch();

//        query.from(order)
//            .innerJoin(order.member, member).fetchJoin()
//            .leftJoin(order.orderItem, orderItem).fetchJoin()
//            .fetch();

        query.from(order, member)
            .where(order.member.eq(member))
            .fetch();
    }

    private static void group(EntityManager em) {
        QItem item = QItem.item;
        JPAQuery<Item> query = new JPAQuery<>(em);

        query.from(item)
            .groupBy(item.price)
            .having(item.price.gt(1000))
            .fetch();
    }

    private static void paging(EntityManager em) {
        QItem item = QItem.item;
        JPAQuery<Item> query = new JPAQuery<>(em);

//        query.from(item)
//            .where(item.price.gt(20000))
//            .orderBy(item.price.desc(), item.stockQuantity.asc())
//            .offset(10).limit(20)
//            .fetch();
//
//        QueryModifiers queryModifiers = new QueryModifiers(20L, 10L);
//        query.from(item)
//            .restrict(queryModifiers)
//            .fetch();

        QueryResults<Item> result =
            query.from(item)
                .where(item.price.gt(20000))
                .offset(10).limit(20)
                .fetchResults();

        System.out.println(result.getTotal());
        System.out.println(">>>>> getResult()");
        System.out.println(result.getResults());

    }

    private static void basic(EntityManager em) {
        JPAQuery<Item> query = new JPAQuery<>(em);
        QItem item = QItem.item;

        List<Item> list = query.from(item)
            .where(item.name.eq("좋은상품").and(item.price.gt(20000)))
            .fetch();
    }

    private static void testSave(EntityManager em) {
        JPAQuery<Member> query = new JPAQuery(em);

        QMember qMember = new QMember("m");

        List<Member> members = query.from(qMember)
            .where(qMember.userName.eq("회원1"))
            .orderBy(qMember.userName.desc())
            .fetch();
    }
}
