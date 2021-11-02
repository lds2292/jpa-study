
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("orphan");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            testSave(em);
            tx.commit();

            em.clear();

//            tx.begin();
//            testSelect(em);
//            tx.commit();
//
//            em.clear();

//            tx.begin();
//            testEdit(em);
//            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void testEdit(EntityManager em) {
        Member member = em.find(Member.class, 1L);

        member.setHomeAddress(new Address("새로운도시", "신도시1", "123456"));

        Set<String> favoriteFoods = member.getFavoriteFoods();
        favoriteFoods.remove("탕수육");
        favoriteFoods.add("치킨");

        List<Address> addressHistory = member.getAddressHistory();
        addressHistory.remove(new Address("서울", "강남", "123-123"));
        addressHistory.add(new Address("새로운도시", "신도시1", "123456"));
    }

    private static void testSelect(EntityManager em) {
        Member member = em.find(Member.class, 1L);

        Address address = member.getHomeAddress();

        Set<String> favoriteFoods = member.getFavoriteFoods();

        for (String favoriteFood : favoriteFoods) {
            System.out.println("favorite Food : " + favoriteFood);
        }

        List<Address> addressHistory = member.getAddressHistory();

        addressHistory.get(0);
    }

    private static void testSave(EntityManager em) {
        Member member = new Member();
        member.setHomeAddress(new Address("통영", "몽돌해수욕장", "660-123"));

        member.getFavoriteFoods().add("짬뽕");
        member.getFavoriteFoods().add("짜장면");
        member.getFavoriteFoods().add("탕수육");

        member.getAddressHistory().add(new Address("서울", "강남", "123-123"));
        member.getAddressHistory().add(new Address("서울", "강남", "123-123"));
        member.getAddressHistory().add(new Address("서울", null, "123-123"));
        member.getAddressHistory().add(new Address("서울", "강북", "000-000"));

        em.persist(member);
    }


}
