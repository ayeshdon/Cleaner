package android.cp.hseya.com.cleaner.api;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIResult {
	
	private String responseBody;
	private int statusCode;
	private InputStream inputStream;
	private JSONArray jsonArray;
	private JSONObject resultJson;
	
	
	/*
	 * get API Responce body
	 */
	public String getResponseBody() {
		return responseBody;
	}

	/*
	 * set API responce body
	 */
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	/*
	 * get API responce status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/*
	 * set API responce status code
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/*
	 * get API responce InputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/*
	 * set API responce InputStream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/*
	 * get responce json array
	 */
	public JSONArray getJsonArray() {
		return jsonArray;
	}

	/*
	 * set responce json array
	 */
	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	/*
	 * get responce json object
	 */
	public JSONObject getResultJson() {
		return resultJson;
	}

	/*
	 * set responce json object
	 */
	public void setResultJson(JSONObject resultJson) {
		this.resultJson = resultJson;
	}
	
	

}
