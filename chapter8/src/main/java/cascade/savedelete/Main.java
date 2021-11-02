package cascade.savedelete;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("cascade");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            testSave(em);
            tx.commit();
            em.clear();
            tx.begin();
            testDelete(em);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void testDelete(EntityManager em) {
        Parent findParent = em.find(Parent.class, 1L);
        em.remove(findParent);
    }

    private static void testSave(EntityManager em) {
        Child child1 = new Child();
        child1.setName("자식1");
        Child child2 = new Child();
        child2.setName("자식2");

        Parent parent = new Parent();
        parent.setName("부모");

        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        em.persist(parent);
    }
}
