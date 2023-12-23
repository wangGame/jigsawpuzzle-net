package com.kw.gdx.framebuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;

public class BufferBufferUtils extends Group {
   private Actor actor;

   public BufferBufferUtils(FrameBuffer frameBuffer,Actor group) {
      this.frameBuffer = frameBuffer;
      this.actor = group;
      this.addActor(group);
      actor.setPosition(0, 0);
      setTouchable(Touchable.disabled);
   }

   public BufferBufferUtils(Actor group,float xx,float yy) {
      this.frameBuffer = Asset.getAsset().buffer();
      this.actor = group;
      this.addActor(group);
      actor.setPosition(xx, yy);
      setTouchable(Touchable.disabled);
   }

   public void draw(Batch batch, float parentAlpha) {
      batch.flush();
      this.frameBuffer.begin();
      Gdx.gl.glClearColor(213.0f / 255.0f, 214.0f / 255.0f, 199.0f / 255.0f, 1.0F);
      Gdx.gl.glClear(16640);
      super.draw(batch, parentAlpha);
      batch.flush();
      this.frameBuffer.end();
   }

   public TextureRegion getBufferTexture1() {
      Texture colorBufferTexture = (Texture) this.frameBuffer.getColorBufferTexture();
      TextureRegion region = new TextureRegion(colorBufferTexture);
      region.setRegion(0, 0, (int) this.actor.getWidth(), (int) this.actor.getHeight());
      region.flip(false, true);
      return region;
   }


   public TextureRegion getBufferTexture(float globalScale) {
      Texture colorBufferTexture = (Texture) this.frameBuffer.getColorBufferTexture();
      TextureRegion region = new TextureRegion(colorBufferTexture);
      region.setRegion(2, 0, (int) this.actor.getWidth()-5, (int) this.actor.getHeight()-40);
      region.flip(false, true);
      return region;
   }

   private FrameBuffer frameBuffer;
}