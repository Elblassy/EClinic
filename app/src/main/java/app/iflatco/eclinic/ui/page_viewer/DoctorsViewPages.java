package app.iflatco.eclinic.ui.page_viewer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ActivityDoctorsViewPagesBinding;
import app.iflatco.eclinic.ui.page_viewer.dr_appointment.DrAppointment;
import app.iflatco.eclinic.ui.page_viewer.finishappointment.FinishAppointment;
import app.iflatco.eclinic.ui.page_viewer.payment.Payment;
import app.iflatco.eclinic.ui.page_viewer.select_doctor.SelectDoctor;
import app.iflatco.eclinic.utils.OnAppointmentSelected;
import app.iflatco.eclinic.utils.OnDoctorSelected;

public class DoctorsViewPages extends AppCompatActivity implements OnDoctorSelected, OnAppointmentSelected {
    private static final String TAG = "DoctorsViewPages";

    private DoctorsViewPagerViewModel mViewModel;
    private ActivityDoctorsViewPagesBinding binding;
    PagerAdapter pagerAdapter;
    private int doctorId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorsViewPagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        setViewPager(getIntent().getIntExtra("category_id", 0));

        if (getItem(0) == 0) {
            binding.prev.setVisibility(View.GONE);
        }
    }

    private void setViewPager(int id) {
        pagerAdapter.addFragment(SelectDoctor.newInstance(this, id));
        pagerAdapter.addFragment(DrAppointment.newInstance(this));
        pagerAdapter.addFragment(Payment.newInstance(this));
        pagerAdapter.addFragment(FinishAppointment.newInstance(this));

        binding.masterViewPager.setAdapter(pagerAdapter);
        binding.masterViewPager.setPagingEnabled(false);


        binding.prev.setOnClickListener(v -> {
            if (binding.masterViewPager.getCurrentItem() != 3) {
                binding.masterViewPager.setCurrentItem(getItem(-1), true);
                if (binding.masterViewPager.getCurrentItem() == 0) {
                    binding.prev.setVisibility(View.GONE);
                } else if (binding.masterViewPager.getCurrentItem() == 3) {
                    binding.prev.setImageResource(R.drawable.ic_home_24dp);
                } else {
                    binding.prev.setVisibility(View.VISIBLE);
                }
            } else {
                onBackPressed();
            }
        });
        binding.dotsIndicator.setViewPager(binding.masterViewPager);
    }

    private int getItem(int i) {
        return binding.masterViewPager.getCurrentItem() + i;
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

    }

    @Override
    public void onClicked(int pos, String name) {

        binding.prev.setVisibility(View.VISIBLE);
        Log.d(TAG, "onDoctorSelected: " + pos);
        String tag = "android:switcher:" + binding.masterViewPager.getId() + ":" + 1;
        DrAppointment f = (DrAppointment) getSupportFragmentManager().findFragmentByTag(tag);
        assert f != null;
        f.setData(pos, name);

        binding.masterViewPager.setCurrentItem(getItem(1), true);


    }

    @Override
    public void onSelectedAppointment(int id) {
        String tag = "android:switcher:" + binding.masterViewPager.getId() + ":" + 2;
        Log.d(TAG, "onSelectedAppointment: " + tag);
        Payment f = (Payment) getSupportFragmentManager().findFragmentByTag(tag);
        assert f != null;
        f.setId(id);

        binding.masterViewPager.setCurrentItem(getItem(1), true);
    }
}
