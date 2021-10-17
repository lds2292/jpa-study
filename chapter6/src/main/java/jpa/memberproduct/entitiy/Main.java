package jpa.memberproduct.entitiy;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            testSave(em);
            tx.commit();
            em.clear();
            tx.begin();
            find(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void find(EntityManager em) {
        //기본키 값 생성
        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");
        memberProductId.setProduct("productA");

        MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);

        Member member = memberProduct.getMember();
        Product product = memberProduct.getProduct();

        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount" + memberProduct.getOrderAmount());
    }


    private static void testSave(EntityManager em) {
        Product productA = new Product("productA", "상품A");
        em.persist(productA);

        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member1);
        memberProduct.setProduct(productA);
        memberProduct.setOrderAmount(3);
        em.persist(memberProduct);

    }
}
