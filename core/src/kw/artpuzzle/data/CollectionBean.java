package kw.artpuzzle.data;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/12 12:08
 */
public class CollectionBean {
    private int sortId;
    private String levelUUID;
    private int levelId;
    private String desc;
    private int unlockCost;
    private String version;
    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getLevelUUID() {
        return levelUUID;
    }

    public void setLevelUUID(String levelUUID) {
        this.levelUUID = levelUUID;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUnlockCost() {
        return unlockCost;
    }

    public void setUnlockCost(int unlockCost) {
        this.unlockCost = unlockCost;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
