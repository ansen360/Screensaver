package com.ansen.screensaver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class PickVideo extends Activity {

    private final int REQUEST_CODE = 1;
    public static String TAG = "ansen";
    public static String SP_NAME = "sp_name";
    public static String SP_Path = "video_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "resultCode:" + resultCode);
        if (resultCode == RESULT_CANCELED)
            finish();

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            Log.d(TAG, "selected video:" + selectedVideo);

            String[] filePathColumn = {MediaStore.Video.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            Log.d(TAG, "video path:" + path);
            //save video path
            SharedPreferences sp = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            Editor editor = sp.edit();
            editor.putString(SP_Path, path);
            editor.commit();

        }
        finish();
    }
}
