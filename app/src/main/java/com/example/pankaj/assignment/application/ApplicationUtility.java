package com.example.pankaj.assignment.application;

import android.content.Context;
import android.net.ConnectivityManager;

public class ApplicationUtility {

	public static boolean checkConnection(Context context) {
		try
		{
			ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())
				return true;
			else
				return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}
}
