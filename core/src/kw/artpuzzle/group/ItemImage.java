package kw.artpuzzle.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.cocosload.CocosResource;
import com.kw.gdx.shader.ShaderManager;
import com.kw.gdx.zip.PackZip;

import kw.artpuzzle.data.LevelBean;
import kw.artpuzzle.down.DownLevelUtils;
import kw.artpuzzle.listener.LevelListener;
import kw.artpuzzle.shader.CornersShader;
import kw.artpuzzle.shader.MskShader;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/8 18:30
 */
public class ItemImage extends Group {
    private LevelBean levelBean;
    private String levelPath;
    private String localStoragePath;
    private int status = 0; //
    private ShaderProgram program;

    public ItemImage(LevelBean levelBean) {
        setSize(465.00f, 465.00f);
        program = ShaderManager.getManager().getType(CornersShader.class);
        this.levelBean = levelBean;
        localStoragePath = Gdx.files.getLocalStoragePath();
        levelPath = "finallevel/" + levelBean.getVersion() + "/" + levelBean.getLevelUUID();
        if (Gdx.files.local(levelPath).exists()) {
            if (PackZip.check(localStoragePath + levelPath)) {
                status = 9;
            }
        }
    }

    public void downLoadImage(){
        DownLevelUtils downLevelUtils
                = new DownLevelUtils(levelBean, "level/out/", new Runnable() {
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
            Asset.getAsset().localAssetManagerLoad(levelPath+"/"+levelBean.getLevelUUID()+".png");
        }
        if (status == 1){
            if (Asset.getAsset().isLocalAssetManagerLoaded(
                    levelPath+"/"+levelBean.getLevelUUID()+".png")){
                status = 2;
                Texture localTexture = Asset.getAsset().getLocalTexture(levelPath + "/" + levelBean.getLevelUUID() + ".png");
                Image levelImage = new Image(localTexture){
                    @Override
                    public void draw(Batch batch, float parentAlpha) {
                        if (program!=null) {
                            batch.setShader(program);
                            if (!msk){
                                float i = 100.0f / localTexture.getWidth();
                                program.setUniformf("ra",i);
                            }
                            batch.flush();
                            super.draw(batch, parentAlpha);
                            batch.setShader(null);
                        }else {
                            super.draw(batch, parentAlpha);
                        }
                    }
                };
                addActor(levelImage);
                levelImage.setSize(getWidth(),getHeight());
            }
        }
    }

    private boolean msk = false;
    public void setMys() {
        msk = true;
        program = ShaderManager.getManager().getType(MskShader.class);
    }
}
