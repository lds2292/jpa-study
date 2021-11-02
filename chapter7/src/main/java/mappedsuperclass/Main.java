package mappedsuperclass;

import mappedsuperclass.entities.Member;
import mappedsuperclass.entities.Seller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mappedsuperclass");

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
        Member member = new Member();
        member.setName("회원");
        member.setEmail("abcde@gmail.com");
        em.persist(member);

        Seller seller = new Seller();
        seller.setName("판매자");
        seller.setShopName("네이버 스마트 스토어");
        em.persist(seller);
    }
}
