package com.tony.puzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.io.File;

import kw.artpuzzle.un.Untext;

public class Unpack {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 1000;
        config.y = 0;
        config.height = (int) (640);
        config.width = (int) (360);
        new LwjglApplication(new Game() {
            @Override
            public void create() {
                String arr[] = {"jigsaws6"};
                for (int i = 0; i < arr.length; i++) {
                    String path = arr[i];
                    File file = new File( arr[i] + "/");
                    file.mkdir();

                    FileHandle packFile = Gdx.files.internal(path + ".atlas");
                    TextureAtlas.TextureAtlasData textureAtlasData = new TextureAtlas.TextureAtlasData(packFile, packFile.parent(), false);
                    Untext untext = new Untext();
                    untext.splitAtlas(textureAtlasData, "out/");
                }
            }
        } );
    }
}
