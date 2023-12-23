package kw.artpuzzle.down;

import com.badlogic.gdx.Gdx;
import com.kw.gdx.net.DownLoadListener;
import com.kw.gdx.zip.PackZip;

import kw.artpuzzle.data.CollectionBean;
import kw.artpuzzle.data.LevelBean;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/12 21:43
 */
public class BaseCollectionDown extends BaseDownLoadUtils{
    protected String outPath;
    protected CollectionBean levelBean;
    public BaseCollectionDown(CollectionBean levelBean, String outPath, Runnable successRunnable
            , Runnable reTryRunnable) {
        super(successRunnable,reTryRunnable);
        this.outPath = outPath;
        this.levelBean = levelBean;
        this.siteusing = NetContant.collectUrl
                +levelBean.getVersion()
                +"/"+levelBean.getLevelUUID()+".zip";
        String localStoragePath = Gdx.files.getLocalStoragePath();
        this.toPath = localStoragePath +outPath+levelBean.getLevelUUID()+".zip";
    }

    protected void downFile() {
        success = new DownLoadListener() {
            @Override
            public void downLoadCallBack() {
                super.downLoadCallBack();
                new Thread() {
                    @Override
                    public void run() {
                        String localStoragePath = Gdx.files.getLocalStoragePath();
                        Gdx.files.local(outPath+levelBean.getLevelUUID()+".zip").copyTo(
                                Gdx.files.local(append("/collections/temp/"+levelBean.getLevelUUID()+"/"+levelBean.getLevelUUID()+".zip")));
                        reDownLoad(PackZip.unpackZip(
                                append( localStoragePath+"collections/temp/"+levelBean.getLevelUUID()+"/"+levelBean.getLevelUUID()+".zip"),
                                append( localStoragePath+"collections/md5/")));
                        reDownLoad(PackZip.check(append(localStoragePath+"collections/md5/"+levelBean.getLevelUUID())));
                        Gdx.files.local(append("collections/md5/"+levelBean.getLevelUUID()+"/"))
                                .copyTo(Gdx.files.local("finalcollections/"+levelBean.getVersion()+"/"));
                        runnable.run();
                    }
                }.start();
            }
        };
        failed = new DownLoadListener() {
            @Override
            public void downLoadCallBack() {

            }

            @Override
            public void downLoadCallBack(int code) {
                super.downLoadCallBack(code);
            }
        };
        super.downFile();
    }
}
