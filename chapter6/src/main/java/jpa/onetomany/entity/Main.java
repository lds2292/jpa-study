package jpa.onetomany.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

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
        Team team1 = new Team("team1");
        em.persist(team1);

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        member1.setTeam(team1);
        member2.setTeam(team1);

//        team1.getMembers().add(member1);
//        team1.getMembers().add(member2);

        em.persist(member1);
        em.persist(member2);

    }
}
