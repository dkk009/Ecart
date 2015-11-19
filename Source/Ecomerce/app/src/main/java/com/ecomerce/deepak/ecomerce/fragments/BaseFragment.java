package com.ecomerce.deepak.ecomerce.fragments;

import android.support.v4.app.Fragment;

/**
 *
 */
public class BaseFragment extends Fragment{

    private boolean isBackPressed = true;
    public interface IntrCommunicator {
        public void loadFragment(BaseFragment fragment);
    }

    public boolean onBackPressed() {
        return  isBackPressed;
    }
}
