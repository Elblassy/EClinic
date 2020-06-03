package app.iflatco.eclinic.ui.page_viewer.dr_appointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.models.DrAvailableSlotsData;
import app.iflatco.eclinic.models.SlotesFilteratiton;
import app.iflatco.eclinic.utils.CustomClickListener;

public class DrAppointmentAdapter extends RecyclerView.Adapter<DrAppointmentAdapter.ViewHolder> {

    private static final String TAG = "SelectDoctorAdapter";
    private List<SlotesFilteratiton> list = new ArrayList<>();
    private Context context;
    private CustomClickListener clickListener;

    public DrAppointmentAdapter(Context context, CustomClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DrAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dr_appointment_item, parent,
                false);
        return new DrAppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrAppointmentAdapter.ViewHolder holder, int position) {

        SlotesFilteratiton drModel = list.get(position);

        holder.time.setText(drModel.getActualTime());

        holder.cardView.setOnClickListener(v -> {
            clickListener.onClick(position);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<SlotesFilteratiton> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView time;

        ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card);
            time = view.findViewById(R.id.time);

        }
    }


}