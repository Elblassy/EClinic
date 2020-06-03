package app.iflatco.eclinic.ui.appointment_patient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.models.PatientAppointmentData;
import app.iflatco.eclinic.utils.CustomClickListener;

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> {

    private static final String TAG = "SelectDoctorAdapter";
    List<PatientAppointmentData> list = new ArrayList<>();
    private Context context;
    private CustomClickListener clickListener;
    private String today;

    public MyAppointmentAdapter(Context context, CustomClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today = df.format(c);
    }

    @NonNull
    @Override
    public MyAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_appointment_list_item, parent,
                false);
        return new MyAppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAppointmentAdapter.ViewHolder holder, int position) {

        PatientAppointmentData drModel = list.get(position);


        holder.name.setText(drModel.getFirstName() + " " + drModel.getLastName());
        holder.title.setText(drModel.getAr());

        Log.d(TAG, "onBindViewHolder: " + today);
        if (drModel.getDate().equals(today)) {
            holder.time.setText("Today" + " at " + drModel.getStartTime());
        } else {
            holder.time.setText(drModel.getDate() + " at " + drModel.getStartTime());

        }

        holder.cardView.setOnClickListener(v -> {
            clickListener.onClick(position);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<PatientAppointmentData> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title, name, time;

        ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card);
            title = view.findViewById(R.id.speciality);
            name = view.findViewById(R.id.name);
            time = view.findViewById(R.id.time);

        }
    }
}
