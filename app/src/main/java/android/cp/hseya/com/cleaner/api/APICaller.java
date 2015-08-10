package android.cp.hseya.com.cleaner.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.cp.hseya.com.cleaner.MainApplication;
import android.os.AsyncTask;




public class APICaller extends AsyncTask<APICall, String, APIResult> {

	private APICallback apiCallback = null;
	private MainApplication application = null;


	public APICaller(APICallback apiCallback,MainApplication application) {

		this.apiCallback = apiCallback;
		this.application = application;

	}

	@Override
	protected APIResult doInBackground(APICall... params) {


		APIResult  apiDataBean=new APIResult(); 
		HttpResponse httpResponse = null;
		InputStream is = null;
		String token="";
		APICall apiCall = null; 

		if (application!=null) {
//			token=application.getLoginToken();
		}

		try {

			apiCall = params[0];



			DefaultHttpClient httpClient = new DefaultHttpClient();



			if (apiCall.getMethod() == APICall.APICallMethod.POST) {


				HttpPost httpPost = new HttpPost(apiCall.getUrl());


				httpPost.setHeader("Content-Type", "application/json");
				httpPost.setHeader("Accept", "application/json");

				if (!token.equals("")) {
					httpPost.setHeader("authentication_token", token);
				}


				if (apiCall.getJsonSend()!=null) { 

					StringEntity se = new StringEntity(apiCall.getJsonSend().toString());

					httpPost.setEntity(se);
				}


				httpResponse = httpClient.execute(httpPost);



			}else if(apiCall.getMethod() == APICall.APICallMethod.GET){



				HttpGet httpGet = new HttpGet(apiCall.getUrl());


				httpGet.setHeader("Content-Type", "application/json");
				httpGet.setHeader("Accept", "application/json");

				if (!token.equals("")) {
					httpGet.setHeader("authentication_token", token);
				}

				httpResponse = httpClient.execute(httpGet);


			}else if(apiCall.getMethod() == APICall.APICallMethod.DELETE){
				//delete method http call


				HttpDelete httpDelete = new HttpDelete(apiCall.getUrl());

				httpDelete.setHeader("Content-Type", "application/json");
				httpDelete.setHeader("Accept", "application/json");

				if (!token.equals("")) {
					httpDelete.setHeader("authentication_token", token);
				}


				httpResponse = httpClient.execute(httpDelete);


			} else if(apiCall.getMethod() == APICall.APICallMethod.PUT){



				HttpPut httpPut = new HttpPut(apiCall.getUrl());

				httpPut.setHeader("Content-Type", "application/json");
				httpPut.setHeader("Accept", "application/json");

				if (!token.equals("")) {
					httpPut.setHeader("authentication_token", token);
				}

				if (apiCall.getJsonSend()!=null) { 

					StringEntity se = new StringEntity(apiCall.getJsonSend().toString());
					httpPut.setEntity(se);
				}

				httpResponse = httpClient.execute(httpPut);

			}

			HttpEntity httpEntity = httpResponse.getEntity();

			apiDataBean.setStatusCode(httpResponse.getStatusLine().getStatusCode());

			is = httpEntity.getContent();

			if (apiCall.isJsonArray()) {
				apiDataBean.setJsonArray(getResultJsonArray(is));
			}else{
				apiDataBean.setResultJson(getResultJsonObject(is));
			}


		}catch(Exception e){
			e.printStackTrace();
			if (apiCall.isJsonArray()) {
				apiDataBean.setJsonArray(null);
			}else{
				apiDataBean.setResultJson(null);
			}

		}

		return apiDataBean;



	}




	@Override
	protected void onPostExecute(APIResult result) {
		super.onPostExecute(result);

		if (result != null) {

			if (apiCallback != null) {

				apiCallback.onAPICallComplete(result);
			}
		}
	}


	/*
	 * get JSONArray from API response 
	 */
	private JSONArray getResultJsonArray(InputStream is){

		JSONArray jsonArray = null;
		String json = "";


		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			is.close();
			json = sb.toString();
			jsonArray=new JSONArray(json);

			return jsonArray;

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}


	}


	/*
	 * get json object form API response 
	 */
	private JSONObject getResultJsonObject(InputStream is ){

		JSONObject jObj = null;
		String json = "";

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			is.close();
			json = sb.toString();

			jObj=null;
			jObj = new JSONObject(json);		

			return jObj;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}


	}

}