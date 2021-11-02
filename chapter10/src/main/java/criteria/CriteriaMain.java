package criteria;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import jpql.Member;
import jpql.Team;

public class CriteriaMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("chapter10");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
//            testSave(em);
//            whereQuery(em);
//            numberQuery(em);
//            distinctQuery(em);
//            tupleQuery(em);
//            tupleEntity(em);
//            groupBy(em);
//            joinQuery(em);
//            simpleSubQuery(em);
//            subQuery(em);
//            inQuery(em);
//            caseQuery(em);
//            parameterQuery(em);
//            nativeFunction(em);
//            jpqlDynamicQuery(em);
            criteriaDynamicQuery(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void criteriaDynamicQuery(EntityManager em) {
        // 검색 조건
        Long age = 10L;
        String username = null;
        String teamname = "팀A";

        // Criteria 동적 쿼리 생성
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);
        Join<Member, Team> t = m.join("team");

        List<Predicate> criteria = new ArrayList<>();

        if (age != null) {
            criteria.add(cb.equal(m.<Integer>get("age"), cb.parameter(Long.class, "age")));
        }
        if (username != null) {
            criteria.add(cb.equal(m.get("userName"), cb.parameter(String.class, "username")));
        }
        if (teamname != null) {
            criteria.add(cb.equal(t.get("name"), cb.parameter(String.class, "teamname")));
        }

        cq.where(cb.and(criteria.toArray(new Predicate[0])));

        TypedQuery<Member> query = em.createQuery(cq);

        if (age != null) {
            query.setParameter("age", age);
        }
        if (username != null) {
            query.setParameter("username", username);
        }
        if (teamname != null) {
            query.setParameter("teamname", teamname);
        }

        List<Member> resultList = query.getResultList();
    }

    private static void jpqlDynamicQuery(EntityManager em) {
        // 검색 조건
        Long age = 10L;
        String username = null;
        String teamname = "팀A";

        // JPQL 동적 쿼리 생성
        StringBuilder jpql = new StringBuilder("select m from Member m join m.team t");
        List<String> criteria = new ArrayList<>();

        if (age != null) {
            criteria.add(" m.age = :age ");
        }
        if (username != null) {
            criteria.add(" m.username = :username ");
        }
        if (teamname != null) {
            criteria.add(" t.name = :teamname ");
        }

        if (criteria.size() > 0) {
            jpql.append(" where ");
        }

        for (int i = 0; i < criteria.size(); i++) {
            if (i > 0) {
                jpql.append(" and ");
            }
            jpql.append(criteria.get(i));
        }

        TypedQuery<Member> query = em.createQuery(jpql.toString(), Member.class);
        if (age != null) {
            query.setParameter("age", age);
        }
        if (username != null) {
            query.setParameter("username", username);
        }
        if (teamname != null) {
            query.setParameter("teamname", teamname);
        }

        List<Member> resultList = query.getResultList();
    }

    private static void nativeFunction(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Member> m = cq.from(Member.class);

        Expression<Long> function = cb.function("SUM", Long.class, m.get("age"));
        cq.select(function);
        em.createQuery(cq);
    }

    private static void parameterQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        // 정의
        cq.select(m)
            .where(
                cb.equal(m.get("userName"), cb.parameter(String.class, "usernameParam"))
            );

        List<Member> resultList = em.createQuery(cq)
            .setParameter("usernameParam", "회원1")
            .getResultList();
    }

    private static void caseQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Member> m = cq.from(Member.class);

        cq.multiselect(
            m.get("userName"),
            cb.selectCase()
                .when(cb.ge(m.<Integer>get("age"), 60L), 600L)
                .when(cb.le(m.<Integer>get("age"), 15L), 500L)
                .otherwise(1000L)
        );

        em.createQuery(cq).getResultList();
    }

    private static void inQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        cq.select(m)
            .where(cb.in(m.get("userName"))
                .value("회원1")
                .value("회원2"));

        em.createQuery(cq).getResultList();
    }

    private static void subQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);

        // 서브쿼리에서 사용되는 메인 쿼리의 m
        Root<Member> m = mainQuery.from(Member.class);

        // 서브쿼리 생성
        Subquery<Team> subquery = mainQuery.subquery(Team.class);
        // 메인 쿼리의 별칭을 가져옴
        Root<Member> subM = subquery.correlate(m);

        Join<Member, Team> t = subM.join("team");
        subquery.select(t)
            .where(cb.equal(t.get("name"), "팀A"));

        // 메인 쿼리 생성
        mainQuery.select(m)
            .where(cb.exists(subquery));

        List<Member> resultList = em.createQuery(mainQuery).getResultList();

    }

    private static void simpleSubQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);

        // 서브쿼리 생성
        Subquery<Double> subquery = mainQuery.subquery(Double.class);
        Root<Member> m2 = subquery.from(Member.class);
        subquery.select(cb.avg(m2.<Integer>get("age")));

        // 메인쿼리 생성
        Root<Member> m = mainQuery.from(Member.class);
        mainQuery.select(m)
            .where(cb.ge(m.<Integer>get("age"), subquery));

        for (Member member : em.createQuery(mainQuery).getResultList()) {
            System.out.println(member.getUserName());
        }
    }

    private static void joinQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Member> m = cq.from(Member.class);
        Join<Member, Team> t = m.join("team", JoinType.INNER);

        cq.multiselect(m, t)
            .where(cb.equal(t.get("name"), "팀A"));
        List<Object[]> resultList = em.createQuery(cq).getResultList();
    }

    private static void groupBy(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Member> m = cq.from(Member.class);

        Expression maxAge = cb.max(m.<Integer>get("age"));
        Expression minAge = cb.min(m.<Integer>get("age"));

//        cq.multiselect(m.get("userName"), maxAge, minAge);
//        cq.groupBy(m.get("userName"));

        cq.multiselect(m.get("team").get("name"), maxAge, minAge);
        cq.groupBy(m.get("team").get("name"));
        cq.having(cb.gt(minAge, 10));

        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> resultList = query.getResultList();

    }

    private static void tupleEntity(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Member> m = cq.from(Member.class);
        cq.select(cb.tuple(
            m.alias("m"),
            m.get("userName").alias("userName")
        ));

        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> resultList = query.getResultList();
        for (Tuple tuple : resultList) {
            Member member = tuple.get("m", Member.class);
            String username = tuple.get("userName", String.class);
        }
    }

    private static void tupleQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        // CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Member> m = cq.from(Member.class);
        cq.multiselect(
            m.get("username").alias("username"),
            m.get("age").alias("age")
        );

        TypedQuery<Tuple> query = em.createQuery(cq);
        List<Tuple> resultList = query.getResultList();
        for (Tuple tuple : resultList) {
            String username = tuple.get("username", String.class);
            Integer age = tuple.get("age", Integer.class);
        }
    }

    private static void distinctQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Member> m = cq.from(Member.class);
        cq.multiselect(m.get("userName"), m.get("age")).distinct(true);
//        cq.select(cb.array(m.get("userName"), m.get("age"))).distinct(true);

        TypedQuery<Object[]> query = em.createQuery(cq);
        List<Object[]> resultList = query.getResultList();
    }

    private static void numberQuery(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> m = cq.from(Member.class);

        //타입 정보 필요
//        Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10);
        Predicate ageGt = cb.gt(m.get("age"), 10);

        cq.select(m)
            .where(ageGt)
            .orderBy(cb.desc(m.get("age")));
        List<Member> members = em.createQuery(cq).getResultList();
    }

    private static void whereQuery(EntityManager em) {
        // JPQL
        // select m from Member m
        // where m.username = '회원1'
        // order by m.age desc

        // Criteria 쿼리 빌더
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // Criteria 생성, 반환 타입 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        // FROM 절
        Root<Member> m = cq.from(Member.class);
        // 검색조건 정의
        Predicate usernameEqual = cb.equal(m.get("userName"), "회원1");
        // 정렬조건 정의
        Order ageDesc = cb.desc(m.get("age"));
        // 쿼리생성
        cq.select(m)
            .where(usernameEqual)
            .orderBy(ageDesc);
        List<Member> resultList = em.createQuery(cq).getResultList();

    }

    private static void testSave(EntityManager em) {
        // JPQL : select m from Member m

        // Criteria 쿼리 빌더
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // Criteria 생성, 반환 타입 지정
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);

        // FROM 절
        Root<Member> m = cq.from(Member.class);
        // SELECT 절
        cq.select(m);

        TypedQuery<Member> query = em.createQuery(cq);
        List<Member> members = query.getResultList();
    }
}
