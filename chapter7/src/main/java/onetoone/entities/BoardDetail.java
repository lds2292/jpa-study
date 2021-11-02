package onetoone.entities;

import javax.persistence.*;

@Entity
public class BoardDetail {
    @Id
    private Long boardId1;

    @MapsId
    @OneToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String content;

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBoardId1() {
        return boardId1;
    }

    public Board getBoard() {
        return board;
    }

    public String getContent() {
        return content;
    }
}
