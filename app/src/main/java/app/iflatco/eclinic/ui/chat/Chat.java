package app.iflatco.eclinic.ui.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ActivityChatBinding;
import app.iflatco.eclinic.models.ChatModel;
import app.iflatco.eclinic.ui.prescription.PrescriptionActivity;
import app.iflatco.eclinic.utils.CustomSharedPref;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Chat extends AppCompatActivity {
    private static final String TAG = "Chat";

    ChatAdapter chatAdapter;
    ActivityChatBinding binding;
    CustomSharedPref pref;
    List<ChatModel> chatModelList = new ArrayList<>();
    AlertDialog.Builder builder;
    Dialog dialog;
    private Socket mSocket;
    private String userName, timer = "", roomId;
    private static final int IMAGE_PICKER_SELECT = 11;
    private ChatViewModel viewModel;
    List<ChatModel> tempChatModel = new ArrayList<>();

    {
        try {
            mSocket = IO.socket("https://clinice.herokuapp.com/");
            Log.d(TAG, "instance initializer: ");

        } catch (URISyntaxException e) {
            Log.d(TAG, "instance initializer: " + e.getMessage());
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    JSONObject data = (JSONObject) args[0];
                    String username = "";
                    String message = "";
                    String role = "";

                    try {
                        username = data.getString("user");
                        message = data.getString("message");
                        role = data.getString("role");
                    } catch (JSONException e) {
                        Log.d(TAG, "message: error" + e.getMessage());
                    }
                    chatModelList.add(new ChatModel(message, "", username, role));
                    chatAdapter.notifyDataSetChanged();
                    binding.layout.recyclerView.smoothScrollToPosition(chatModelList.size() - 1);

                }
            });
        }
    };

    private Emitter.Listener onNewImage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username = "";
                    String image = "";
                    String role = "";

                    try {
                        username = data.getString("user");
                        image = data.getString("image");
                        role = data.getString("role");
                    } catch (JSONException e) {
                        Log.d(TAG, "message: error" + e.getMessage());
                    }
                    for (int i = 0; i < tempChatModel.size(); i++) {
                        chatModelList.remove(tempChatModel.get(i));
                    }
                    chatModelList.add(new ChatModel("", image, username, role));
                    chatAdapter.notifyDataSetChanged();
                    binding.layout.recyclerView.smoothScrollToPosition(chatModelList.size() - 1);

                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        pref = new CustomSharedPref(this);
        userName = getIntent().getStringExtra("doctor_name");
        roomId = getIntent().getStringExtra("room_id");

        viewModel.uploadMutableLiveData.observe(this, fileName -> {
            mSocket.emit("uploadImage", fileName.getData());
        });

        initSocket();
        initView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICKER_SELECT) {
            final Uri imageUri = data.getData();

            File file = new File(getRealPathFromURI(imageUri));
            MediaType mediaType = MediaType.parse(getContentResolver().getType(imageUri));

            RequestBody requestFile =
                    RequestBody.create(saveBitmapToFile(file), mediaType);

            MultipartBody.Part image =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            MultipartBody.Part chatRoomId =
                    MultipartBody.Part.createFormData("chatRoomId", roomId);


            tempImage();

            viewModel.uploadImage(pref.getSessionValue("tokenId"), image, chatRoomId);


        }
    }


    /*
    To display progress bar while image uploaded and temp this image to delete it
    when sent
     */
    private void tempImage() {
        ChatModel chatModel = new ChatModel("", "default", pref.getSessionValue("firstName")
                + " " + pref.getSessionValue("lastName"), "user");

        tempChatModel.add(chatModel);
        chatModelList.add(chatModel);
        chatAdapter.notifyDataSetChanged();
        binding.layout.recyclerView.smoothScrollToPosition(chatModelList.size() - 1);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI,
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

    private void initSocket() {
        mSocket.on(Socket.EVENT_CONNECT, args -> {

            mSocket.emit("userJoined", pref.getUserId(), pref.getSessionValue("firstName") + " "
                    + pref.getSessionValue("lastName"), getIntent().getStringExtra("appEndTime"))
                    .emit("joinUserToRoom", roomId);

        }).on("isTyping", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(() -> {
                    if (args[0].equals("")) {
                        binding.toolbarTitle.setText(userName);
                    } else {
                        binding.toolbarTitle.setText(args[0].toString());

                    }
                });
            }
        }).on("message", onNewMessage).on("time", args -> {
            runOnUiThread(() -> {
                binding.timer.setVisibility(View.VISIBLE);
                long minutes = (Integer.parseInt(args[0].toString()) / 1000) / 60;
                int seconds = ((Integer.parseInt(args[0].toString()) / 1000) % 60);

                binding.timer.setText(String.valueOf(minutes + ":" + seconds));
                timer = binding.timer.getText().toString();
            });

        }).on("imageUploaded", onNewImage).on(Socket.EVENT_DISCONNECT, args -> {
            runOnUiThread(() -> {
                if (timer.equals("0:0")) {
                    Toast.makeText(this, "Your time is finished", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Your internet is disconnected", Toast.LENGTH_LONG).show();

                }
            });
        });


        mSocket.connect();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        dialog = builder.create();
        dialog.setCancelable(false);

        if (!dialog.isShowing()) {
            dialog.show();
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.toolbarTitle.setText(userName);
        binding.back.setOnClickListener(v -> onBackPressed());
        manager.setStackFromEnd(true);
        binding.layout.recyclerView.setLayoutManager(manager);
        binding.layout.recyclerView.setNestedScrollingEnabled(false);
        chatAdapter = new ChatAdapter(this, chatModelList);
        binding.layout.recyclerView.setAdapter(chatAdapter);

        binding.layout.send.setVisibility(View.GONE);

        binding.layout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 0) {
                    binding.layout.send.setVisibility(View.GONE);
                    mSocket.emit("stopTyping");

                } else {
                    binding.layout.send.setVisibility(View.VISIBLE);
                    mSocket.emit("typing");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.layout.send.setOnClickListener(v -> {
            if (mSocket.connected()) {
                Log.d(TAG, "onCreate: " + " connected");

                String message = binding.layout.editText.getText().toString();

                mSocket.emit("sendMessage", message);

                Log.d(TAG, "onCreate: " + message);

                binding.layout.editText.setText("");
            }

        });

        binding.layout.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.layout.editText.getRight() - binding.layout.editText
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, IMAGE_PICKER_SELECT);
                        return true;
                    }
                }
                return false;
            }
        });

        binding.fileLayout.setOnClickListener(v -> startActivity(new Intent(Chat.this,
                PrescriptionActivity.class).putExtra("room_id", roomId)));

        binding.video.setOnClickListener(v -> {
            URL serverURL;
            try {
                serverURL = new URL("https://meet.sst-egy.com");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid server URL!");
            }
            JitsiMeetConferenceOptions defaultOptions
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(roomId)
                    .build();

            JitsiMeetActivity.launch(this, options);
        });

    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        mSocket.disconnect();
        super.onDestroy();
    }
}