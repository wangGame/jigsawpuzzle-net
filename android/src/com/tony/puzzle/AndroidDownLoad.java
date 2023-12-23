package com.tony.puzzle;

import android.app.Activity;

import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.net.DownLoad;
import com.kw.gdx.net.DownLoadListener;
import com.kw.gdx.utils.log.NLog;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/8 19:21
 */
public class AndroidDownLoad extends DownLoad {
    private int totalSize;
    private int sofarSize;

    public AndroidDownLoad(Activity mainActivity) {
        FileDownloader.setup(mainActivity);
    }

    @Override
    public void downloadOneFile(String siteusing, String fromPath, String toPath,
                                DownLoadListener onComplete,
                                DownLoadListener onFail) {
        BaseDownloadTask baseDownloadTask1 = FileDownloader
                .getImpl()
                .create(siteusing + fromPath)
                .setPath(toPath);
        if (Constant.realseDebug){

        }
        System.out.println("download url :"+siteusing+fromPath);
        FileDownloadSampleListener fileDownloadSampleListener = new FileDownloadSampleListener() {
            @Override
            protected void completed(BaseDownloadTask task) {
                onComplete.downLoadCallBack();
            }
            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                e.printStackTrace();
                byte status = task.getStatus();
                onFail.downLoadCallBack(status);
            }
            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                sofarSize = soFarBytes;
                totalSize = totalBytes;
                float perent = (sofarSize * 1.0F)/totalSize;
                NLog.i((perent*100)+"");
                BaseGame.setText("down load perent:"+(perent*100)+"");
            }
        };
        baseDownloadTask1.setListener(fileDownloadSampleListener);
        //尝试次数
        baseDownloadTask1.setAutoRetryTimes(1);
        baseDownloadTask1.start();
    }

    public void downloadOneFile(String siteusing,
                                String toPath,
                                DownLoadListener onComplete,
                                DownLoadListener onFail) {
        BaseDownloadTask baseDownloadTask1 = FileDownloader
                .getImpl()
                .create(siteusing)
                .setPath(toPath);
        if (Constant.realseDebug){

        }
        System.out.println("download url :"+siteusing+  "     "+ toPath);
        FileDownloadSampleListener fileDownloadSampleListener = new FileDownloadSampleListener() {
            @Override
            protected void completed(BaseDownloadTask task) {
                onComplete.downLoadCallBack();
            }
            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                e.printStackTrace();
                byte status = task.getStatus();
                onFail.downLoadCallBack(status);
            }
            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                sofarSize = soFarBytes;
                totalSize = totalBytes;
                float perent = (sofarSize * 1.0F)/totalSize;
                NLog.i((perent*100)+"");
                BaseGame.setText("down load perent:"+(perent*100)+"");
            }
        };
        baseDownloadTask1.setListener(fileDownloadSampleListener);
        //尝试次数
        baseDownloadTask1.setAutoRetryTimes(1);
        baseDownloadTask1.start();
    }
}
