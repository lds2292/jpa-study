package jpa.orders.entitiy.entitiy;


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
        Long orderId = 1L;
        Orders orders = em.find(Orders.class, orderId);

        Member member = orders.getMember();
        Product product = orders.getProduct();

        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount" + orders.getOrderAmount());
    }


    private static void testSave(EntityManager em) {
        Product productA = new Product("productA", "상품A");
        em.persist(productA);

        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        Orders orders = new Orders();
        orders.setMember(member1);
        orders.setProduct(productA);
        orders.setOrderAmount(3);
        em.persist(orders);
    }
}
