import entities.Board;
import entities.BoardDetail;
import entities.Member;
import entities.TableBoard;

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
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(EntityManager em) {
//        Board board = new Board();
//        board.setAuthor("라이언");
//        em.persist(board);
//        System.out.println("board.id = " + board.getId());

        BoardDetail boardDetail = new BoardDetail();
        boardDetail.setAuthor("어피치");
        System.out.println(">>> em.persist(boardDetail);");
        em.persist(boardDetail);
        System.out.println("boardDetail.id = " + boardDetail.getId());

//        BoardDetail board1 = new BoardDetail();
//        board1.setAuthor("라이언");
//        System.out.println(">>> em.persist(board1);");
//        em.persist(board1);
//
//
//        BoardDetail board2 = new BoardDetail();
//        board2.setAuthor("어피치");
//        System.out.println(">>> em.persist(board2);");
//        em.persist(board2);
//
//
//        BoardDetail board3 = new BoardDetail();
//        board3.setAuthor("제이지");
//        System.out.println(">>> em.persist(board3);");
//        em.persist(board3);
//
//
//        BoardDetail board4 = new BoardDetail();
//        board4.setAuthor("춘식이");
//        System.out.println(">>> em.persist(board4);");
//        em.persist(board4);

//        TableBoard board1 = new TableBoard();
//        board1.setAuthor("라이언");
//        System.out.println(">>> em.persist(board1);");
//        em.persist(board1);
    }

}