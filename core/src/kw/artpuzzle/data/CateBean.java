package kw.artpuzzle.data;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/9 20:44
 */
public class CateBean {
    private int sortId;
    private String desc;
    private String type;
    private String cateGory;
    private int unlockCost;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setCateGory(String cateGory) {
        this.cateGory = cateGory;
    }

    public String getCateGory() {
        return cateGory;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getSortId() {
        return sortId;
    }

    public void setUnlockCost(int unlockCost) {
        this.unlockCost = unlockCost;
    }

    public int getUnlockCost() {
        return unlockCost;
    }

    @Override
    public String toString() {
        return "CateBean{" +
                "sortId=" + sortId +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", cateGory='" + cateGory + '\'' +
                ", unlockCost=" + unlockCost +
                '}';
    }
}
