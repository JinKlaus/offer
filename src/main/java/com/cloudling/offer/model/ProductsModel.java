package com.cloudling.offer.model;

import com.cloudling.offer.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-07-13  10:59
 * @Version 1.0
 **/
public class ProductsModel extends Model{
    public ProductsModel(String table) {
        super(table);
    }

    public void add_products(HashMap<String, String> map){
        try {
            map.put("create_time",TimeUtil.getShortTimeStamp()+"");
            add(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, String>> list() {
        return select();
    }
}
