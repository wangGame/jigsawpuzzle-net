package kw.artpuzzle.group.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.cocosload.CocosResource;
import com.kw.gdx.shader.ShaderManager;
import com.kw.gdx.zip.PackZip;

import kw.artpuzzle.data.CollectionBean;
import kw.artpuzzle.down.BaseCollectionDown;
import kw.artpuzzle.down.DownLevelUtils;
import kw.artpuzzle.shader.CornersShader;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/12 11:23
 */
public class BannerGroup extends Group {
    private CollectionBean collectionBean;
    private String localStoragePath;
    private String levelPath;
    private ShaderProgram program;
    private int status = 0; //
    private Group rootView;
    public BannerGroup(CollectionBean collectionBean){

        program = ShaderManager.getManager().getType(CornersShader.class);
        this.collectionBean = collectionBean;
        localStoragePath = Gdx.files.getLocalStoragePath();
        rootView = CocosResource.loadFile("cocos/bannergroup.json");
        addActor(rootView);
        setSize(rootView.getWidth(),rootView.getHeight());
        Label collectiontitle = rootView.findActor("collectiontitle");
        collectiontitle.setText(collectionBean.getDesc());
        levelPath = "finalcollections/" + collectionBean.getVersion() + "/" + collectionBean.getLevelUUID();
        if (Gdx.files.local(levelPath).exists()) {
            if (PackZip.check(localStoragePath + levelPath)) {
                status = 9;
            }
        }

        addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
    }


    public void downLoadImage(){
        BaseCollectionDown downLevelUtils
                = new BaseCollectionDown(collectionBean, "collections/out/", new Runnable() {
            @Override
            public void run() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (PackZip.check(localStoragePath + levelPath)) {
                            status = 9;
                        }
                    }
                });
            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        });
        downLevelUtils.downLoad();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (status == 0){
            status = 8;
            downLoadImage();
        }
        if (status == 9){
            status = 1;
            Asset.getAsset().localAssetManagerLoad(levelPath+"/"+collectionBean.getLevelUUID()+".png");
        }
        if (status == 1){
            if (Asset.getAsset().isLocalAssetManagerLoaded(
                    levelPath+"/"+collectionBean.getLevelUUID()+".png")){
                status = 2;
                Texture localTexture = Asset.getAsset().getLocalTexture(levelPath + "/" + collectionBean.getLevelUUID() + ".png");
                Image levelImage = new Image(localTexture){
//                    @Override
//                    public void draw(Batch batch, float parentAlpha) {
//                        if (program!=null) {
//                            batch.setShader(program);
//                            float i = 100.0f / localTexture.getWidth();
//                            program.setUniformf("ra",i);
//                            batch.flush();
//                            super.draw(batch, parentAlpha);
//                            batch.setShader(null);
//                        }else {
//                            super.draw(batch, parentAlpha);
//                        }
//                    }
                };


                Group bannerbg = rootView.findActor("bannerbg");
                bannerbg.addActor(levelImage);
                levelImage.setSize(getWidth(),getHeight());
                levelImage.setPosition(bannerbg.getWidth()/2.0f,bannerbg.getHeight()/2.0f, Align.center);
            }
        }
    }

}
