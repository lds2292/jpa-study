package embeddedid;

import embeddedid.entities.Child;
import embeddedid.entities.Parent;
import embeddedid.entities.ParentId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("embeddedid");

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
        parent.setId(new ParentId("myid1", "myid2"));
        parent.setName("parentName");
        em.persist(parent);

        Child child = new Child();
        child.setId("childid1");
        child.setParent(parent);
        em.persist(child);
    }
}
