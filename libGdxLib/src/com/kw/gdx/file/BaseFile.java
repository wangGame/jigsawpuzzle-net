package com.kw.gdx.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * @Auther jian xian si qi
 * @Date 2023/12/11 17:03
 */
public class BaseFile<T,E> {
    private String fileName;
    private String preFix = "historyfile";
    private ArrayMap<T,E> arrayMap = new ArrayMap<>();
    public BaseFile(String fileName){
        this.fileName = fileName;
    }

    public BaseFile(String fileName,String preFix){
        this.fileName = fileName;
        this.preFix = preFix;
    }

    protected String getFileName() {
        return fileName;
    }

    public ArrayMap<T,E> getMapV(){
        return getMapV(false);
    }

    public ArrayMap<T,E> getMapV(boolean update){
        FileHandle file = getFile();
        if (file == null) return arrayMap;
        if (!update) {
            if (!file.exists()) return arrayMap;
        }
        String s = file.readString();
        String[] split = s.split(",");
        arrayMap.clear();
        for (String s1 : split) {
            arrayMap.put((T)s1,(E)s1);
        }
        return arrayMap;
    }

    public void saveMapV(T ke,boolean isUpdate){
        ArrayMap<T, E> mapV = arrayMap;
        if (isUpdate){
            mapV = getMapV();
        }else if (mapV == null){
            mapV = getMapV();
        }
        if (mapV.containsKey(ke)) {
            mapV.removeKey(ke);
        }
        mapV.insert(0,ke,(E)ke);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mapV.size; i++) {
            T keyAt = mapV.getKeyAt(i);
            builder.append(keyAt+",");
        }
        getFile().writeString(builder.toString(),false);
    }

    private FileHandle getFile(){
        return Gdx.files.local(preFix+"/"+fileName);
    }

}
