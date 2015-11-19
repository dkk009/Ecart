package com.ecomerce.deepak.ecomerce.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ecomerce.deepak.ecomerce.R;
import com.ecomerce.deepak.ecomerce.interfaces.IEventListener;
import com.ecomerce.deepak.ecomerce.model.Facet;

import java.util.ArrayList;

/**
 *
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder>{

    private IEventListener mListener;
    private ArrayList<Facet> mFacetList;

    public FilterAdapter(IEventListener listener,ArrayList<Facet> list) {
        mFacetList = list;
        mListener = listener;
    }
    @Override
    public FilterAdapter.FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        FilterViewHolder filterViewHolder = new FilterViewHolder(view,mListener);
        return filterViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterAdapter.FilterViewHolder holder, int position) {
            final Facet facet = mFacetList.get(position);
            holder.bindData(facet);
    }



    @Override
    public int getItemCount() {
        return null ==mFacetList ? 0 : mFacetList.size();
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox mChkField;
        private final TextView mTxtProductName;
        private final View view;
        private final IEventListener mListener;
        public FilterViewHolder(View itemView,IEventListener listener) {
            super(itemView);
            mChkField = (CheckBox) itemView.findViewById(R.id.chk_selection);
            mTxtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
            view= itemView;
            mListener = listener;
        }

        public void bindData(Facet facet) {
            mChkField.setChecked(facet.isSelected());
            mTxtProductName.setText(facet.getLabel()+ "("+ facet.getCount() +")");
            itemView.setTag(facet);
            mChkField.setTag(facet);
            view.setOnClickListener(mOnClickListener);
            mChkField.setOnClickListener(mOnClickListener);

        }

        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facet facet = (Facet) v.getTag();
                boolean isChecked = !facet.isSelected();
                facet.setIsSelected(isChecked);
                mChkField.setChecked(isChecked);
                if(null != mListener) {
                    mListener.onItemChecked(isChecked,facet.getTag());
                }
            }
        };

    }
}
