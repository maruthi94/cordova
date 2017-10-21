/**
 */
package com.maruthi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.media.ThumbnailUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FilterInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileNotFoundException;
import android.util.Log;

import java.util.Date;

public class ThumbnailGenerator extends CordovaPlugin {

  private CallbackContext callbackContext;
  private Uri inputUri;
  private Uri outputUri;
  private int targetWidth;
  private int targetHeight;

  @Override
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    String imagePath = args.getString(0);
    JSONObject options = args.getJSONObject(1);
    this.targetWidth = options.getInt("targetWidth");
    this.targetHeight = options.getInt("targetHeight");

    this.inputUri = Uri.parse(imagePath);
    this.outputUri = Uri.fromFile(new File(getTempDirectoryPath() + "/" + System.currentTimeMillis()+ "-thumnail.jpg"));


    if(action.equals("bitmap")) {
        this.startBitmapping();
        
     return true;
    } 
    // else if(action.equals("getDate")) {
    //   // An example of returning data back to the web layer
    //   final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
    //   callbackContext.sendPluginResult(result);
    // }
    return false;
  }
   
   private void startBitmapping(){
   
    Bitmap thumbnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(this.inputUri.getPath()),100,100);
    File thumbnailFile = new File(this.getTempDirectoryPath() + "/" + System.currentTimeMillis()+ "-thumnail.jpg");

    try {
      FileOutputStream fos = new FileOutputStream(thumbnailFile);
      thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, fos);
      fos.flush();
      fos.close();
    } catch (FileNotFoundException e) {

      
    }
    
    this.callbackContext.success("file://" + thumbnailFile + "?" + System.currentTimeMillis());
    this.callbackContext = null;
   }


  private String getTempDirectoryPath() {
    File cache = null;

    // SD Card Mounted
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        cache = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Android/data/" + cordova.getActivity().getPackageName() + "/cache/");
    }
    // Use internal storage
    else {
        cache = cordova.getActivity().getCacheDir();
    }

    // Create the cache directory if it doesn't exist
    cache.mkdirs();
    return cache.getAbsolutePath();
}

}
