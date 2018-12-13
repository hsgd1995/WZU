package com.hxbd.clp.utils.common;

import java.util.HashMap;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendMsg {
	public static HashMap<String,String> getMessageStatus(String phone) throws Exception{
		HashMap<String,String> map = new HashMap<String,String>();
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		//在头文件中设置转码
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
		//6位数验证码
		String code = RandomUtil.getRandom();
		//Uid:sms网站用户名&& Key:接口密钥&& smsMob：目的手机号码&& smsText:短信内容
		NameValuePair[] data ={ new NameValuePair("Uid", "asdaaaa"),//new NameValuePair("Uid", "哈麻皮666"),new NameValuePair("Key", "eb83b7e6e0579a9ddc7b")
								new NameValuePair("Key", "0f9f85cb86412ddc7081"),
								new NameValuePair("smsMob",phone),
								new NameValuePair("smsText","验证码为:"+code+""+"有效时间为5分钟。【恒信博大教育投资有限公司】")};
		map.put("code", code);
		post.setRequestBody(data);
		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		/*for(Header h : headers) {
			System.out.println(h.toString());
		}*/
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
		//打印返回消息状态
		//System.out.println(result);
		map.put("result", result);
		
		post.releaseConnection();
		return map;
		
	}
}
