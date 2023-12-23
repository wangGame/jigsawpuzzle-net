package kw.artpuzzle.data;

import com.badlogic.gdx.utils.Array;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 17:10
 */
public class SaveHistoryBean {
    // 6x6   8x8  ……
    private int splitNum;
    //剩下的
    private int leftNum;

    private Array<Integer> leftPicIndex = new Array<>();

    public void setLeftNum(int leftNum) {
        this.leftNum = leftNum;
    }

    public int getLeftNum() {
        return leftNum;
    }

    public void setLeftPicIndex(int leftPicIndex) {
        this.leftPicIndex.add(leftPicIndex);
    }

    public Array<Integer> getLeftPicIndex() {
        return leftPicIndex;
    }

    public void setSplitNum(int splitNum) {
        this.splitNum = splitNum;
    }

    public int getSplitNum() {
        return splitNum;
    }

    @Override
    public String toString() {
        return "SaveHistoryBean{" +
                "splitNum=" + splitNum +
                ", leftNum=" + leftNum +
                ", leftPicIndex=" + leftPicIndex +
                '}';
    }
}
