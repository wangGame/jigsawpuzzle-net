package kw.artpuzzle.group.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.shader.ShaderManager;
import com.kw.gdx.zip.PackZip;

import kw.artpuzzle.data.CateBean;
import kw.artpuzzle.down.DownLevelUtils;
import kw.artpuzzle.listener.SignListener;
import kw.artpuzzle.shader.CornersShader;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/5 11:39
 */
public class CateItemGroup extends Group {
    private CateBean cateBean;
    private String localStoragePath;
    private String levelPath;
    private SignListener listener;

    public CateItemGroup(CateBean cateBean){
        this.cateBean = cateBean;
        localStoragePath = Gdx.files.getLocalStoragePath();
        levelPath = "category/catepic" + "/" + cateBean.getDesc()+"/";
        setSize(Constant.GAMEWIDTH-20,116);
        {
            Texture texture = Asset.getAsset()
                    .getTexture(levelPath + "1.png");
            Image levelImage = new Image(texture){
                ShaderProgram program = ShaderManager.getManager().getType(CornersShader .class);
                @Override
                public void draw(Batch batch, float parentAlpha) {

                    if (program!=null) {
                        batch.setShader(program);
                        float i = 100.0f / texture.getWidth();
                        program.setUniformf("ra",i);
                        batch.flush();
                        super.draw(batch, parentAlpha);
                        batch.setShader(null);
                    }else {
                        super.draw(batch, parentAlpha);
                    }
                }
            };
            addActor(levelImage);
            levelImage.setSize(getHeight(), getHeight());
            levelImage.setPosition(56, getHeight() / 2.0f, Align.left);
        }
        {
            Image icon = new Image(Asset.getAsset().getTexture("common/categorybg.png"));
//        addActor(icon);
            icon.setPosition(60, getHeight() / 2.0f, Align.left);
            Label cateLabel = new Label("", new Label.LabelStyle() {{
                font = Asset.getAsset().loadBitFont("cocos/font/inter-semi-32.fnt");
            }});
            cateLabel.setColor(Color.BLACK);
            cateLabel.setText(cateBean.getDesc());
            addActor(cateLabel);
            cateLabel.setOrigin(Align.left);
            cateLabel.setX(icon.getX(Align.right) + 20);
            cateLabel.setY(icon.getY(Align.center),Align.center);
            cateLabel.setScale(1.5f);
        }

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                listener.sign(cateBean);
            }
        });

//        if (Gdx.files.local(levelPath).exists()){
//            if (PackZip.check(localStoragePath + levelPath)) {
//                Image levelImage = new Image(Asset.getAsset().getLocalTexture(levelPath+"/"+levelBean.getLevelUUID()+".png"));
//                addActor(levelImage);
//                levelImage.setSize(getWidth(),getHeight());
//            }else {
//                FileHandle local = Gdx.files.local(levelPath);
//                local.deleteDirectory();
//                downLoadImage();
//            }
//        }
//        else {
//            FileHandle local = Gdx.files.local(levelPath);
//            local.deleteDirectory();
//            downLoadImage();
//        }
    }

    public void setSignListener(SignListener listener) {
        this.listener = listener;
    }

//
//    public void downLoadImage(){
//        DownLevelUtils downLevelUtils
//                = new DownLevelUtils(levelBean, "level/out/", new Runnable() {
//            @Override
//            public void run() {
//                Gdx.app.postRunnable(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (PackZip.check(localStoragePath + levelPath)) {
//                            Image levelImage = new Image(Asset.getAsset().getLocalTexture(levelPath+"/"+levelBean.getLevelUUID()+".png"));
//                            addActor(levelImage);
//                            levelImage.setSize(getWidth(),getHeight());
//                        }
//                    }
//                });
//            }
//        }, new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        downLevelUtils.downLoad();
//    }
}
