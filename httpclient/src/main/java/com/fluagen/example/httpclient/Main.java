package com.fluagen.example.httpclient;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static Logger log = Logger.getLogger(Main.class.getName());

	public static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws Exception {

		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("tenant_id", "testlib");
		params1.put("sign_method", "md5");
		params1.put("timestamp", System.currentTimeMillis() + "");
		params1.put("client_id", "api.a000001.las.001");
		params1.put("user_type", "staff");
		params1.put("redirect_uri", "http://www.baidu.com");
		params1.put("response_type", "code");
		params1.put("view_format", "pc");

		String sign = MD5Util.getSign(params1);
		params1.put("sign", sign);

		log.info("=========================== 1. 请求授权码 ==============================================");
		String code = requestAuthCode(params1);
		log.info("=========================== 1. 请求授权码 end ==========================================");

		log.info("=========================== 2. 获取access token =======================================");
		String token = getAccessToken(code);
		log.info("=========================== 2. 获取access token end ===================================");

		log.info("=========================== 3. 请求服务 [notify] =======================================");
		reqService1(token);
		log.info("=========================== 3. 请求服务 [notify] end ===================================");

		log.info(
				"=========================== 4. 请求服务 [acquisitions/prdcatalog] =======================================");
		reqService2(token);
		log.info(
				"=========================== 4. 请求服务 [acquisitions/prdcatalog] end ===================================");

	}

	public static String requestAuthCode(Map<String, String> params) throws Exception {
		String url = "http://login.open.api.calis.edu.cn:8081/libraries/oauth2/login";

		URIBuilder builder = new URIBuilder(url);
		params.forEach((k, v) -> {
			builder.setParameter(k, v);
		});

		// 请求登录 1.1
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(builder.build());
		httpGet.setConfig(RequestConfig.custom().setRedirectsEnabled(false).build());
		CloseableHttpResponse res = client.execute(httpGet);
		Header h = res.getFirstHeader("Location");
		String loginURL = h.getValue();
		res.close();

		log.info("请求认证 [" + loginURL + "]");

		// 请求认证 1.2
		HttpPost httpPost = new HttpPost(loginURL);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "zhangsan"));
		nvps.add(new BasicNameValuePair("password", "zhangsan"));
		nvps.add(new BasicNameValuePair("secureCode", "123"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse res2 = client.execute(httpPost);
		Header h2 = res2.getFirstHeader("Location");
		String redirectURL = h2.getValue();
		res2.close();

		List<NameValuePair> args = URLEncodedUtils.parse(new URI(redirectURL), StandardCharsets.UTF_8);
		String code = "";
		for (NameValuePair nvp : args) {
			if (nvp.getName().equals("code")) {
				code = nvp.getValue();
				break;
			}
		}
		log.info("认证成功后的回调地址 [" + redirectURL + "]");
		log.info("获取授权码 [" + code + "]");
		return code;
	}

	public static String getAccessToken(String code) throws Exception {

		String url = "http://login.open.api.calis.edu.cn:8081/libraries/oauth2/get_access_token";

		Map<String, String> params = new HashMap<String, String>();
		params.put("code", code);
		params.put("tenant_id", "testlib");
		params.put("client_id", "api.a000001.las.001");
		params.put("grant_type", "authorization_code");
		params.put("sign_method", "md5");
		params.put("timestamp", System.currentTimeMillis() + "");
		String sign = MD5Util.getSign(params);
		params.put("sign", sign);

		String json = mapper.writeValueAsString(params);
		log.info("请求access token 服务参数 [" + json + "]");

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
		StringEntity se = new StringEntity(json);
		se.setContentEncoding("UTF-8");
		se.setContentType("application/json");
		httpPost.setEntity(se);
		CloseableHttpResponse res = client.execute(httpPost);
		HttpEntity entity = res.getEntity();
		String content = EntityUtils.toString(entity);
		int statusCode = res.getStatusLine().getStatusCode();

		log.info("响应状态 [" + statusCode + "]");
		log.info("响应结果 " + content);

		EntityUtils.consume(entity);
		res.close();

		Map<String, String> rst = mapper.readValue(content, new TypeReference<Map<String, String>>() {
		});
		String token = rst.get("access_token");

		return token;
	}

	public static void reqService1(String token) throws Exception {

		String url = "http://openapi.calis.edu.cn/notify";

		Map<String, String> params = new HashMap<String, String>();
		params.put("sign_method", "md5");
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("client_id", "api.a000001.las.001");
		params.put("tenant_id", "testlib");
		params.put("access_token", token);

		String sign = MD5Util.getSign(params);

		params.put("sign", sign);

		URIBuilder builder = new URIBuilder(url);
		params.forEach((k, v) -> {
			builder.setParameter(k, v);
		});

		log.info("请求 notify 服务 [" + builder.build().toString() + "]");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(builder.build());
		httpGet.addHeader("X-real-ip", "122.234.57.199");
		httpGet.addHeader("Content-Type", "application/json;charset=utf-8");
		httpGet.addHeader("Accept", "application/json; text/plain");
		CloseableHttpResponse res = client.execute(httpGet);
		HttpEntity entity = res.getEntity();

		int statusCode = res.getStatusLine().getStatusCode();
		String content = EntityUtils.toString(entity);

		log.info("响应状态 [" + statusCode + "]");
		log.info("响应结果 " + content);

		EntityUtils.consume(entity);
		res.close();
	}

	public static void reqService2(String token) throws Exception {
		String url = "http://openapi.calis.edu.cn/libraries/acquisitions/prdcatalog?" + "tenant_id=testlib&"
				+ "access_token=" + token;

		Map<String, String> params = new HashMap<String, String>();
		params.put("sign_method", "md5");
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("client_key", "api.a000001.las.001");
		params.put("prdcatalog_title", "test1");
		params.put("ver", "1.0");
		params.put("format", "json");

		Map<String, String> signParams = new HashMap<String, String>();
		signParams.putAll(params);
		signParams.put("tenant_id", "testlib");
		signParams.put("access_token", token);

		String sign = MD5Util.getSign(signParams);

		params.put("sign", sign);

		String json = mapper.writeValueAsString(params);
		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
		httpPost.addHeader("X-real-ip", "122.234.57.199");
		httpPost.addHeader("Accept", "application/json; text/plain");
		StringEntity se = new StringEntity(json);
		se.setContentEncoding("UTF-8");
		se.setContentType("application/json");
		httpPost.setEntity(se);
		CloseableHttpResponse res = client.execute(httpPost);
		HttpEntity entity = res.getEntity();

		int statusCode = res.getStatusLine().getStatusCode();
		String content = EntityUtils.toString(entity);

		log.info("响应状态 [" + statusCode + "]");
		log.info("响应结果 " + content);

		EntityUtils.consume(entity);
		res.close();
	}
}
