package com.cloudling.offer.model;

import com.cloudling.offer.bean.AttrBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttrModel extends Model {
    public AttrModel (){
        super("attr");
    }



    /**
     * @Description:根据spre_id获取配件属性
     * @param:
     * @return:
     * @auther: CodyLongo
     * @modified:
     */

    public List<AttrBean> getlistBySpareId(String spare_id){



        return null;
    }

    public List<AttrBean> getBeanByParentId(String spare_id){
        List<AttrBean> list =new ArrayList<>();
        ArrayList<HashMap<String, String>> map = getListByParentId(spare_id);
        for (int i=0;i<map.size(); i++){
            HashMap<String,String>  res=where("id ="+map.get(i).get("id")).find();
            AttrBean bean = new AttrBean(res);
            list.add(bean);}
        return list;
    }

    /*public ArrayList<HashMap<String, String>> getspareId(String sapre_id) {
        ArrayList<HashMap<String, String>> list = where("spare_id =" + sapre_id).select();
        return list;
    }*/

    public ArrayList<HashMap<String, String>> getListByParentId(String spare_id){
       ArrayList<HashMap<String, String>> list = new ArrayList<>();
        ArrayList<HashMap<String, String>> map =  where("spare_id =" + spare_id).select();
        for (int i=0;i<map.size(); i++){
           ;
          list= where(("parent_id ="+  map.get(i).get("id"))+"and parent_id is not"+0).select();
        }

        return list ;
    }

}