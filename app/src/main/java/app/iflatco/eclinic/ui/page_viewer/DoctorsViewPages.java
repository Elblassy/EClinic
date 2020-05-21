package app.iflatco.eclinic.ui.page_viewer;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ActivityDoctorsViewPagesBinding;
import app.iflatco.eclinic.ui.page_viewer.DoctorsViewPagerViewModel;
import app.iflatco.eclinic.ui.page_viewer.PagerAdapter;
import app.iflatco.eclinic.ui.page_viewer.select_doctor.SelectDoctor;

public class DoctorsViewPages extends AppCompatActivity {

    private DoctorsViewPagerViewModel mViewModel;
    private ActivityDoctorsViewPagesBinding binding;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorsViewPagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        setViewPager();

        if (getItem(0) == 0) {
            binding.prev.setVisibility(View.GONE);
        }
    }

    private void setViewPager() {
        pagerAdapter.addFragment(SelectDoctor.newInstance(this));
        pagerAdapter.addFragment(SelectDoctor.newInstance(this));
        pagerAdapter.addFragment(SelectDoctor.newInstance(this));
        binding.masterViewPager.setAdapter(pagerAdapter);
        binding.masterViewPager.setPagingEnabled(false);
        binding.next.setOnClickListener(v -> {
            binding.masterViewPager.setCurrentItem(getItem(+1), true);
            if (binding.masterViewPager.getCurrentItem() == 0) {
                binding.prev.setVisibility(View.GONE);
            } else {
                binding.prev.setVisibility(View.VISIBLE);
            }
        });

        binding.prev.setOnClickListener(v -> {
            binding.masterViewPager.setCurrentItem(getItem(-1), true);
            if (binding.masterViewPager.getCurrentItem() == 0) {
                binding.prev.setVisibility(View.GONE);
            } else {
                binding.prev.setVisibility(View.VISIBLE);
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
}
