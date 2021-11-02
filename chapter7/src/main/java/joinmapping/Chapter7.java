package joinmapping;

import joinmapping.entities.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Chapter7 {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("joinmapping");

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
        Album album = new Album();
        album.setName("앨범1");
        album.setArtist("아티스트");
        album.setPrice(10000);
        em.persist(album);
    }
}
