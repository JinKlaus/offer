package com.klaus.offer.server;

import com.alibaba.fastjson.JSON;

import com.klaus.offer.exception.RouteErrorException;
import com.klaus.offer.util.CommonUtil;
import com.klaus.offer.util.LogUtil;
import org.eclipse.jetty.server.Request;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;


public class ControllerContext {
	
	public HttpServletRequest request;
	public HttpServletResponse response;
	public String METHOD;
	
	public String URL;
	public String uri;
	public String MOUDLE;
	public String CONTROLLER;
	public String ACTION;
	
	public HashMap<String, String> GET;
	public HashMap<String, Object> POST;
	String target;
	Request baseRequest;
	
	public ControllerContext(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws RouteErrorException {
		this.target = target;
		this.baseRequest = baseRequest;
		this.request = request;
		this.response = response;
		initRoute();
		initGet();
		initPost();
	}
	
	//鍒濆鍖栬矾鐢�
	void initRoute() throws RouteErrorException {
		
		uri = request.getRequestURI();
		uri = uri.equals("/")? "":uri;
		
		if(uri.indexOf("index.php?s=")>-1) uri.replace("index.php?s=", "");
		
		String[] pStrings= uri.split("/");
		
		if(pStrings.length<4) throw new RouteErrorException("route error:"+uri);
		MOUDLE = pStrings[1];
		CONTROLLER = pStrings[2];
		ACTION = pStrings[3];
		
		if(CommonUtil.isEmpty(MOUDLE) || CommonUtil.isEmpty(CONTROLLER) || CommonUtil.isEmpty(ACTION)) throw new RouteErrorException("route error");
		
		
	}
	
	//鑾峰彇get鍙傛暟
	void initGet() throws RouteErrorException{
		GET = new HashMap<>();
		String qString = request.getQueryString();
		if(qString!=null){
			String[] pStrings = qString.split("&");
			for(int i=0;i<pStrings.length;i++){
				if(pStrings[i].indexOf("=")==-1) throw new RouteErrorException("params error");
				String[] lStrings = pStrings[i].split("=");
				if(lStrings.length!=2)  GET.put(lStrings[0], "");
				else GET.put(lStrings[0], lStrings[1]);
			}
		}
		LogUtil.log("get:"+GET.toString());
	}
	
	//鑾峰彇post鍙傛暟
	void initPost(){
		POST = new HashMap<>();
		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String str = (String) names.nextElement();
			POST.put(str, request.getParameter(str));
		}
		//if(POST.size()==0) setJsonPost();

		LogUtil.log("post:"+POST.toString());

	}
	void setJsonPost(){
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) { 
			  sb.append(temp);
			}
			br.close();
			String params = sb.toString();
			if(!CommonUtil.isEmpty(params)){
				try {
					POST = JSON.parseObject(params,new HashMap<String,Object>().getClass());
					LogUtil.log("post:"+POST.toString());
					
				} catch (Exception e) {
					POST = new HashMap<>();
					String[] t1 = params.split("&");
					for(int i=0;i<t1.length;i++) {
						String t2[] = t1[i].split("=");
						if(t2.length==2)
						POST.put(t2[0],URLDecoder.decode(t2[1], "UTF-8"));
						else POST.put(t2[0],"");
					}
					
					
					LogUtil.log("post:"+POST.toString());
				}
			
			}else {
				POST = new HashMap<>();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	public String getSessionId(){
		return request.getSession().getId();
	}
	
	public String getIpAddr() {
		
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		return ip;
		}
	
	
	
	

}
