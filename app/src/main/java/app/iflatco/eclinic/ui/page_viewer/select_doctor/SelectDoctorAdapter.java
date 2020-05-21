package app.iflatco.eclinic.ui.page_viewer.select_doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.models.DrModel;

public class SelectDoctorAdapter extends RecyclerView.Adapter<SelectDoctorAdapter.ViewHolder> {

    private static final String TAG = "SelectDoctorAdapter";
    List<DrModel> list = new ArrayList<>();
    private Context context;

    public SelectDoctorAdapter(Context context) {
        this.context = context;
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

        DrModel drModel = list.get(position);

        holder.name.setText(drModel.getName());
        holder.title.setText(drModel.getTitle());
        holder.drImage.setImageResource(drModel.getImage());
        holder.price.setText(drModel.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<DrModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, name, price;
        ImageView drImage;

        ViewHolder(View view) {
            super(view);
            this.setIsRecyclable(false);
            title = view.findViewById(R.id.speciality);
            name = view.findViewById(R.id.name);
            drImage = view.findViewById(R.id.dr_image);
            price = view.findViewById(R.id.price);
        }
    }


}