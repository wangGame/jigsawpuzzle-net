package kw.artpuzzle.data;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 19:30
 */
public class SelectItemBean {
    private int id;
    private int piecesnum;
    private int rewardcoin;
    private int piece;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public int getPiece() {
        return piece;
    }

    public void setRewardcoin(int rewardcoin) {
        this.rewardcoin = rewardcoin;
    }

    public int getRewardcoin() {
        return rewardcoin;
    }

    public int getPiecesnum() {
        return piecesnum;
    }

    public void setPiecesnum(int piecesnum) {
        this.piecesnum = piecesnum;
    }
}
