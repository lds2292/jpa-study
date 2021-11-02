package jointable.manytoone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jointable.manytoone");

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
        Parent parent = new Parent();
        parent.setName("부모");
        em.persist(parent);

        Child child = new Child();
        child.setName("자식");
        child.setParent(parent);
        em.persist(child);

        Child child2 = new Child();
        child2.setName("자식2");
        child2.setParent(parent);
        em.persist(child2);



    }
}
