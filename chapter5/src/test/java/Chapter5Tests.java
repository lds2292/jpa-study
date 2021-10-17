import entites.Member;
import entites.Team;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JPA를 사용하지 않는 순수한 자바 객체를 양방향 했을 경우이다
 */
class Chapter5Tests {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    @Test
    void JPA를_사용하지_않은_순수한_객체_양방향_테스트(){
        // 팀1
        Team team1 = new Team("team1", "팀");
        // 회원1, 회원2
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        // 연관관계 설정
        member1.setTeam(team1);
        member2.setTeam(team1);

        List<Member> members = team1.getMembers();
        assertEquals(0, members.size());
    }

    @Test
    void JPA를_사용하지_않은_순수한_객체_양방향_테스트2(){
        // 팀1
        Team team1 = new Team("team1", "팀");
        // 회원1, 회원2
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        // 연관관계 설정
        member1.setTeam(team1);
        team1.getMembers().add(member1);

        member2.setTeam(team1);
        team1.getMembers().add(member2);

        List<Member> members = team1.getMembers();
        assertEquals(2, members.size());
    }

    @Test
    void JPA_양방향_테스트(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private void logic(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        team1.getMembers().add(member1);
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        team1.getMembers().add(member2);
        em.persist(member2);

        //멤버 사이즈 체크
        assertEquals(2, team1.getMembers().size());
    }


    private void testSave() {

    }
}