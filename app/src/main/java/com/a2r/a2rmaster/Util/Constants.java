package com.a2r.a2rmaster.Util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Amaresh on 1/28/18.
 */

public class Constants {
    public static String BASEURL="https://a2r.in/admin/";
    public static String LOGIN="users/loginMobile.json";
    public static String ADDSHOPS="shops/add.json";
    public static String SHOPLIST="shops/index.json";
    public static String GSTLIST="taxs/index.json";
    public static String GSTEDIT="taxs/edit.json";
    public static String PRODUCTCATEGORY="ProductCategories/index.json";
    public static String PRODUCTTYPE="ProductTypes/index.json";
    public static String PRODUCTSCATEGORY="products/index.json";
    public static String EDITRESSTRAUNT="shops/edit.json";
    public static String USERLIST="users/index.json";
    public static String ADDUSER="users/add.json";
    public static String FCM_ID="fcmid";


    public static  String SHAREDPREFERENCE_KEY="a2rmaster";
    public static String USER_ID="user_id";
    public static String USER_NAME="user_name";
    public static String USER_MAIL="user_mail";
    public static String USER_PHONE="user_phone";



    // for avoid rotation of image
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

// uri to bitmap
public static  Bitmap getBitmap(ContentResolver cr, Uri url)
        throws FileNotFoundException, IOException {
    InputStream input = cr.openInputStream(url);
    Bitmap bitmap = BitmapFactory.decodeStream(input);
    input.close();
    return bitmap;
}

}
