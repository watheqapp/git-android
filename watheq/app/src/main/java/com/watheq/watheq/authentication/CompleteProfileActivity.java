package com.watheq.watheq.authentication;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseActivity;
import com.watheq.watheq.model.CompleteProfileBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.utils.FileUtils;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.utils.Utils;
import com.watheq.watheq.utils.Validations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class CompleteProfileActivity extends BaseActivity {
    public static final int IMAGE_PICKER_GALLERY_SELECT = 10545;
    @BindView(R.id.acp_username_edt)
    EditText username;
    @BindView(R.id.acp_email_edt)
    EditText email;
    @BindView(R.id.acp_confirm_btn)
    CircularProgressButton confirmBtn;
    @BindView(R.id.acp_profile_image)
    CircleImageView profileImage;
    private CompleteProfileViewModel completeProfileViewModel;
    private String imageBase64;


    private final Observer<LoginModelResponse> getCompleteProfileResponse = new Observer<LoginModelResponse>() {
        @Override
        public void onChanged(@Nullable LoginModelResponse completeProfileResponse) {
            if (completeProfileResponse.getThrowable() == null
                    && completeProfileResponse.getCode() == 200) {
                UserManager.getInstance().addUser(completeProfileResponse);
                MainActivity.start(CompleteProfileActivity.this);
                confirmBtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
            } else if (completeProfileResponse.getThrowable() == null) {
                showError(completeProfileResponse.getMessage());
            } else {
                showError(getString(R.string.all_no_internet));
            }
        }
    };

    private void showError(String error) {
        showSnackBarNotification(error);
        Validations.enableViews(email, username, profileImage);
        confirmBtn.revertAnimation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        completeProfileViewModel = ViewModelProviders.of(this).get(CompleteProfileViewModel.class);

    }

    @Override
    public void clean() {

    }

    @Override
    public int myView() {
        return R.layout.activity_complete_profile;
    }

    @OnClick(R.id.acp_confirm_btn)
    void onConfirmClicked() {
        if (!Utils.isNetworkAvailable(this)) {
            showSnackBar(getString(R.string.all_no_internet));
            return;
        }
        if (Validations.isEmpty(username.getText().toString())) {
            showSnackBar(getString(R.string.validation_error_required));
            return;
        }
        if (!Validations.isValidEmail(email.getText().toString())) {
            showSnackBar(getString(R.string.validation_error_email));
            return;
        }
        confirmBtn.startAnimation();
        CompleteProfileBody completeProfileBody = new CompleteProfileBody();
        completeProfileBody.setEmail(email.getText().toString());
        completeProfileBody.setName(username.getText().toString());
        completeProfileBody.setImage(imageBase64);
        completeProfileViewModel.completeProfile(UserManager.getInstance().getUserToken(),
                completeProfileBody).observe(this, getCompleteProfileResponse);
        Validations.disableViews(email, username, profileImage);
    }

    public static void start(Context context, View view, Activity activity) {
        Intent starter = new Intent(context, CompleteProfileActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity,
                        view,
                        ViewCompat.getTransitionName(view));
        context.startActivity(starter, options.toBundle());
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, CompleteProfileActivity.class);
        context.startActivity(starter);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    protected void openGallery() {
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (takeGalleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeGalleryIntent, IMAGE_PICKER_GALLERY_SELECT);
        }
    }


    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForLocation(PermissionRequest request) {
        showRationaleDialog(R.string.permission_external_storage_rationale, request);
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @OnClick(R.id.acp_profile_image)
    void onProfileImageClicked() {
        openGallery();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICKER_GALLERY_SELECT) {
            imageBase64 = onResult(data);
        }
    }

    private String onResult(Intent data) {
        Bitmap bmb = null;
        Uri uri = data.getData();
        Log.v("schemaRes", uri.getScheme());
        int dataSize = 0;
        InputStream fileInputStream;
        try {
            fileInputStream = getContentResolver().openInputStream(uri);
            if (fileInputStream != null) {
                dataSize = fileInputStream.available();
            }
            if (dataSize > 2000000) {
                showSnackBar(getString(R.string.file_size));
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bmb = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileImage.setImageURI(data.getData());
        return FileUtils.encodeImage(bmb);

    }
}
