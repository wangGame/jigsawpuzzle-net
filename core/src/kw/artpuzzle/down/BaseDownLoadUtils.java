package kw.artpuzzle.down;

import com.kw.gdx.net.DownLoadListener;
import com.kw.gdx.utils.log.NLog;

import kw.artpuzzle.constant.GameStaticInstance;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/8 12:56
 */
public abstract class BaseDownLoadUtils {
    protected String siteusing;
    protected String toPath;
    protected StringBuilder stringBuilder ;
    protected Runnable runnable;
    protected Runnable retryRunnable;
    protected DownLoadListener success;
    protected DownLoadListener failed;

    public BaseDownLoadUtils(Runnable successRunnable, Runnable retryRunnable){
        this.runnable = successRunnable;
        this.retryRunnable = retryRunnable;
        this.stringBuilder = new StringBuilder();
    }

    protected String append(Object... arg){
        stringBuilder.setLength(0);
        for (Object s : arg) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    protected void downFile() {
        NLog.e("start down load ...");
        GameStaticInstance.downLoad.downloadOneFile(
                siteusing,
                toPath,
                success,
                failed);
    }

    protected void reDownLoad(boolean copyFlag) {
        if (!copyFlag){
            clearDir();
            downLoad();
        }
    }

    public void downLoad() {
        downFile();
    }

    protected void clearDir() {

    }
}
