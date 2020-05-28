package app.iflatco.eclinic.ui.page_viewer.select_doctor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.models.DrDataModel;
import app.iflatco.eclinic.utils.CustomClickListener;

public class SelectDoctorAdapter extends RecyclerView.Adapter<SelectDoctorAdapter.ViewHolder> {

    private static final String TAG = "SelectDoctorAdapter";
    List<DrDataModel> list = new ArrayList<>();
    private Context context;
    private CustomClickListener clickListener;

    public SelectDoctorAdapter(Context context, CustomClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_item, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DrDataModel drModel = list.get(position);

        holder.name.setText(drModel.getFirstName() + " " + drModel.getLastName());
        holder.title.setText(drModel.getCategory());
        holder.price.setText(drModel.getPrice() + " EGP");
        Uri uri = Uri.parse("https://clinice.herokuapp.com/images/" + drModel.getPicture());

        Glide.with(context)
                .asBitmap()
                .load(uri)
                .override(640, 640)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.drImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        holder.cardView.setOnClickListener(v -> {
            clickListener.onClick(position);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<DrDataModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title, name, price;
        ImageView drImage;

        ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card);
            title = view.findViewById(R.id.speciality);
            name = view.findViewById(R.id.name);
            drImage = view.findViewById(R.id.dr_image);
            price = view.findViewById(R.id.price);
        }
    }


}