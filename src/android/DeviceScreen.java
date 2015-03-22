package io.securiteam.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
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
	
	private CallbackContext cbContext;
	private Context context;
	private Activity activity;
	private Window window;
	private View decorView;
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
		cbContext = callbackContext;
		activity = cordova.getActivity();
		context = activity.getApplicationContext(); 
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
	 * The width of the screen in immersive mode
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
					Point outSize = new Point();
					
					decorView.getDisplay().getRealSize(outSize);
					
			        PluginResult res = new PluginResult(PluginResult.Status.OK, outSize.x);
			        cbContext.sendPluginResult(res);
				}
				catch (Exception e)
				{
					cbContext.error(e.getMessage());
				}
			}
		});
		
		return true;
	}
	
	/**
	 * The height of the screen in immersive mode
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
					Point outSize = new Point();
					
					decorView.getDisplay().getRealSize(outSize);
					
			        PluginResult res = new PluginResult(PluginResult.Status.OK, outSize.y);
			        cbContext.sendPluginResult(res);
				}
				catch (Exception e)
				{
					cbContext.error(e.getMessage());
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
					final float density = context.getResources().getDisplayMetrics().density;
			        PluginResult res = new PluginResult(PluginResult.Status.OK, density);
			        cbContext.sendPluginResult(res);
				}
				catch (Exception e)
				{
					cbContext.error(e.getMessage());
				}
			}
		});
        
		return true;
	}
	
}