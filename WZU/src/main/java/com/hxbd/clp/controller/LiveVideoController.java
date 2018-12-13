package com.hxbd.clp.controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.Consts;

import java.util.Date;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxbd.clp.domain.CreateSuccess;
import com.hxbd.clp.service.ChannelService;
import com.hxbd.clp.utils.common.CheckSumBuilder;
import com.hxbd.clp.utils.common.RandomUtil;
import com.hxbd.clp.utils.common.RasConstants;

@Controller
@SuppressWarnings({ "deprecation", "resource" })
public class LiveVideoController {

	@Autowired
	private ChannelService channelService;
	/**
	 * 创建一个直播频道
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/create")
	public @ResponseBody String liveCreate() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//HttpClient client = new HttpClient();
        String url = "https://vcloud.163.com/app/channel/create";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"name\":\"恒信博大教育5\", \"type\":0}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        CreateSuccess createSuccess = new CreateSuccess();
        if(code == 200){
        	JSONObject ret = (JSONObject) obj.get("ret");
        	String httpPullUrl = ret.getString("httpPullUrl");
        	String hlsPullUrl = ret.getString("hlsPullUrl");
        	String rtmpPullUrl = ret.getString("rtmpPullUrl");
        	String name = ret.getString("name");
        	String pushUrl = ret.getString("pushUrl");
        	int ctime = ret.getInt("ctime");
        	String cid = ret.getString("cid");
        	createSuccess.setCode(code);
        	createSuccess.setHttpPullUrl(httpPullUrl);
        	createSuccess.setHlsPullUrl(hlsPullUrl);
        	createSuccess.setRtmpPullUrl(rtmpPullUrl);
        	createSuccess.setName(name);
        	createSuccess.setPushurl(pushUrl);
        	createSuccess.setCtime(ctime);
        	createSuccess.setCid(cid);
        	channelService.saveChannel(createSuccess);
        	//System.err.println("Code :" + createSuccess.getCode());
        	//System.err.println("HttpPullUrl :" + createSuccess.getHttpPullUrl());
        	//System.err.println("HlsPullUrl :" + createSuccess.getHlsPullUrl());
        	//System.err.println("RtmpPullUrl :" + createSuccess.getRtmpPullUrl());
        	//System.err.println("Name :" + createSuccess.getName());
        	//System.err.println("Pushurl :" + createSuccess.getPushurl());
        	//System.err.println("Ctime :" + createSuccess.getCtime());
        	//System.err.println("Cid :" + createSuccess.getCid());
        	
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	createSuccess.setCode(badCode);
        	createSuccess.setMsg(msg);
        	//System.err.println("Code :" + createSuccess.getCode());
        	//System.err.println("Msg :" + createSuccess.getMsg());
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	/**
	 * 修改频道信息   参数{频道name}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/update")
	public @ResponseBody String channelUpdate() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channel/update";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"name\":\"恒信博大教育6\",\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\", \"type\":0}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	String msg = obj.getString("msg");
        	String ret = obj.getString("ret");
        	//System.err.println("Code :" + code);
        	//System.err.println("msg :" + msg);
        	//System.err.println("ret :" + ret);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	/**
	 * 删除一个频道   参数{频道CID}
	 * @return
	 * @throws Exception
	 */
	public @ResponseBody String channelDelete() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channel/delete";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	String ret = obj.getString("ret");
        	//System.err.println("Code :" + code);
        	//System.err.println("ret :" + ret);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	/**
	 * 获取一个直播频道的具体信息   参数{频道CID}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channelstats")
	public @ResponseBody String channelstats() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channelstats";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	String ret = obj.getString("ret");
        	//System.err.println("Code :" + code);
        	//System.err.println("ret :" + ret);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	/**
	 * 获取用户直播频道列表   有四个可选参数 records、pnum、ofield、sort
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channellist")
	public @ResponseBody String channellist() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channellist";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
//        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
//        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	String ret = obj.getString("ret");
        	//System.err.println("Code :" + code);
        	//System.err.println("ret :" + ret);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 重新获取推流地址    参数{频道CID}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/address")
	public @ResponseBody String channelAddress() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/address";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	String ret = obj.getString("ret");
        	//System.err.println("Code :" + code);
        	//System.err.println("ret :" + ret);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 设置频道为录制状态 	参数  cid needRecord format duration filename
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/setAlwaysRecord")
	public @ResponseBody String channelSetAlwaysRecord() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channel/setAlwaysRecord";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\",\"needRecord\": 1, \"format\":1, \"duration\":20, \"filename\":\"record\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	String ret = obj.getString("ret");
        	//System.err.println("Code :" + code);
        	//System.err.println("ret :" + ret);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	/**
	 * 禁用用户正在直播的频道		参数{频道CID}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/pause")
	public @ResponseBody String channelPause() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channel/pause";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	
	/**
	 * 批量禁用用户正在直播的频道		参数{频道CID[]}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/list/pause")
	public @ResponseBody String channelListPause() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channellist/pause";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 恢复用户被禁用的频道		参数{频道CID}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/resume")
	public @ResponseBody String channelResume() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channel/resume";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 批量恢复用户被禁用的频道		参数{频道CID[]}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/list/resume")
	public @ResponseBody String channelListResume() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channellist/resume";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 获取某频道录制视频文件列表，按生成时间由近至远提供分页    参数{频道CID} records  pnum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/videolist")
	public @ResponseBody String videolist() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/videolist";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	/**
	 * 通过开始和结束的时间点，获取某频道录制视频文件列表。(时间跨度不能超过1周)  	cid beginTime   endTime		sort(排序参数可选)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vodvideolist")
	public @ResponseBody String vodvideolist() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/vodvideolist";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\":\"f0c34c06145e49f292c66254ee37f3d2\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	
	/**
	 * 设置视频录制回调地址		参数{recordClk}录制文件生成回调地址(http开头)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/record/setcallback")
	public @ResponseBody String recordSetcallback() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/record/setcallback";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"recordClk\":\"http://xxxxxxxxx\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 设置回调的加签秘钥	
	 * 用该秘钥对回调内容生成MD5签名，用于用户接口的校验。可以不设置，默认为“vcloud”。该秘钥对用户所有设置的回调地址生效。
	 * pamas 	signKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/callback/setSignKey")
	public @ResponseBody String callbackSetSignKey() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/callback/setSignKey";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"signKey\":\"xxxxxxxxx\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 录制文件合并		pamas {outputName}  {vidList}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/video/merge")
	public @ResponseBody String videoMerge() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/video/merge";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"outputName\":\"xxxxxxxxx\", \"vidList\": [vidxxxxxxxxx0, vidxxxxxxxxx1, vidxxxxxxxxx2]}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	

	/**
	 * 录制重置			pamas{cid}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/resetRecord")
	public @ResponseBody String channelResetRecord() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channel/resetRecord";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\": \"cidxxxxxxxxx\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	/**
	 * 该接口用于：获取直播实时转码相关地址   	pamas{cid}
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/transcodeAddress")
	public @ResponseBody String transcodeAddress() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/transcodeAddress";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\": \"cidxxxxxxxxx\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	
	/**
	 * 视频录制回调地址查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/record/callbackQuery")
	public @ResponseBody String recordCallbackQuery() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/record/callbackQuery";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
//        StringEntity params = new StringEntity("{\"cid\": \"cidxxxxxxxxx\"}",Consts.UTF_8);
//        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
	
	
	
	/**
	 * 设置录制信息
	 * param cid format duration filename
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/channel/setupRecordInfo")
	public @ResponseBody String channelSetupRecordInfo() throws Exception{
		try{
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://vcloud.163.com/app/channel/setupRecordInfo";
        HttpPost httpPost = new HttpPost(url);
        String appKey = RasConstants.APPKEY;	//开发者平台分配的AppKey
        String appSecret = RasConstants.APPSECRET;						//开发者平台分配的
        String nonce =  RandomUtil.getRandom();			//随机数（随机数，最大长度128个字符）
        String curTime = String.valueOf((new Date()).getTime() / 1000L);	//当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        // 设置请求的参数 		name:频道名称	type:频道类型 ( 0 : rtmp, 1 : hls, 2 : http)
        StringEntity params = new StringEntity("{\"cid\": \"cidxxxxxxxxx\",  \"format\":1, \"duration\":20, \"filename\":\"record\"}",Consts.UTF_8);
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        // 打印执行结果
//        System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        //System.err.println("---");
//        JSONObject.toJSONString();
        String result = new String(EntityUtils.toString(response.getEntity()));
        //System.err.println(result);
        //System.err.println("-----");
        JSONObject obj = new JSONObject(result);
        int code = obj.getInt("code");
        //System.err.println("code：" + code);
        if(code == 200){
        	//System.err.println("Code :" + code);
        }else{
        	int badCode = obj.getInt("code");
        	String msg = obj.getString("msg");
        	//System.err.println("Code :" + badCode);
        	//System.err.println("Msg :" + msg);
        }
        httpPost.releaseConnection();
		return "{\"message\":true}";
	}catch (Exception e) {
		e.printStackTrace();
    }
		return "{\"message\":false}";

}
}
