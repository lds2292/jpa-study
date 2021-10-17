package jpa.manytomany.entity;


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
//            findProduct(em);
            findInverse(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void findInverse(EntityManager em) {
        Product product = em.find(Product.class, "productA");
        List<Member> members = product.getMembers();
        members.forEach(member -> {
            System.out.println("member = " + member.getUsername());
        });
    }

    private static void findProduct(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        List<Product> products = member.getProducts();
        products.forEach(product -> {
            System.out.println("product.name = " + product.getName());
        });
    }

    private static void testSave(EntityManager em) {
        Product productA = new Product("productA", "상품A");
        em.persist(productA);
        Product productB = new Product("productB", "상품B");
        em.persist(productB);
        Product productC = new Product("productC", "상품C");
        em.persist(productC);

        Member member1 = new Member("member1", "회원1");
        member1.getProducts().add(productA);
        member1.getProducts().add(productB);
        member1.getProducts().add(productC);
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.getProducts().add(productB);
        em.persist(member2);
    }
}
