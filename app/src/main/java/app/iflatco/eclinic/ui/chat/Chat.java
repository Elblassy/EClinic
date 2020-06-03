package app.iflatco.eclinic.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.databinding.ActivityChatBinding;
import app.iflatco.eclinic.models.ChatModel;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class Chat extends AppCompatActivity {
    private static final String TAG = "Chat";

    ChatAdapter chatAdapter;
    ActivityChatBinding binding;
    CustomSharedPref pref;
    List<ChatModel> chatModelList = new ArrayList<>();

    private Socket mSocket;

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
                    chatModelList.add(new ChatModel(message, username, role));
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

        pref = new CustomSharedPref(this);

        mSocket.on("message", onNewMessage);

        mSocket.emit("userJoined", 1, pref.getSessionValue("firstName") + " "
                + pref.getSessionValue("lastName"));

        mSocket.emit("joinUserToRoom", getIntent().getStringExtra("room_id"));
        mSocket.connect();

        binding.layout.send.setVisibility(View.GONE);

        binding.layout.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 0)
                    binding.layout.send.setVisibility(View.GONE);
                else
                    binding.layout.send.setVisibility(View.VISIBLE);
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

        initView();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.toolbarTitle.setText(getIntent().getStringExtra("doctor_name"));
        manager.setStackFromEnd(true);
        binding.layout.recyclerView.setLayoutManager(manager);
        binding.layout.recyclerView.setNestedScrollingEnabled(false);
        chatAdapter = new ChatAdapter(this, chatModelList);
        binding.layout.recyclerView.setAdapter(chatAdapter);
    }
}