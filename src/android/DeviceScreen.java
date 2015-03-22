package io.securiteam.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.util.DisplayMetrics;

public class DeviceScreen extends CordovaPlugin
{
	public static final String ACTION_GET_WIDTH = "getWidth";
	public static final String ACTION_GET_HEIGHT = "getHeight";
	public static final String ACTION_GET_SCALE = "getScale";
	
	private CallbackContext context;
	private Activity activity;
	private Window window;
	private View decorView;
	
	//https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/core/res/res/values/dimens.xml
	private int getNavigationBarHeight() {
		Resources resources = activity.getApplicationContext().getResources();

		int id = resources.getIdentifier(
				resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape",
				"dimen", "android");
		if (id > 0) {
			return resources.getDimensionPixelSize(id);
		}
		return 0;
	}
	private int getStatusBarHeight() {
		Resources resources = activity.getApplicationContext().getResources();
		
		int id = resources.getIdentifier("status_bar_height", "dimen", "android");
		if (id > 0) {
			return resources.getDimensionPixelSize(id);
		}
		return 0;
	}
	private int getActionBarHeight() {
		Resources resources = activity.getApplicationContext().getResources();
		
		int id = resources.getIdentifier("action_bar_default_height", "dimen", "android");
		if (id > 0) {
			return resources.getDimensionPixelSize(id);
		}
		return 0;
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
		context = callbackContext;
		activity = cordova.getActivity();
		window = activity.getWindow();
		decorView = window.getDecorView();
		
		if (ACTION_GET_WIDTH.equals(action))
			return getWidth();
		else if (ACTION_GET_HEIGHT.equals(action))
			return getHeight();
		else if (ACTION_GET_SCALE.equals(action))
			return getScale();
		
		return false;
	}
	
	/**
	 * The width of the screen
	 */
	protected boolean getWidth()
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				try
				{
					int width;
					
					if (Build.VERSION.SDK_INT >= 17) {
						Point outSize = new Point();
						decorView.getDisplay().getRealSize(outSize);
						width = outSize.x;
					} else {
						width = activity.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
					}
					
			        PluginResult res = new PluginResult(PluginResult.Status.OK, width);
			        context.sendPluginResult(res);
				}
				catch (Exception e)
				{
					context.error(e.getMessage());
				}
			}
		});
		
		return true;
	}
	
	/**
	 * The height of the screen
	 */	
	protected boolean getHeight()
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				try
				{
					int height;
					
					if (Build.VERSION.SDK_INT >= 17) {
						Point outSize = new Point();
						decorView.getDisplay().getRealSize(outSize);
						height = outSize.y;
					} else {
						height = activity.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
						height += getNavigationBarHeight() + getStatusBarHeight() + getActionBarHeight();
					}
					
			        PluginResult res = new PluginResult(PluginResult.Status.OK, height);
			        context.sendPluginResult(res);
				}
				catch (Exception e)
				{
					context.error(e.getMessage());
				}
			}
		});
        
		return true;
	}
	
	/**
	 * The scale of the screen
	 */	
	protected boolean getScale()
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run() 
			{
				try
				{
					float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
			        PluginResult res = new PluginResult(PluginResult.Status.OK, density);
			        context.sendPluginResult(res);
				}
				catch (Exception e)
				{
					context.error(e.getMessage());
				}
			}
		});
        
		return true;
	}
	
}