package jointable.onetomany;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jointable.onetomany");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            testSave(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void testSave(EntityManager em) {
        Child child = new Child();
        child.setName("자식");
        em.persist(child);

        Child child2 = new Child();
        child2.setName("자식2");
        em.persist(child2);

        Parent parent = new Parent();
        parent.setName("부모");
        parent.getChild().add(child);
        parent.getChild().add(child2);
        em.persist(parent);

        // 다른 부모에 자식을 넣으면 에러가 남 유니크 제약조건
//        Parent parent2 = new Parent();
//        parent2.setName("완전 다른 부모");
//        parent2.getChild().add(child);
//        em.persist(parent2);

    }
}
