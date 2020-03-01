package com.example.sbb;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabHolder extends FragmentPagerAdapter {

    public TabHolder(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
            Card card = new Card();
            return card;
            case 1:
                Users users = new Users();
                return users;

                default:
                    return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Donor";

            case 1:
                return "Contact";
        }

        return super.getPageTitle(position);
    }
}

