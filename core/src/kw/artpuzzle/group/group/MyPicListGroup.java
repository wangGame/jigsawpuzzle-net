package kw.artpuzzle.group.group;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/5 14:51
 */
public class MyPicListGroup extends MyPzBaseGroup {

    public MyPicListGroup(){
        Table contentTable = getContentTable();
        for (int i = 0; i < 2; i++) {
            contentTable.add(new ItemGroup()).pad(15);
            contentTable.add(new ItemGroup()).pad(15);
            contentTable.row();
        }
    }

    public static MyPzBaseGroup getInstance() {
        return new MyPicListGroup();
    }
}
