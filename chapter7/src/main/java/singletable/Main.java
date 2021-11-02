package singletable;

import singletable.entities.Album;
import singletable.entities.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("singletable");

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

        Book book = new Book();
        book.setName("JPA 프로그래밍");
        book.setAuthor("김영한");
        book.setIsbn("1234567890");
        book.setPrice(35000);
        em.persist(book);
    }
}
