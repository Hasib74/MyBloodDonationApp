package com.example.sbb;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FeedbackPager_Adapter extends FragmentPagerAdapter {
    public FeedbackPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                FeedBack_one feedBack_one = new FeedBack_one();
                return feedBack_one;

            case 1:
                SubmitFragement submitFragement = new SubmitFragement();
                return submitFragement;

            case 2:
                ServicesFragment servicesFragment = new ServicesFragment();
                return servicesFragment;

            case 3:
                DonarFragment donarFragment = new DonarFragment();
                return donarFragment;

                default:
                    return null;
        }


    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Welcome";

            case 1:
                return "Services";

            case 2:
                return "Donner";

            case 3:
                return "Submit";

        }

        return super.getPageTitle(position);
    }
}
