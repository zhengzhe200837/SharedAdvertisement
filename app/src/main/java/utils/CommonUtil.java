package utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.hardware.Camera;

import com.google.gson.Gson;

public class CommonUtil {
    public static boolean isCameraCanUse() {
            boolean canUse = true;
            Camera mCamera = null;
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                canUse = false;
            }
            if (canUse) {
                if (mCamera != null)
                    mCamera.release();
                mCamera = null;
            }
            return canUse;
    }

    public static int ORDER_TOTAL_SIZE = 0;

    public static void storeVideoInfo(Context context, VideoInfo vi) {
        ORDER_TOTAL_SIZE++;
        Gson viGson = new Gson();
        String viString = viGson.toJson(vi);
        SharedPreferences sp = context.getSharedPreferences("order", Context.MODE_APPEND);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(String.valueOf(ORDER_TOTAL_SIZE), viString);
        spe.commit();
    }

    public static VideoInfo getVideoInfo(Context context, int id) {
        SharedPreferences sp = context.getSharedPreferences("order", Context.MODE_APPEND);
        String videoInfo = sp.getString(String.valueOf(id), "null");
        Gson gson = new Gson();
        VideoInfo vi = gson.fromJson(videoInfo, VideoInfo.class);
        return vi;
    }
}
