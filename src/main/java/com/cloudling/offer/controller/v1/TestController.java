package com.cloudling.offer.controller.v1;


import com.cloudling.offer.annotation.action;
import com.cloudling.offer.server.Controller;
import com.cloudling.offer.server.ControllerContext;

/**
 * @Description TODO
 * @Author 小云网络jxl
 * @Date 2018-08-04  13:07
 * @Version 1.0
 **/
public class TestController extends Controller {
    public TestController(ControllerContext context) {
        super(context);
    }

    @action
    public void test(){
        toHtml("admin_tpl/from_model");
    }
}
