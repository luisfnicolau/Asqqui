package com.example.luis.asqqui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.transition.TransitionInflater;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.luis.asqqui.data.DatabaseContract;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.Arrays;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    EditText nameField;
    EditText ssnField;
    EditText emailField;
    EditText passwordField;
    EditText dateField;
    Activity activity;
    de.hdodenhof.circleimageview.CircleImageView userImageView;
    private final int GALLERY_REQUEST_CODE = 0;
    private final int CAMERA_REQUEST_CODE = 1;
    Bitmap userImageBitmap;
    int currentRotation = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        userImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add_photo_icon);
        setContentView(R.layout.activity_register);
        userImageView = (CircleImageView) findViewById(R.id.register_image);
        registerForContextMenu(userImageView);

        nameField = (EditText) findViewById(R.id.register_name);
        ssnField = (EditText) findViewById(R.id.register_ssn);
        emailField = (EditText) findViewById(R.id.register_email);
        passwordField = (EditText) findViewById(R.id.register_password);
        dateField = (EditText) findViewById(R.id.register_date);

        dateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegisterActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegisterActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        FloatingActionButton proceed = (FloatingActionButton) findViewById(R.id.register_action_button);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ContentResolver resolver = getContentResolver();

                final ContentValues values = new ContentValues();
                values.put(DatabaseContract.UserEntry.COLUMN_NAME, nameField.getText().toString());
                values.put(DatabaseContract.UserEntry.COLUMN_EMAIL, emailField.getText().toString());
                values.put(DatabaseContract.UserEntry.COLUMN_PASSWORD, passwordField.getText().toString());
                values.put(DatabaseContract.UserEntry.COLUMN_PHOTO, Utility.convertImageToBytes(Utility.compressImage(userImageBitmap, 80)));
                values.put(DatabaseContract.UserEntry.COLUMN_IDENTIFICATION_NUMBER, ssnField.getText().toString());
                values.put(DatabaseContract.UserEntry.COLUMN_BIRTH_DATE, dateField.getText().toString());

                BackendlessUser backendlessUser = new BackendlessUser();
                backendlessUser.setEmail(emailField.getText().toString());
                backendlessUser.setPassword(passwordField.getText().toString());
                backendlessUser.setProperty("name", nameField.getText().toString());

                Backendless.UserService.register(backendlessUser, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(activity, "You have been registered", Toast.LENGTH_SHORT)
                                .show();

                        Backendless.UserService.login(emailField.getText().toString(),
                                passwordField.getText().toString(),
                                new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                MainActivity.USER_LOGGED = true;
                                Toast.makeText(getApplicationContext(), "Login Succesfull", Toast.LENGTH_SHORT);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT);
                            }
                        }, true);
//                        resolver.insert(DatabaseContract.UserEntry.CONTENT_URI, values);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(activity, "Failed to register a user, please check internet connection", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        dateField.setText(date);
    }

    public void GetAPhoto(View v) {
        this.openContextMenu(v);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

                userImageView.setImageURI(selectedImage);
                userImageBitmap = ((BitmapDrawable) userImageView.getDrawable()).getBitmap();
                userImageBitmap = Utility.compressImage(userImageBitmap, 80);
                userImageView.setImageBitmap(userImageBitmap);
            RotateBitmap(-90);
            setProfilePicture(userImageBitmap);
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            userImageBitmap = (Bitmap) extras.get("data");
            setProfilePicture(Utility.compressImage(userImageBitmap, 80));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.choose_photo_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_get_from_gallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
                return true;
            case R.id.action_take_a_photo:
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void RotateImage(View v) {
        RotateBitmap(90);
        AnimateRotation();

    }

    private void setProfilePicture(Bitmap bitmap) {
        userImageView.setImageBitmap(bitmap);
    }

    private void RotateBitmap(int degrees) {
        Matrix matrix = new Matrix();

        matrix.postRotate(degrees);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(userImageBitmap, userImageBitmap.getWidth(), userImageBitmap.getHeight(), true);

        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        userImageBitmap = rotatedBitmap;
    }

    private void AnimateRotation() {
        RotateAnimation rotate = new RotateAnimation(currentRotation, currentRotation + 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentRotation = (currentRotation + 90) % 360;
        rotate.setDuration(300);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillAfter(true);
        rotate.setFillEnabled(true);

        userImageView.startAnimation(rotate);
    }
}

