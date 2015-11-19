package com.ecomerce.deepak.ecomerce.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.ecomerce.deepak.ecomerce.CustomViews.SlidingTabLayout;
import com.ecomerce.deepak.ecomerce.R;
import com.ecomerce.deepak.ecomerce.activities.MainActivity;
import com.ecomerce.deepak.ecomerce.adapter.ProductAdapter;
import com.ecomerce.deepak.ecomerce.adapter.TabAdapter;
import com.ecomerce.deepak.ecomerce.animation.AlphaInAnimationAdapter;
import com.ecomerce.deepak.ecomerce.animation.ScaleInAnimationAdapter;
import com.ecomerce.deepak.ecomerce.interfaces.IDataModel;
import com.ecomerce.deepak.ecomerce.interfaces.IEventListener;
import com.ecomerce.deepak.ecomerce.model.Facet;
import com.ecomerce.deepak.ecomerce.model.Folder;
import com.ecomerce.deepak.ecomerce.model.Product;
import com.ecomerce.deepak.ecomerce.model.SearchResult;
import com.ecomerce.deepak.ecomerce.network.RequestHandler;
import com.ecomerce.deepak.ecomerce.utilities.CommonUtility;
import com.ecomerce.deepak.ecomerce.utilities.UrlBuilder;

import java.util.ArrayList;

/**
 *
 */
public class ProductListFragment extends BaseFragment implements IEventListener {

    private RecyclerView mProductRecyclerListView;
    private TextView mTxtHeader;
    private View mProgressBar;
    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private ProductAdapter mProductAdapter;
    private ArrayList<Product> mProductList = new ArrayList<>();
    private String mUrl = "http://api.buyingiq.com/v1/search/?tags=mobiles&facet=1&page=1";
    private LinearLayoutManager mLinearlayoutManager;
    private boolean isRequestLoading = false;
    private String mNextUrl;
    private TabAdapter mViewPagerAdaptor;
    private SearchResult mSearchResult;
    private Folder mCategoryFolder;
    private ArrayList<String> mTabTitleList = new ArrayList<>();
    private String mSearchQuery = "mobiles";
    private SearchView mSearchView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list,container,false);
        initView(view);
        return view;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        loadData();
    }

    private void initView(View view) {
        mProductRecyclerListView = (RecyclerView) view.findViewById(R.id.product_list);
        mTxtHeader = (TextView) view.findViewById(R.id.txt_header);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mLinearlayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mProductRecyclerListView.setLayoutManager(mLinearlayoutManager);
        mProductRecyclerListView.setItemAnimator(new DefaultItemAnimator());
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        if (((AppCompatActivity)getActivity()) != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.search_view);
        }
        initTabLayout(view);
        mProductAdapter = new ProductAdapter(getActivity(),mProductList);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mProductAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        mProductRecyclerListView.setAdapter(scaleAdapter);
        registerScrollListener();

    }

    private void initTabLayout(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.tab_viewpager);
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.layout_tab, R.id.txt_tab_name);
        mViewPager.setVisibility(View.VISIBLE);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Folder folder = mSearchResult.getFolders().get(position);
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void registerScrollListener() {

        mProductRecyclerListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLinearlayoutManager.getChildCount();
                int totalItemCount = mLinearlayoutManager.getItemCount();
                int firstVisibleItemPosition = mLinearlayoutManager.findFirstVisibleItemPosition();
                if (!isRequestLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        if (!TextUtils.isEmpty(mNextUrl)) {
                            UrlBuilder.setsPageNumber(mNextUrl.trim());
                            loadData();
                        }
                    }
                }
            }
        });
    }
    private void initLoadData() {
        if(null != mSearchView)
        mSearchView.onActionViewCollapsed();
        isRequestLoading = true;
        mProgressBar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
    }
    private void loadData() {
        if(!CommonUtility.isNetworkAvailable()) {
            Toast.makeText(getActivity(),"Network Error",Toast.LENGTH_SHORT).show();
            return;
        }
        initLoadData();
        String url = UrlBuilder.getUrl();
        Log.d("url", "Url:" + url);
        RequestHandler.getInstance().sendJsonRequest(Request.Method.GET, url, null, SearchResult.class, new RequestHandler.RequestCompletedCallback() {
            @Override
            public void onSuccess(IDataModel response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(VolleyError error) {
                Log.d("resp", "Error:" + error);
                handleError(error);

            }
        });
    }



    private void handleError(VolleyError error) {
        mTxtHeader.setText("Error");
        mProgressBar.setVisibility(View.INVISIBLE);
        isRequestLoading = false;
    }

    private void handleResponse(IDataModel response) {
        mProgressBar.setVisibility(View.INVISIBLE);
        isRequestLoading = false;
        mSearchResult = (SearchResult) response;
        String title = "";

        mProductList.addAll(mSearchResult.getProducts());
        mNextUrl = mSearchResult.getNextUrl();
        mTabTitleList.clear();
        for (Folder folder : mSearchResult.getFolders()) {
            mTabTitleList.add(folder.getName());
        }
        extractCategoryList();
        removeCategoryItem();
        mViewPagerAdaptor = new TabAdapter(ProductListFragment.this, mSearchResult.getFolders(), mTabTitleList);
        mViewPager.setAdapter(mViewPagerAdaptor);
        mSlidingTabLayout.setViewPager(mViewPager);
        mProductAdapter.notifyDataSetChanged();
        title = mSearchQuery + "(" + mSearchResult.getTotal() + (mSearchResult.getTotal() > 1 ? " results" : " result") + ")";

        mTxtHeader.setText(title);

    }

    private void extractCategoryList() {
        if(null != mSearchResult) {
            if(!mSearchResult.getFolders().isEmpty() && mSearchResult.getFolders().get(0).getName().equalsIgnoreCase("Categories")) {
                mCategoryFolder = mSearchResult.getFolders().remove(0);
            }
        }
    }

    private void removeCategoryItem() {
        if(null != mSearchResult) {
            if(!mSearchResult.getFolders().isEmpty() && mSearchResult.getFolders().get(0).getName().equalsIgnoreCase("Categories")) {
                mSearchResult.getFolders().remove(0);
            }
        }

        if(null != mTabTitleList) {
            if(!mTabTitleList.isEmpty() && mTabTitleList.get(0).equalsIgnoreCase("Categories")) {
                mTabTitleList.remove(0);
            }
        }
    }
    @Override
    public void onItemChecked(boolean isChecked, String tag) {
        if(isChecked) {
            UrlBuilder.addTag(tag);
        }else {
            UrlBuilder.removeTag(tag);
        }
    }

    @Override
    public void onApplyClicked() {
        mViewPager.setVisibility(View.GONE);
        mProductList.clear();
        loadData();
    }

    @Override
    public void onClearClicked() {
        if(!mSearchResult.getFolders().isEmpty()) {
            for(Folder folder : mSearchResult.getFolders()) {
                for(Facet facet : folder.getFacet()) {
                    facet.setIsSelected(false);
                }
            }
        }
        UrlBuilder.clearUrls();
        mViewPagerAdaptor.notifyDataSetChanged();
    }

    @Override
    public boolean onBackPressed() {
        if(mViewPager.getVisibility() == View.VISIBLE) {
            mViewPager.setVisibility(View.GONE);
            return  false;
        }else {
            return super.onBackPressed();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        MenuItemCompat.setActionView(item, mSearchView);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //  Toast.makeText(getActivity(),query,Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(query)) {
                    UrlBuilder.clearUrls();
                    mSearchQuery = query;
                    UrlBuilder.setTagString(query);
                    loadData();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        mSearchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (mSearchItem != null) {
            mSearchView = (SearchView) mSearchItem.getActionView();
        }
        if (mSearchView != null) {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            mSearchView.isIconified();
            mSearchView.setOnQueryTextListener(this);
        }
        return  super.onPrepareOptionsMenu(menu);
       // return super.onCreateOptionsMenu(menu);
    }*/

}
