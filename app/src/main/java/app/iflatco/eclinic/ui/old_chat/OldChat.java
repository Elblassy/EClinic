package app.iflatco.eclinic.ui.old_chat;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ActivityChatBinding;
import app.iflatco.eclinic.models.OldMessageData;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class OldChat extends AppCompatActivity {
    private static final String TAG = "OldChat";

    private ActivityChatBinding binding;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private OldChatAdapter chatAdapter;
    private List<OldMessageData> chatModelList = new ArrayList<>();
    private OldChatViewModel viewModel;
    private CustomSharedPref pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = new CustomSharedPref(this);

        viewModel = new ViewModelProvider(this).get(OldChatViewModel.class);

        viewModel.getOldMessages(pref.getSessionValue("tokenId"),
                getIntent().getStringExtra("room_id"));

        viewModel.oldMessagesMutableLiveData.observe(this, oldMessagesModel -> {
            chatModelList.clear();
            dialog.dismiss();
            chatModelList.addAll(oldMessagesModel.getData());
            chatAdapter.notifyDataSetChanged();
        });

        initView();

    }

    private void initView() {
        builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        dialog = builder.create();
        dialog.setCancelable(false);

        if (!dialog.isShowing()) {
            dialog.show();
        }
        binding.back.setOnClickListener(v -> onBackPressed());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        binding.layout.recyclerView.setLayoutManager(manager);
        binding.layout.recyclerView.setNestedScrollingEnabled(false);
        chatAdapter = new OldChatAdapter(this, chatModelList);
        binding.layout.recyclerView.setAdapter(chatAdapter);

        binding.toolbarTitle.setText(getIntent().getStringExtra("chat_header"));
        binding.layout.editText.setVisibility(View.GONE);
        binding.layout.send.setVisibility(View.GONE);
        binding.video.setVisibility(View.GONE);


    }

}