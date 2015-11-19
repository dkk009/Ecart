package com.ecomerce.deepak.ecomerce.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ecomerce.deepak.ecomerce.CustomViews.RecyclerItemDec;
import com.ecomerce.deepak.ecomerce.R;
import com.ecomerce.deepak.ecomerce.animation.AlphaInAnimationAdapter;
import com.ecomerce.deepak.ecomerce.interfaces.IEventListener;
import com.ecomerce.deepak.ecomerce.model.Facet;
import com.ecomerce.deepak.ecomerce.model.Folder;
import com.ecomerce.deepak.ecomerce.utilities.CommonUtility;

import java.util.ArrayList;

/**
 *
 */
public class TabAdapter extends PagerAdapter {

    private IEventListener mListener;
    private ArrayList<Folder> mFolderList;
    private ArrayList<String> mTabList;
    public TabAdapter(IEventListener listener,ArrayList<Folder> folderList,ArrayList<String> tabNameList) {
        mListener = listener;
        mFolderList = folderList;
        mTabList = tabNameList;

    }



    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.size() > position ? mTabList.get(position) : "";
    }

    @Override
    public int getCount() {
        return mFolderList == null ? 0 : mFolderList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_search_tab, container, false);
        view.setTag(position);
        RecyclerView recyclerViewFacet = (RecyclerView) view.findViewById(R.id.recycler_facet);
        int height = CommonUtility.getScreenHeight();
        recyclerViewFacet.getLayoutParams().height = (int) (height * 0.5);
        recyclerViewFacet.addItemDecoration(new RecyclerItemDec());
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFacet.setLayoutManager(linearlayoutManager);
        FilterAdapter filterAdapter = new FilterAdapter(mListener, (ArrayList<Facet>) mFolderList.get(position).getFacet());
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(filterAdapter);
        recyclerViewFacet.setAdapter(alphaInAnimationAdapter);
        Button btnApply = (Button) view.findViewById(R.id.btn_apply);
        btnApply.setOnClickListener(mOnClickListener);

        Button btnClear = (Button) view.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(mOnClickListener);
        container.addView(view);
        return view;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null == mListener) {
                return;
            }
            switch (v.getId()) {
                case R.id.btn_apply:
                    mListener.onApplyClicked();
                    break;
                case R.id.btn_clear:
                    mListener.onClearClicked();
                    break;


            }
        }
    };
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
