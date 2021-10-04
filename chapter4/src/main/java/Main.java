import entities.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        Member member = createMember("memberA", "회원A");

        member.setUsername("회원명변경");

        mergeMember(member);
    }

    private static Member createMember(String id, String username) {
        // 영속성 컨텍스트1 시작
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);

        System.out.println(">>>>> em1.persist(member);");
        em1.persist(member);
        System.out.println(">>>>> tx1.commit();");
        tx1.commit();

        // 영속성 컨텍스트1 종료
        // member 엔티티는 준영속 상태가 된다.
        em1.close();

        return member;

    }

    private static void mergeMember(Member member) {
        // 영속성 컨텍스트2 시작
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        System.out.println(">>>>> Member mergeMember = em2.merge(member);");
        Member mergeMember = em2.merge(member);
//        member = em2.merge(member);
        System.out.println(">>>>> tx2.commit();");
        tx2.commit();

        // 준영속 상태
        System.out.println("member = " + member.getUsername());

        // 영속 상태
        System.out.println("mergeMember = " + mergeMember.getUsername());

        System.out.println("em2 contains member = " + em2.contains(member));
        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember));

        em2.close();
    }
}