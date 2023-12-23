package kw.artpuzzle.view;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import kw.artpuzzle.constant.GameConstant;

/**
 * @Auther jian xian si qi
 * @Date 2023/5/16 20:07
 *
 * 基本就是这样弄了，  具体细节用的时候在完善
 */
public class ModelGroup extends Group {
    private ShaderProgram program;
    private float startU = 0.f;
    private float startV = 0.f;
    private float u;
    private float v;
    private Image image;
    private float currentScale;
    private Texture texture;
    private boolean freeStatus;

    public ModelGroup(String maskName){
        setSize(266,266);
        image = new Image(Asset.getAsset().getTexture(maskName)){
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.setShader(program);
                Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1);
                texture.bind();
                int uniformLocation = program.getUniformLocation("u_texture3");
                program.setUniformi(uniformLocation,1);
                program.setUniformf("u",startU);
                program.setUniformf("u2",startU+u);
                program.setUniformf("v",startV);
                program.setUniformf("v2",startV+v);
                Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
                super.draw(batch, parentAlpha);
                batch.setShader(null);
            }

            @Override
            public void setSize(float width, float height) {
                super.setSize(width, height);
                if (texture==null)return;
                u = texture.getWidth() / getWidth() * (1800.0f/ texture.getWidth());
                v = texture.getHeight() / getHeight() * (1800.0f / texture.getHeight());
                texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
            }
        };
        addActor(image);
        program = new ShaderProgram(
                Gdx.files.internal("shader/masktest.vert"),
                Gdx.files.internal("shader/masktest.frag"));
    }

    public void setImageSize(float imageWidth,float imageHight){
        image.setSize(imageWidth,imageHight);
        image.setOrigin(Align.center);
        currentScale = getWidth() / (imageWidth - 130);
        image.setScale(currentScale);
        image.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
    }

    public void setStartU(float startU) {
        this.startU = startU;
    }

    public void setStartV(float startV) {
        this.startV = startV;
    }

    private float posX;
    public void setPosX(float x) {
        this.posX = x;
    }

    public float posY;
    public void setPosY(float y) {
        this.posY = y;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setImagePosition(float x, float y) {
        image.setPosition(x, y,Align.center);
        image.setScale(GameConstant.modelScale);
    }

    private Vector2 imageVector = new Vector2();
    public Vector2 imageVector(){
        imageVector.set(image.getX(Align.center),image.getY(Align.center));
        image.getParent().localToStageCoordinates(imageVector);
        return imageVector;
    }

    public void resetPosition() {
        image.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
    }

    public void resetScale(){
        image.setScale(currentScale);
    }

    public void setImageScale(float v) {
        image.setScale(1.0f);
    }

    public void setTexure(Texture texture) {
        this.texture = texture;
    }

    public void setUpdateSize(Runnable runnable){

    }

    private int modelIndex;
    public void setPicIndex(int modelIndex) {
        this.modelIndex = modelIndex;
        setName(""+modelIndex);
    }

    public int getModelIndex() {
        return modelIndex;
    }

    public void setFree(boolean b) {
        freeStatus = b;
    }

    public boolean isFreeStatus() {
        return freeStatus;
    }
}