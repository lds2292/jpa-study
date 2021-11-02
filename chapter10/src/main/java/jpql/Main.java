package jpql;

import jpql.type.Item;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("chapter10");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
//            testSave(em);
//            em.clear();
//            useCriteria(em);
//            typeQuery(em);
            inheritedType(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void inheritedType(EntityManager em) {
//        List<Item> items = em.createQuery("select i from Item i where type(i) IN (Book)").getResultList();
        List<Item> items = em.createQuery("select i from Item i where treat(i as Book).author = 'kim'").getResultList();
        System.out.println(">>> ITEMS");
        System.out.println(items);
    }

    private static void typeQuery(EntityManager em) {
        TypedQuery typedQuery = em.createQuery("select m from Member m", Member.class);

        List<Member> resultList = typedQuery.getResultList();
        for (Member member : resultList) {
            System.out.println("member = " + member);
        }

    }

    private static void useCriteria(EntityManager em) {
        //Criteria 사용 준비
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        // 루트 클래스(조회를 시작할 클래스)
        Root<Member> m = query.from(Member.class);

        // 쿼리 생성
        CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("userName"), "kim"));
        List<Member> resultList = em.createQuery(cq).getResultList();

    }

    private static void testSave(EntityManager em) {
        Member member = new Member();
        em.persist(member);

        String jqpl = "select m from Member as m where m.userName = 'kim'";
        List<Member> resultList = em.createQuery(jqpl, Member.class).getResultList();
    }
}
