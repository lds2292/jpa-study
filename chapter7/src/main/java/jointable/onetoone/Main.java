package jointable.onetoone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jointable.onetoone");

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

        Parent parent = new Parent();
        parent.setName("부모");
        parent.setChild(child);
        em.persist(parent);

    }
}
