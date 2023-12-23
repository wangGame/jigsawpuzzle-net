package kw.artpuzzle.group.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.cocosload.CocosResource;
import com.kw.gdx.screen.BaseScreen;
import com.kw.gdx.scrollpane.ScrollPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kw.artpuzzle.JigSawPuzzle;
import kw.artpuzzle.constant.LevelConfig;
import kw.artpuzzle.data.GameData;
import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.data.SaveHistoryBean;
import kw.artpuzzle.data.SelectItemBean;
import kw.artpuzzle.fileutils.FileUtils;
import kw.artpuzzle.group.group.BgTheme;
import kw.artpuzzle.jigsawfile.EnterLevelFile;
import kw.artpuzzle.jigsawfile.Jigsawfile;
import kw.artpuzzle.jigsawfile.PlayedLevelFile;
import kw.artpuzzle.listener.MyClickListener;
import kw.artpuzzle.pref.JigsawPreference;
import kw.artpuzzle.utils.GameLogicUtils;
import kw.artpuzzle.view.ModelGroup;
import kw.artpuzzle.view.ModelUtils;
import kw.artpuzzle.view.TempView;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/4 10:37
 */
public class GameView extends Group {
    private Group rootView;
    private ScrollPane bottomPanelScrollPanel;
    private GameLogicUtils logicUtils;
    private ModelUtils modelUtils ;
    private  BaseScreen baseScreen;
    private boolean isShowPre;
    private Table bottomModelTable;
    //因为排序
    private ArrayList<ModelGroup> finalModelGroup;
    private boolean showBorder;
    private  Group picGroup;
    private TempView view;

    public GameView(BaseScreen baseScreen){
       this.baseScreen = baseScreen;
       this.finalModelGroup = new ArrayList<>();
    }

    public void initView(){
        setSize(1080,1920);
        EnterLevelFile.getJigsawfile().saveLevelId(LevelConfig.levelIndex.getLevelUUID());
        setPosition(Constant.GAMEWIDTH/2.0f,Constant.GAMEHIGHT/2.0f,Align.center);
        rootView = CocosResource.loadFile("cocos/gamegroup.json");
        addActor(rootView);
//        if (JigsawPreference.getInstance().getTheme().equals(""))
        changeBg();
        rootView.setPosition(getWidth()/2.0f,getHeight()/2.0f,Align.center);
        Group gamebottom = rootView.findActor("gamebottom");
        gamebottom.setY(gamebottom.getY() - baseScreen.getOffsetY());
        Actor bottombg = gamebottom.findActor("bottombg");
        bottombg.setWidth(Constant.GAMEWIDTH);
        bottombg.setX(540,Align.center);
        Group gamemiddle = rootView.findActor("gamemiddle");
        Actor middlebg = gamemiddle.findActor("middlebg");
        middlebg.setVisible(false);
        Vector2 vector2 = new Vector2();
        vector2.set(middlebg.getX(Align.center),middlebg.getY(Align.center));
        middlebg.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT - 140.0f - 260.0f);
        middlebg.setPosition(vector2,Align.center);
        Group gametop = rootView.findActor("gametop");
        gametop.setY(gametop.getY() + baseScreen.getOffsetY());
        Group topback = gametop.findActor("topback");
        Group egebtn = gametop.findActor("egebtn");
        Group clearbtn = gametop.findActor("clearbtn");
        Group tipbtn = gametop.findActor("tipbtn");
        Group prebtn = gametop.findActor("prebtn");
        Group themebtn = gametop.findActor("themebtn");
        Actor topbg = gametop.findActor("topbg");
        topbg.setWidth(Constant.GAMEWIDTH);
        topbg.setX(540,Align.center);
        ArrayList<Actor> actors = new ArrayList<>();
        actors.add(topback);
        actors.add(egebtn);
        actors.add(clearbtn);
        actors.add(tipbtn);
        actors.add(prebtn);
        actors.add(themebtn);
        topback.setOrigin(Align.center);
        egebtn.setOrigin(Align.center);
        clearbtn.setOrigin(Align.center);
        tipbtn.setOrigin(Align.center);
        prebtn.setOrigin(Align.center);
        themebtn.setOrigin(Align.center);

        float v = Constant.GAMEWIDTH / 6.0f;
        float v1 = v / 2.0f - baseScreen.getOffsetX();
        for (int i = 0; i < actors.size(); i++) {
            Actor actor = actors.get(i);
            actor.setX(v1 + v * i,Align.center);
        }
        LevelBean levelBean = LevelConfig.levelIndex;
        modelUtils = new ModelUtils("finallevel/"
                +levelBean.getVersion()+"/"+levelBean.getLevelUUID()
                +"/"+levelBean.getLevelUUID()+".png",6,6);
        ArrayList<ModelGroup> allModels = modelUtils.getAllModels();
        finalModelGroup.addAll(allModels);
        Collections.shuffle(finalModelGroup);
        logicUtils = new GameLogicUtils(modelUtils.getTempView());

        bottomPanelScrollPanel = new ScrollPane(bottomModelTable = new Table(){{
            for (ModelGroup allModel : finalModelGroup) {
                allModel.addListener(getItemListener(allModel));
                add(allModel);
                allModel.setUpdateSize(new Runnable(){

                    @Override
                    public void run() {

                    }
                });
            }
            pack();
            align(Align.bottomLeft);
            setY(100);
        }});
        bottomPanelScrollPanel.setTouchable(Touchable.childrenOnly);
        bottomPanelScrollPanel.setSize(Constant.GAMEWIDTH,260);
        bottomPanelScrollPanel.setY(150);
        bottomPanelScrollPanel.setX(-baseScreen.getOffsetX());
        gamebottom.addActor(bottomPanelScrollPanel);
        gamebottom.toFront();
        Group picGroup = new Group();
        picGroup.setSize(1050,1050);
        view = modelUtils.getTempView();
        picGroup.addActor(view);
        gamemiddle.addActor(picGroup);
        view.setOrigin(Align.center);
        view.setScale(0.5f);
        picGroup.setPosition(gamemiddle.getWidth()/2.0f,gamemiddle.getHeight()/2.0f,Align.center);
        setOrigin(Align.center);
        gamemiddle.addListener(new ActorGestureListener(){
            private float touchDownScale;
            private float minScale;
            private float maxScale;
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                touchDownScale = view.getScaleX();
                minScale = (1080.0f * 0.6f) / view.getWidth();
                maxScale = (1080.0f * 1.2f) / view.getWidth();
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
                System.out.println(minScale +" ------- "+maxScale);
                float v = distance / initialDistance;
                float v1 = v *touchDownScale;
                if (maxScale<v1){
                    v1 = maxScale;
                }
                if (minScale>v1){
                    v1 = minScale;
                }
                view.setScale(v1);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

            }
        });
        view.setPosition(picGroup.getWidth()/2.0f,picGroup.getHeight()/2.f,Align.center);
        bottomPanelScrollPanel.setRectangle(0,0,getWidth(),220);
        Texture texture = modelUtils.getTexture();
//        Group group = new Group();
//        for (ModelGroup allModel : allModels) {
//            allModel.addListener(getItemListener(allModel));
//            group.addActor(allModel);
//            allModel.setPosition(allModel.getPosX(),allModel.getPosY(),Align.center);
//        }
//        addActor(group);
//        group.setPosition(200,200);
//        group.setScale(0.4f);
        clearbtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                clearModel();
            }
        });

        tipbtn.addListener(new OrdinaryButtonListener(){
            @SuppressWarnings("NewApi")
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ArrayList<ModelGroup> modelGroups = new ArrayList<>();
                modelGroups.addAll(finalModelGroup);
                modelGroups.sort(new Comparator<ModelGroup>() {
                    @Override
                    public int compare(ModelGroup group, ModelGroup t1) {
                        return group.getModelIndex() - t1.getModelIndex();
                    }
                });
                for (ModelGroup modelGroup : modelGroups) {

                        Vector2 targetPos = logicUtils.findTargetPos(modelGroup.getName());
                        finalModelGroup.remove(modelGroup);
                        logicUtils.addActor(modelGroup);
                        modelGroup.clearListeners();
                        modelGroup.setImageScale(1.0f);
                        modelGroup.setPosition(targetPos.x, targetPos.y, Align.center);

                        if (finalModelGroup.size()<=0){
                            addAction(Actions.delay(0.4f,Actions.run(()->{
                                gameSuccess(picGroup,view);
                            })));
                        }
                        break;
                    }

            }
        });

        topback.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ArrayList<ModelGroup> finalModelGroup = GameView.this.finalModelGroup;
                ArrayList<ModelGroup> modelGroups = new ArrayList<>(finalModelGroup);
                int size = modelGroups.size();
                if (size != 0) {
                    SaveHistoryBean saveHistoryBean = new SaveHistoryBean();
                    ArrayMap<Integer, SelectItemBean> entries = GameData.getInstance().readSelectItemBean();
                    SelectItemBean selectItemBean = entries.get(LevelConfig.splitnum);
                    saveHistoryBean.setSplitNum(selectItemBean.getPiecesnum());
                    saveHistoryBean.setLeftNum(size);
                    for (ModelGroup modelGroup : modelGroups) {
                        saveHistoryBean.setLeftPicIndex(modelGroup.getModelIndex());
                    }
                    FileUtils jigsawfile = FileUtils.getJigsawfile();
                    jigsawfile.saveHistoryPic(saveHistoryBean);
                }
                GameView.this.addAction(
                        Actions.sequence(
                                Actions.fadeOut(0.2f),
                                Actions.removeActor()));
            }
        });
        egebtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                showBorder = !showBorder;
                if (showBorder) {
                    egebtn.findActor("egebtnIcon").setColor(Color.valueOf("#44cb66"));
                }else {
                    egebtn.findActor("egebtnIcon").setColor(Color.valueOf("#868d9d"));
                }
                showBorder();
            }
        });

//        gameSuccess(picGroup,view);
        prebtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                isShowPre = !isShowPre;
                gamebottom.clearActions();
                if (isShowPre) {
                    prebtn.findActor("prebtnicon").setColor(Color.valueOf("#44cb66"));
                    gamebottom.addAction(Actions.fadeOut(0.2f));
                    gamebottom.setTouchable(Touchable.disabled);
                }else {
                    prebtn.findActor("prebtnicon").setColor(Color.valueOf("#868d9d"));
                    gamebottom.addAction(Actions.fadeIn(0.2f));
                    gamebottom.setTouchable(Touchable.enabled);
                }
                //绘制
                view.setShowPre(isShowPre);
            }
        });

        themebtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                BgTheme bgTheme = new BgTheme(new Runnable(){
                    @Override
                    public void run() {
                        changeBg();
                    }
                });
                addActor(bgTheme);

            }
        });
    }

    public void changeBg(){
        Image gamebg = rootView.findActor("gamebg");
        ((NinePatchDrawable)(gamebg.getDrawable())).setPatch(new NinePatch(
                    Asset.getAsset().getSprite("themebg/"+JigsawPreference.getInstance().getTheme())));
        gamebg.setSize(Constant.GAMEWIDTH, Constant.GAMEHIGHT);
        gamebg.setPosition(540.0f, 960.0f, Align.center);
    }

    private void gameSuccess(Group picGroup,TempView view) {
        PlayedLevelFile.getJigsawfile().saveMapV(LevelConfig.levelIndex.getLevelUUID(),true);
        Actor gametop1 = rootView.findActor("gametop");
        Actor gamebottom1 = rootView.findActor("gamebottom");
        gamebottom1.addAction(Actions.fadeOut(0.2f));
        gametop1.addAction(Actions.fadeOut(0.2f));
        view.addAction(Actions.scaleTo(0.56f,0.56f,0.2f));
//        view.addAction(Actions.scaleTo(0.52f,0.52f,0.2f));
        Image image = new Image(new NinePatch(
                Asset.getAsset().getTexture("common/success.png"),
                46,46,46,46));
        picGroup.addActor(image);
        image.getColor().a = 0.0f;
        image.addAction(Actions.sequence(Actions.delay(0.2F),Actions.fadeIn(0.3f)));
        image.setSize(1220,1220);
        image.setPosition(picGroup.getWidth()/2.0f,picGroup.getHeight()/2.0f - 80,Align.center);
        picGroup.setOrigin(Align.center);
        Image successBg = new Image(
                new NinePatch(
                        Asset.getAsset().getTexture("white.png"),
                        5,5,5,5));
        successBg.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        rootView.addActor(successBg);
        successBg.toBack();
        successBg.setColor(Color.valueOf("#0a8518"));
        successBg.getColor().a = 0;
        successBg.addAction(Actions.sequence(
                Actions.delay(1.0f),
                Actions.fadeIn(0.4f)));
        Image gamebg = rootView.findActor("gamebg");
        gamebg.toBack();
        successBg.setPosition(540,960,Align.center);
        picGroup.addAction(Actions.sequence(
                Actions.delay(1.0F,
                        Actions.scaleTo(0.798f,0.798f,0.3f)),
                Actions.run(()->{

                    Group group = CocosResource.loadFile("cocos/btn.json");
                    addActor(group);
                    group.getColor().a = 0;
                    group.addAction(Actions.fadeIn(0.3f));
                    group.setOrigin(Align.center);
                    group.setPosition(getWidth()/2.0f,200 - baseScreen.getOffsetY(),Align.bottom);
                    group.addListener(new OrdinaryButtonListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            super.clicked(event, x, y);
                            GameView.this.addAction(Actions.sequence(
                                    Actions.fadeOut(0.4f),
                                    Actions.removeActor()
                            ));
                        }
                    });
                })));
        ArrayMap<Integer, SelectItemBean> entries = GameData.getInstance().readSelectItemBean();
        SelectItemBean selectItemBean = entries.get(LevelConfig.splitnum);
        JigsawPreference.getInstance().saveCoinNum(selectItemBean.getRewardcoin());
    }

    private boolean successMove = false;
    private MyClickListener getItemListener(ModelGroup allModel) {
        return new MyClickListener(allModel){
            private Vector2 startV = new Vector2();
            private Vector2 convert = new Vector2();
            private Vector2 targetPos ;
            private boolean isDragged = false;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                successMove = false;
                isDragged = false;
                startV.set(x,y);
                bottomPanelScrollPanel.setValid(true);
                ModelGroup group = getGroup();
                targetPos = logicUtils.findTargetPos(group.getName());
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (successMove)return;
                System.out.println(event.getStageY());
                ModelGroup group = getGroup();

                if (group.isFreeStatus() ||isDragged || ((Math.abs(x - startV.x) < Math.abs(y - startV.y))&&(Math.abs(x - startV.x) * Math.abs(x - startV.x) +
                        Math.abs(y - startV.y) * Math.abs(y - startV.y)>200))) {
                    bottomPanelScrollPanel.cancel();
                    if (event.getStageY()>400) {
                        isDragged = true;
                        group = getGroup();
                        convert.set(x, y);
                        group.localToStageCoordinates(convert);
                        group.setImagePosition(x, y);
                        Vector2 vector21 = group.imageVector();
                        logicUtils.convertTarget(vector21);
                        group.resetPosition();
                        group.setX(vector21.x, Align.center);
                        group.setY(vector21.y, Align.center);
//                        group.clearListeners();
                        group.setImageScale(1.0f);
                        logicUtils.addActor(group);
                        group.setFree(true);
                    }else {
                        if (isDragged) {
                            group = getGroup();
                            group.setFree(false);
                            float stageX = event.getStageX();
                            Vector2 vector2 = new Vector2();
                            vector2.set(stageX,0);
                            bottomModelTable.stageToLocalCoordinates(vector2);
                            float scrollX = bottomPanelScrollPanel.getScrollX();
                            int v = (int) ((scrollX+vector2.x) / group.getWidth())-1;
                            if (v<0){
                                v = 0;
                            }
                            SnapshotArray<Actor> children = bottomModelTable.getChildren();
                            Array<Actor> array = new Array<>();
                            boolean success = false;
                            for (int i = 0; i < children.size; i++) {
                                array.add(children.get(i));
                                if (i == v){
                                    success = true;
                                    array.add(group);
                                }
                            }
                            if (!success){
                                array.add(group);
                            }
                            bottomModelTable.clear();
                            for (Actor actor : array) {
                                bottomModelTable.add(actor);
                            }
                            bottomModelTable.pack();
                            bottomModelTable.pack();
                            group.resetScale();
                        }
                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                ModelGroup group = getGroup();
                Vector2 vector2 = group.imageVector();
                logicUtils.convertTarget(vector2);
                if (logicUtils.check(targetPos, vector2)) {
                    successMove = true;
                    finalModelGroup.remove(group);
                    group.clearListeners();
                    group.addAction(Actions.moveToAligned(targetPos.x, targetPos.y, Align.center, 0.1f));

                    if (finalModelGroup.size()<=0){
                        addAction(Actions.delay(0.4f,Actions.run(()->{
                            gameSuccess(picGroup,view);
                        })));
                    }
                }
//                ModelGroup group = getGroup();
//                group.resetPosition();
//                group.resetScale();
            }
        };
    }

    public void showBorder() {
        bottomModelTable.clear();
        for (ModelGroup modelGroup : finalModelGroup) {
            if (modelGroup.isFreeStatus())continue;
            modelGroup.resetPosition();
            modelGroup.resetScale();
            if (showBorder) {
                ArrayList<ModelGroup> borderModels = modelUtils.getBorderModels();
                if (borderModels.contains(modelGroup)) {
                    bottomModelTable.add(modelGroup);
                }
            }else {
                bottomModelTable.add(modelGroup);
            }
        }
        bottomModelTable.pack();
        bottomModelTable.align(Align.bottomLeft);
    }

    public void clearModel(){
        for (ModelGroup modelGroup : finalModelGroup) {
            if (modelGroup.isFreeStatus()) {
                modelGroup.setFree(false);
            }
        }
        showBorder();
    }
}
