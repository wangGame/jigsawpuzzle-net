package com.tony.puzzle.desktopnet;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.net.DownLoad;
import com.kw.gdx.net.DownLoadListener;
import com.kw.gdx.resource.csvanddata.demo.CsvUtils;
import com.kw.gdx.utils.log.NLog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DeskDownload extends DownLoad {
    NetJavaImpl3 downloadnet;

    @Override
    public void downloadOneFile(String siteusing, String toPath, DownLoadListener onComplete, DownLoadListener onFail) {
        downloadnet = new NetJavaImpl3(4);
        startDownload(siteusing, toPath, onComplete,onFail);
    }

    @Override
    public void downloadOneFile(String siteusing, String fromPath,
                                String toPath, DownLoadListener onComplete,
                                DownLoadListener onFail) {
        downloadnet = new NetJavaImpl3(4);
        System.out.println(siteusing+fromPath);
        startDownload(siteusing + fromPath, toPath, onComplete,onFail);
    }
    //    http://gaoshanren.cdn-doodlemobile.com/Art_Puzzle/level_resource/verion1/level0.zip
    private void startDownload(String url, String path,
                               DownLoadListener runnable,
                               DownLoadListener onFail) {
        NLog.i("download uri: %s",url);
        System.out.println(url);
        final Net.HttpRequest down1 = new Net.HttpRequest();
        down1.setUrl(url);
        down1.setMethod("GET");
        down1.setTimeOut(90000);
        downloadnet.sendHttpRequest(down1, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                try {
                    if (httpResponse.getStatus().getStatusCode() == 200) {
                        int todownsize = Integer.parseInt(httpResponse.getHeader("Content-Length"));
                        InputStream is = null;
                        RandomAccessFile randomFile = null;
                        try {
                            is = httpResponse.getResultAsStream();
                            FileHandle file = Gdx.files.local(path);
                            if (!file.parent().exists()) {
                                file.parent().mkdirs();
                            }

                            if (file.exists()) {
                                NLog.e("delete file " + path);
                                file.delete();
                                NLog.e("still exist " + file.exists());
                            }
                            FileHandle parent = file.parent();
                            if (!parent.exists()) {
                                parent.mkdirs();
                            }

                            randomFile = new RandomAccessFile(file.parent()+ "/" + file.name(), "rwd");
                            randomFile.setLength(todownsize);
                            randomFile.seek(0);
                            byte[] buffer = new byte[1024];
                            int len = -1;
                            while ((len = is.read(buffer)) != -1) {

                                randomFile.write(buffer, 0, len);
                            }
                            randomFile.close();
                            is.close();
                            runnable.downLoadCallBack();

                        } catch (IOException e) {
                            e.printStackTrace();
//                            System.out.println("------------");
                            onFail.downLoadCallBack(-1);
                            System.out.println(url);
                            try {
                                randomFile.close();
                                is.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }else {
                        NLog.i(httpResponse.getStatus().getStatusCode());
                        onFail.downLoadCallBack(-1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onFail.downLoadCallBack(-1);
                }
            }
            @Override
            public void failed(Throwable t) {
                t.printStackTrace();
                onFail.downLoadCallBack();
            }

            @Override
            public void cancelled() {
                onFail.downLoadCallBack(-1);
            }
        });
    }
}
