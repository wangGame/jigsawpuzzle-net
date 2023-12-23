package kw.artpuzzle.group.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.scrollpane.ScrollPane;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/5 15:01
 */
public abstract class MyPzBaseGroup extends Group {
    protected ScrollPane levelListScrollPanel;
    private Table contentTable;
    public MyPzBaseGroup(){
        levelListScrollPanel = new ScrollPane(contentTable = new Table(){{
            for (int i = 0; i < 2; i++) {
                Group group = new Group();
                group.setSize(465,0);
                add(group);
            }
            pack();
            row();
            align(Align.top);
        }});
        addActor(levelListScrollPanel);
        float height = Constant.GAMEHIGHT - 142 - 142 - 100;
        setSize(Constant.GAMEWIDTH,height);
        levelListScrollPanel.setSize(getWidth(),getHeight());
        levelListScrollPanel.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
    }

    public Table getContentTable() {
        return contentTable;
    }

    public static MyPzBaseGroup getInstance() {
        return null;
    }

    public void update(){}
}
