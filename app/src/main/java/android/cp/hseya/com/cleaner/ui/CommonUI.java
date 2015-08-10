package android.cp.hseya.com.cleaner.ui;


import android.app.Dialog;
import android.content.Context;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.activity.InspectionActivity;
import android.cp.hseya.com.cleaner.listener.ActionbarTitleClickListener;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CommonUI  { 


	private static CommonUI instance;
	private static Context context;
	private Dialog generalDialog; 
	private LinearLayout errorDialogLayout;

	public static synchronized CommonUI getInstance(Context context){

		CommonUI.context = context;

		if (instance == null) {

			instance = new CommonUI();
		}

		return instance;
	}


//	/*
//	 * genaral one button alert box
//	 */
//	public void genaralAlert(String title,String msg,final GeneralAlertButtonListener listener,final boolean isFinish) {
//		try {
//
//
//			generalDialog = new Dialog(context, R.style.Dialog_No_Border);
//			generalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			generalDialog.setCancelable(false);
//
//			LayoutInflater viewInflater = LayoutInflater.from(context);
//			View view    = viewInflater.inflate(R.layout.alertbox_general, null);
//			errorDialogLayout = (LinearLayout) view.findViewById(R.id.cadllMain);
//
//			errorDialogLayout.setBackgroundResource(R.drawable.btn_style_roundcorner);
//
//
//			Button generalAlertOkButton = (Button) view.findViewById(R.id.generalAlertOkButton);
//			generalAlertOkButton.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					if (generalDialog != null) {
//						if (generalDialog.isShowing()) {
//							generalDialog.dismiss();
//							generalDialog = null;
//						}
//					}
//					listener.OnGeneralAlertClick(v, isFinish);
//				}
//			});
//
//
//
//			TextView generalAlertTitleTextView = (TextView) view.findViewById(R.id.generalAlertTitleTextView);
//			generalAlertTitleTextView.setText(title);
//
//			TextView generalAlertMsgTextView = (TextView) view.findViewById(R.id.generalAlertMsgTextView);
//			generalAlertMsgTextView.setText(msg);
//
//			generalDialog.setContentView(view);
//			generalDialog.show();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


	public void setCommonActionBar(ActionBar customActionBar,Context context,boolean isHomeNavEnable,
			boolean isShowHeaderImg,String title,boolean isHomeUp,final ActionbarTitleClickListener titleClickListener) throws Exception {
		try {

			customActionBar.setDisplayHomeAsUpEnabled(false);        
			customActionBar.setDisplayShowCustomEnabled(true);
			customActionBar.setDisplayUseLogoEnabled(true);
			customActionBar.setDisplayShowTitleEnabled(true);
 
			if (isHomeNavEnable) {

				if (isHomeUp) {

					customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | 
							ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_HOME);


				}else{
					customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
							ActionBar.DISPLAY_SHOW_HOME);
				}
			}else{
				customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM );
			}


			customActionBar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.actionbar));



			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.actionbar_layout, null);

			TextView  actionBarTextView  = (TextView)  v.findViewById(R.id.actionBarTextView);

			actionBarTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					titleClickListener.onTitleClick();
				}
			});
			
			
			actionBarTextView.setText(title);






			customActionBar.setCustomView(v);

		} catch (Exception e) {
			throw e;
		}
	}

	
	
	
//	public void setCommonActionBarWithMainMenu(ActionBar customActionBar,Context context,boolean isHomeNavEnable,
//			boolean isShowHeaderImg,String title,boolean isHomeUp,final ActionbarTitleClickListener titleClickListener,
//			final MainMenuClickListener mainMenuClickListener) throws Exception {
//	
//		try {
//
//			customActionBar.setDisplayHomeAsUpEnabled(false);        
//			customActionBar.setDisplayShowCustomEnabled(true);
//			customActionBar.setDisplayUseLogoEnabled(true);
//			customActionBar.setDisplayShowTitleEnabled(true);
// 
//			if (isHomeNavEnable) {
//
//				if (isHomeUp) {
//
//					customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | 
//							ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_HOME);
//
//
//				}else{
//					customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM |
//							ActionBar.DISPLAY_SHOW_HOME);
//				}
//			}else{
//				customActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM );
//			}
//
//
//			customActionBar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.actionbar));
//
//
//
//			LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			View v = inflator.inflate(R.layout.actionbar_layout, null);
//
//			ImageView actionbarImageView = (ImageView) v.findViewById(R.id.actionbarImageView);
//			TextView  actionBarTextView  = (TextView)  v.findViewById(R.id.actionBarTextView);
//			
//			actionBarTextView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//					titleClickListener.onTitleClick();
//				}
//			});
//			
//			
//			actionBarTextView.setText(title); 
// 
//			ImageView mainActionImageView = (ImageView) v.findViewById(R.id.mainActionImageView);
//			mainActionImageView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//
//					mainMenuClickListener.onMainMenuClick();
//					
//				}
//			});
//
//			if (isShowHeaderImg) {
//
//				actionbarImageView.setVisibility(View.VISIBLE);
//
//			}else{
//				actionbarImageView.setVisibility(View.GONE);
//			}
//
//
//			customActionBar.setCustomView(v);
//
//		} catch (Exception e) {
//			throw e;
//		}
//	}
	
	
	


	


}
