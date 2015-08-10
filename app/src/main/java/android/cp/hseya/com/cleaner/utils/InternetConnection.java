package android.cp.hseya.com.cleaner.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection {



	private static InternetConnection instance;
	private Context context;

	private InternetConnection(Context context){
		this.context = context;
	}

	private InternetConnection(){}
	
	

	/**
	 * 
	 * get instance of InternetConnection
	 */
	public static InternetConnection getInstance(Context context){

		if (instance==null) {

			instance=new InternetConnection(context);
		}
		

		return instance;

	}

	/**
	 * 
	 * check Internet connection available in system
	 */
	public boolean isConnectingToInternet(){
		try {


			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			
			
			if (connectivity != null){
				
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				
				if (info != null) 
					
					for (int i = 0; i < info.length; i++) 
						
						if (info[i].getState() == NetworkInfo.State.CONNECTED){
							
							return true;
						}

			}
			
			return false;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
