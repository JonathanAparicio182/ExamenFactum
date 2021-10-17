package com.ejemplos.kotlin.examenfactum.common;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentos = new ArrayList<>(  );
    private List<String> fragmentosTitulos = new ArrayList<>(  );

    public ViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentos.get( position );
    }

    @Override
    public int getCount() {
        return fragmentos.size();
    }
    //recupera el título a mostrar
    @Override
    public CharSequence getPageTitle(int position){
        return fragmentosTitulos.get( position );
    }
    //agrega fragmentos al tabLayout
    public void addFragment(Fragment fragment, String title){
        fragmentos.add( fragment );
        fragmentosTitulos.add( title );
    }
}
