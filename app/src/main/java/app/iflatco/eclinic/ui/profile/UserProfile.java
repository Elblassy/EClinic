package app.iflatco.eclinic.ui.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.UserProfileFragmentBinding;
import app.iflatco.eclinic.ui.appointment_patient.MyAppointment;
import app.iflatco.eclinic.ui.old_appointment.MyOldAppointment;
import app.iflatco.eclinic.utils.CustomSharedPref;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserProfile extends Fragment {

    private static final String TAG = "UserProfile";

    private UserProfileViewModel mViewModel;
    private UserProfileFragmentBinding binding;
    private CustomSharedPref pref;
    private static final int IMAGE_PICKER_SELECT = 11;
    private static final int PERMISSION = 7;

    public static UserProfile newInstance() {
        return new UserProfile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = UserProfileFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);

        pref = new CustomSharedPref(getContext());
        initView();

        binding.appointment.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyAppointment.class)));
        binding.oldVisits.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyOldAppointment.class)));

        binding.profileImage.setOnClickListener(v -> {
            checkPermission();
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, IMAGE_PICKER_SELECT);
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICKER_SELECT) {

            final Uri imageUri = data.getData();

            File file = new File(getRealPathFromURI(imageUri));

            MediaType mediaType = MediaType.parse(getActivity().getContentResolver().getType(imageUri));

            RequestBody requestFile =
                    RequestBody.create(file, mediaType);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            // pref.setProfImage(encodedImage);
            //mViewModel.updateImage(pref.getSessionValue("tokenId"), body);

            binding.profileImage.setImageURI(imageUri);


        } else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        binding.userName.setText(pref.getSessionValue("firstName") + " "
                + pref.getSessionValue("lastName"));
        binding.gender.setText(pref.getSessionValue("gender"));
        binding.height.setText(pref.getSessionValue("height") + " " +
                getResources().getString(R.string.height_title));
        binding.weight.setText(pref.getSessionValue("weight") + " " +
                getResources().getString(R.string.weight_title));
        binding.age.setText(String.valueOf(pref.getAge(pref.getSessionValue("birthDate"))) +
                " " + getResources().getString(R.string.years_title));

//        byte[] decodedString = android.util.Base64.decode(pref.getSessionValue("profImage"),
//                android.util.Base64.DEFAULT);
//
//        Glide.with(getActivity())
//                .asBitmap()
//                .override(640, 640)
//                .load(decodedString)
//                .into(binding.profileImage);

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI,
                null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        Log.d(TAG, "getRealPathFromURI: " + result);
        return result;
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE},
                            PERMISSION);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE},
                            PERMISSION);
                }
            }
        }
    }


}
