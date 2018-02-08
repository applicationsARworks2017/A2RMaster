package com.a2r.a2rmaster.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.a2r.a2rmaster.R;
import com.a2r.a2rmaster.Util.CheckInternet;
import com.a2r.a2rmaster.Util.Constants;
import com.a2r.a2rmaster.Util.MultipartUtility;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mobileapplication on 2/2/18.
 */

public class RestruntEdit extends AppCompatActivity {

    ImageView iv_logo, iv_submit;
    Button bt_addlogo;
    EditText et_name, et_phone, et_add, et_gst;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    String imPath;
    File imageFile;
    Uri picUri = null;
    RelativeLayout addshop_rel;
    Bitmap bitmap;
    File imgfile;
    String res_title,res_mobile,res_address,res_gst,user_id,res_added_by,res_edit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_edit);

        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        iv_submit = (ImageView) findViewById(R.id.iv_submit);
        bt_addlogo = (Button) findViewById(R.id.bt_addlogo);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_add = (EditText) findViewById(R.id.et_address);
        et_gst = (EditText) findViewById(R.id.et_gst);
        addshop_rel=(RelativeLayout)findViewById(R.id.addshop_rel);
        user_id = RestruntEdit.this.getSharedPreferences(Constants.SHAREDPREFERENCE_KEY, 0).getString(Constants.USER_ID, null);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            res_title = extras.getString("title");
            res_mobile = extras.getString("mobile").trim();
            res_address = extras.getString("address");
            res_gst = extras.getString("gst");
            res_added_by = extras.getString("addedby");
            res_edit_id = extras.getString("editid");
        }

        et_name.setText(res_title);
        et_phone.setText(res_mobile);
        et_add.setText(res_address);
        et_gst.setText(res_gst);
        bt_addlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(RestruntEdit.this);
                dialog.setContentView(R.layout.chooseaction);
                LinearLayout camera = (LinearLayout) dialog.findViewById(R.id.camera);
                LinearLayout gallery = (LinearLayout) dialog.findViewById(R.id.gallery);
                dialog.show();
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        captureImage("gallery");

                    }
                });

            }
        });
        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if(et_name.getText().toString().trim().length()<=0){
            showSnackBar("Enter Name");
        }
        else if(et_phone.getText().toString().trim().length()<10){
            showSnackBar("Enter Valid Phone Number");
        }
        else if(et_add.getText().toString().trim().length()<0){
            showSnackBar("Enter Address");
        }else if(et_gst.getText().toString().trim().length()<0){
            showSnackBar("Enter Gst");
        }
        else{
            editRestraunt();
        }
    }

    private void editRestraunt() {
        String name=et_name.getText().toString().trim();
        String phone=et_phone.getText().toString().trim();
        String address=et_add.getText().toString().trim();
        String gst=et_gst.getText().toString().trim();
        if(CheckInternet.getNetworkConnectivityStatus(RestruntEdit.this)){
            //  sendDataroserver(name,phone,address,gst);
            if(imgfile!=null) {
                Bitmap bitmap = ((BitmapDrawable) iv_logo.getDrawable()).getBitmap();
                imgfile = persistImage(bitmap, "name1");
            }
            EditRestraunt res_edit = new EditRestraunt();
            res_edit.execute(name, phone, address, gst);
        }
        else {
            showSnackBar("No Internet");
        }
    }
    private  File persistImage(Bitmap bitmap, String name) {
        File filesDir = RestruntEdit.this.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return imageFile;
    }


    public class EditRestraunt extends AsyncTask<String,Void,Void> {
        private static final String TAG = "AddShopAsyntask";
        private ProgressDialog progressDialog = null;
        int server_status;
        String server_message;
        String link = Constants.BASEURL+Constants.EDITRESSTRAUNT;
        String charset = "UTF-8";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(RestruntEdit.this, "Adding", "Please wait...");
            }
            // onPreExecuteTask();
        }
        @Override
        protected Void doInBackground(String... params) {

                try {
                    MultipartUtility multipart = new MultipartUtility(link, charset);
                    multipart.addFormField("added_by",res_added_by);
                    multipart.addFormField("title",params[0]);
                    multipart.addFormField("address",params[2]);
                    multipart.addFormField("gst",params[3]);
                    multipart.addFormField("save_from","mobile");
                    multipart.addFormField("mobile_no",params[1]);
                    multipart.addFormField("id",res_edit_id);
                    if (imgfile != null) {
                        multipart.addFilePart("logo", imgfile);
                    }
                    List<String> response = multipart.finish();
                    String res = "";
                    for (String line : response) {
                        res = res + line + "\n";
                    }
                    Log.i(TAG, res);
                    if (res != null && res.length() > 0) {
                        JSONObject ress = new JSONObject(res.trim());
                        JSONObject res1 = ress.getJSONObject("res");
                        server_status = res1.optInt("status");
                        if (server_status == 1) {
                            server_message = "Edit Successfully";
                        } else {
                            server_message = "Unsuccessfull";
                        }

                    }
                } catch (Exception e) {
                    server_message = "Connectivity issues,please try again";
                    Log.e(TAG, e.toString());
                }
                return null;
            }
        @Override
        protected void onPostExecute(Void user) {
            super.onPostExecute(user);
            progressDialog.cancel();
            if (server_status == 1) {
                Toast.makeText(RestruntEdit.this,server_message,Toast.LENGTH_SHORT).show();
            }
            else{
                showSnackBar(server_message);
            }
        }
    }


    private void captureImage(String action) {
        if (action.contentEquals("camera")) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (cameraIntent.resolveActivity(RestruntEdit.this.getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(RestruntEdit.this,
                                "com.a2r.a2rmaster",
                                photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            } else {
                imPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                imageFile = new File(imPath);
                picUri = Uri.fromFile(imageFile); // convert path to Uri
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        } else {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, RESULT_LOAD_IMAGE);
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = RestruntEdit.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imPath = image.getAbsolutePath();
        imageFile = new File(imPath);
        picUri = Uri.fromFile(image); // convert path to Uri
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            iv_logo.setImageBitmap(BitmapFactory.decodeFile(picturePath));


        }
    }
    void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(addshop_rel, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#ae0a11"));

        snackbar.show();
    }

}

