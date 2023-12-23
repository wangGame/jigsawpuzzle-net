package kw.artpuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.net.DownLoad;
import com.kw.gdx.resource.annotation.GameInfo;
import com.kw.gdx.utils.log.NLog;

import kw.artpuzzle.constant.GameStaticInstance;
import kw.artpuzzle.listener.GameListener;
import kw.artpuzzle.screen.LoadingScreen;

@GameInfo(width = 1080,height = 1920,batch = Constant.COUPOLYGONBATCH)
public class JigSawPuzzle extends BaseGame {
    public JigSawPuzzle(DownLoad deskDownload, GameListener listener){
        Gdx.isJiami = true;
        GameStaticInstance.gameListener = listener;
        listener.changeLocalPath();
        Asset.enterGameClear();
        NLog.isLog = false;
        Constant.viewColor.set(Color.WHITE);
        GameStaticInstance.downLoad = deskDownload;
    }

    @Override
    protected void loadingView() {
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        Asset.getAsset().assetManagerUpdate();
        super.render();
    }
}
