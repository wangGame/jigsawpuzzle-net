package kw.artpuzzle.group.mainview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;

import kw.artpuzzle.group.group.MyPicListGroup;
import kw.artpuzzle.group.group.MyPlayingGroup;
import kw.artpuzzle.group.group.MyPuzzleGroup;
import kw.artpuzzle.group.group.MyPzBaseGroup;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 21:30
 */
public class PuzzleView extends BaseView {
    private Group contentGroup;
    private Image selectbg;
    private Group contentTitleGroup;

    public PuzzleView(){
        contentTitleGroup = new Group();
        addActor(contentTitleGroup);
        contentTitleGroup.setSize(990,92);
        selectbg = new Image(
                new NinePatch(
                        Asset.getAsset().getTexture("common/btnpro.png"),
                        10,10,0,0));
        selectbg.setWidth(320);
        selectbg.setY(46,Align.center);
        selectbg.setColor(Color.valueOf("#34bd59"));
        Image contentTitleBg = new Image(Asset.getAsset().getTexture("common/btnbg.png"));
        contentTitleGroup.addActor(contentTitleBg);
        contentTitleGroup.addActor(selectbg);
        contentTitleBg.setPosition(contentTitleGroup.getWidth()/2.0f,contentTitleGroup.getHeight()/2.0f,Align.center);
        contentTitleGroup.setY(getHeight(),Align.top);
        String str[] = {
                "my puzzle",
                "my piclist",
                "playing"
        };
        for (int i = 0; i < 3; i++) {
            Group groupTemp = new Group();
            Label labelTitle = new Label("",new Label.LabelStyle(){{
                font = Asset.getAsset().loadBitFont("cocos/font/inter-semi-32.fnt");
            }});
            labelTitle.setAlignment(Align.center);
            labelTitle.setText(str[i]);
            labelTitle.pack();
            labelTitle.setName("label"+i);
            labelTitle.setOrigin(Align.center);
            labelTitle.setScale(1.5f);
            labelTitle.setColor(Color.BLACK);
            groupTemp.setSize(330.0f,92.0f);
            groupTemp.addActor(labelTitle);
            labelTitle.setPosition(groupTemp.getWidth()/2.0f,groupTemp.getHeight()/2.0f,Align.center);
            groupTemp.setPosition(165.0f+i*330.0f,46.0f,Align.center);
            contentTitleGroup.addActor(groupTemp);
            contentTitleGroup.setX(getWidth()/2.0f,Align.center);
            contentAddListener(groupTemp,i);
        }
        contentGroup = new Group();
        contentGroup.setSize(getWidth(),getHeight() - 100);
        addActor(contentGroup);
        contentGroup.setY(0);
        updateSelect(0);
    }

    private void contentAddListener(Group contentTitleGroup, int i) {
        contentTitleGroup.setOrigin(Align.center);
        contentTitleGroup.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                updateSelect(i);
            }
        });
    }

    private int currentIndex= -1;

    public void updateSelect(int page){
        if (page == currentIndex)return;
        this.currentIndex = page;
        selectbg.clearActions();
        selectbg.addAction(Actions.moveToAligned(165 + page * 330.f,46,Align.center,0.2f));
        contentGroup.clear();
        for (int i = 0; i < 3; i++) {
            if (page == i) {
                contentTitleGroup.findActor("label" + i).setColor(Color.WHITE);
            }else {
                contentTitleGroup.findActor("label" + i).setColor(Color.valueOf("#73798c"));
            }
        }
        if (page == 0){
            contentGroup.addActor(MyPuzzleGroup.getInstance());
        }else if (page == 1){
            contentGroup.addActor(MyPicListGroup.getInstance());
        }else if (page == 2){
            contentGroup.addActor(MyPlayingGroup.getInstance());
        }else {
            contentGroup.addActor(MyPuzzleGroup.getInstance());
        }
    }

    @Override
    public void update() {
        super.update();
        MyPzBaseGroup instance = MyPuzzleGroup.getInstance();
        instance.update();
    }
}