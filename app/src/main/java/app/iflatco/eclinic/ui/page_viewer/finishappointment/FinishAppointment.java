package app.iflatco.eclinic.ui.page_viewer.finishappointment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.iflatco.eclinic.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinishAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinishAppointment extends Fragment {


    public static FinishAppointment newInstance(Context context) {

        return new FinishAppointment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finish_appointment, container, false);
    }
}
