package com.example.mehmood.splitbill.utils;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class FragmentUtility {
    public static void inflateFragment(Fragment iFragmentToInflate, FragmentManager iFragmentManager, int iContentFrameId, Boolean isAddToBacStack, Boolean isReplace, Bundle iBundle) {
        try {
            Fragment fragment = iFragmentManager.findFragmentByTag(iFragmentToInflate.getClass().getSimpleName());

            // Remove fragment if already added
            if (fragment != null) FragmentUtility.removeFragment(fragment, iFragmentManager);

            FragmentTransaction transaction = iFragmentManager.beginTransaction();
            String TAG = iFragmentToInflate.getClass().getSimpleName();

            if (isReplace) transaction.replace(iContentFrameId, iFragmentToInflate, TAG);
            else transaction.add(iContentFrameId, iFragmentToInflate, TAG);

            if (isAddToBacStack) transaction.addToBackStack(TAG);

            if (iBundle != null) iFragmentToInflate.setArguments(iBundle);
            transaction.commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to remove fragment
     */
    public static void removeFragment(Fragment iFragmentToRemove, FragmentManager iFragmentManager) {
        try {
            FragmentTransaction trans = iFragmentManager.beginTransaction();
            trans.remove(iFragmentToRemove);
            trans.commit();
            iFragmentManager.popBackStackImmediate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
