package com.ecomerce.deepak.ecomerce.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ecomerce.deepak.ecomerce.R;
import com.ecomerce.deepak.ecomerce.fragments.BaseFragment;
import com.ecomerce.deepak.ecomerce.fragments.ProductListFragment;
import com.ecomerce.deepak.ecomerce.model.Product;

import java.util.List;

public class MainActivity extends BaseActivity implements BaseFragment.IntrCommunicator{

    private ProductListFragment mProductListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mProductListFragment = new ProductListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mProductListFragment, ProductListFragment.class.getSimpleName()).commit();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadFragment(BaseFragment fragment) {

    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                if(!((BaseFragment)fragment).onBackPressed()) {
                    return;
                };
            }
        }
        super.onBackPressed();
    }
}
