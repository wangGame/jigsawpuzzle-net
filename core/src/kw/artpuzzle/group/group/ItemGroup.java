package kw.artpuzzle.group.group;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.cocosload.CocosResource;

import kw.artpuzzle.constant.LevelConfig;
import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.dialog.GetPicDialog;
import kw.artpuzzle.dialog.SelectDifficultyDialog;
import kw.artpuzzle.constant.GameStaticInstance;
import kw.artpuzzle.group.ItemImage;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 22:15
 */
public class ItemGroup extends Group {
    private ItemImage itemGroup;
    private String type;
    public ItemGroup(){
        Group group = CocosResource.loadFile("cocos/levelitemview.json");
        setSize(group.getWidth(),group.getHeight());
        addActor(group);
        group.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
        setOrigin(Align.center);
    }

    public ItemGroup(LevelBean levelBean,Runnable runnable){
        Group group = CocosResource.loadFile("cocos/levelitemview.json");
        setSize(group.getWidth(),group.getHeight());
        addActor(group);
        group.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
        setOrigin(Align.center);
        itemGroup = new ItemImage(levelBean);
        Group levelitem = group.findActor("levelitem");
        levelitem.addActor(itemGroup);
        itemGroup.setPosition(group.getWidth()/2.0f,getHeight()/2.0f,Align.center);
        Actor lock = group.findActor("lock");
        lock.toFront();
        if (levelBean.getUnlockCost() > 0) {
            lock.setVisible(true);
            addListener(new OrdinaryButtonListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    GameStaticInstance.baseScreen.showDialog(new GetPicDialog(levelBean,runnable));
                }
            });
        }else {
            lock.setVisible(false);
            addListener(new OrdinaryButtonListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    LevelConfig.levelIndex = levelBean;
                    GameStaticInstance.baseScreen.showDialog(new SelectDifficultyDialog(runnable));
//                    runnable.run();
                }
            });
        }
        Group process = group.findActor("process");
        process.setVisible(false);
        process.toFront();
        Label processlabel = process.findActor("processlabel");
        processlabel.setColor(Color.valueOf("#AAAAAA"));

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void setType(String type) {
        this.type = type;
        if (type.equals("PAINT")){
            itemGroup.setMys();
        }
    }
}
