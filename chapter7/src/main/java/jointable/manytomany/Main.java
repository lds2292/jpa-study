package jointable.manytomany;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jointable.manytomany");

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
        child.setName("자식1");
        em.persist(child);

        Child child2 = new Child();
        child2.setName("자식2");
        em.persist(child2);

        Parent parent = new Parent();
        parent.setName("부모1");
        parent.getChild().add(child);
        parent.getChild().add(child2);
        em.persist(parent);

        Parent parent1 = new Parent();
        parent1.setName("누구냐 넌");
        parent1.getChild().add(child);
        parent1.getChild().add(child2);
        em.persist(parent1);
    }
}
