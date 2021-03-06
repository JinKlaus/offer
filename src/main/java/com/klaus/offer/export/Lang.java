package com.klaus.offer.export;

import com.klaus.offer.util.ModelUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-09-14  17:52
 * @Version 1.0
 **/
public class Lang {

    static HashMap<String,String> langs = new HashMap<>();
    static {
        ArrayList<HashMap<String, String>> list = ModelUtil.getModel("english_refer").select();
        for (int i=0;i<list.size();i++){
            langs.put(list.get(i).get("name"),list.get(i).get("e_name"));
        }
    }

    public static String getEn(String key){
        if(langs.containsKey(key)){
            return langs.get(key);
        }else{
            return key;
        }

    }
}
