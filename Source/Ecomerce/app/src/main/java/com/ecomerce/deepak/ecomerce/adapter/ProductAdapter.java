package com.ecomerce.deepak.ecomerce.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ecomerce.deepak.ecomerce.Constants.Constants;
import com.ecomerce.deepak.ecomerce.R;
import com.ecomerce.deepak.ecomerce.model.Image;
import com.ecomerce.deepak.ecomerce.model.Product;
import com.ecomerce.deepak.ecomerce.network.ImageDownloader;
import com.ecomerce.deepak.ecomerce.utilities.CommonUtility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{


    private Context mContext;
    private ArrayList<Product> mProductList;

    public ProductAdapter(Context context,ArrayList<Product> productList) {
        mContext = context;
        mProductList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_layout,parent,false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.bindData(product);
    }

    @Override
    public int getItemCount() {
        return null == mProductList ? 0 : mProductList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtProductName;
        private final ImageView imgProductImage;
        private final TextView txtProductPrice;
        private final TextView txtStoreCount;
        private final TextView txtRatingBar;
        private final TextView txtRatingValue;
        private Context mContext;
        public ProductViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            txtProductName = (TextView) itemView.findViewById(R.id.txt_product_name);
            imgProductImage = (ImageView) itemView.findViewById(R.id.img_product);
            txtProductPrice = (TextView) itemView.findViewById(R.id.txt_product_price);
            txtStoreCount = (TextView) itemView.findViewById(R.id.txt_store_count);
            txtRatingBar = (TextView) itemView.findViewById(R.id.txt_rating_bar);
            txtRatingValue = (TextView) itemView.findViewById(R.id.txt_rating_count);
        }

        public void bindData(Product product) {
            txtProductName.setText(product.getName());
            txtProductPrice.setText(mContext.getString(R.string.rupee_sign) +". " + product.getMinPriceStr());
            int imageViewSize = (int) (CommonUtility.getScreenWidth() * 0.3);
            imgProductImage.getLayoutParams().height = imageViewSize;
            imgProductImage.getLayoutParams().width = imageViewSize;

            String storeCountText= product.getRatingCount() +"" + (product.getRatingCount() > 1 ? mContext.getString(R.string.stores) : mContext.getString(R.string.store));
            txtStoreCount.setText(storeCountText);

            txtRatingBar.setText("" + product.getAvgRating());
            Double colorValue = Double.valueOf(product.getAvgRating());
            txtRatingBar.setBackgroundColor(Color.rgb(0, (int) (255 - (colorValue * 20)), 0));
            if(product.getRatingCount() != 0) {
                String txtRatingCount = product.getRatingCount() > 1 ? "Votes" : "Vote";
                txtRatingValue.setText(txtRatingCount);
            }

            String imageUrl = getBestImageUrl(product.getImages_o());
            imgProductImage.setImageResource(R.drawable.placeholder);
            if(!TextUtils.isEmpty(imageUrl)) {
                downloadImage(imageUrl);
            }

        }

        public String getBestImageUrl(HashMap<String,String> images) {
            int deviceType = CommonUtility.getDeviceDpType();
            switch (deviceType) {
                case Constants.LOW_END_DEVICE:
                    return getSuitableUrlForLowEndDevice(images);
                case Constants.HIGH_END_DEVICE:
                case Constants.MEDIUM_DEVICE:
                    return getSuitableUrlForHighEndDevice(images);
                case Constants.VERY_HIGH_END_DEVICE:
                    return getSuitableUrlForVeryHighEndDevice(images);
            }
            return getSuitableUrlForVeryHighEndDevice(images);
        }

        private String getSuitableUrlForVeryHighEndDevice(HashMap<String, String> images) {
            String keys[] = {Constants.IMG_XLARGE,Constants.IMG_LARGE,Constants.IMG_MEDIUM,Constants.IMG_SMALL};
            String url = "";
            for(String key : keys) {
                url = images.get(key);
                if(!TextUtils.isEmpty(url)) {
                    break;
                }
            }
            return url;
        }

        private String getSuitableUrlForHighEndDevice(HashMap<String, String> images) {
            String keys[] = {Constants.IMG_LARGE,Constants.IMG_XLARGE,Constants.IMG_MEDIUM,Constants.IMG_SMALL};
            String url = "";
            for(String key : keys) {
                url = images.get(key);
                if(!TextUtils.isEmpty(url)) {
                    break;
                }
            }
            return url;
        }


        private String getSuitableUrlForLowEndDevice(HashMap<String, String> images) {
            String keys[] = {Constants.IMG_MEDIUM,Constants.IMG_SMALL,Constants.IMG_LARGE,Constants.IMG_XLARGE};
            String url = "";
            for(String key : keys) {
                url = images.get(key);
                if(!TextUtils.isEmpty(url)) {
                    break;
                }
            }
            return url;
        }

        public void downloadImage(final String url) {
            ImageDownloader.getInstance().getImageLoader().get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if(null != imgProductImage && null != response.getBitmap()) {
                        imgProductImage.setImageBitmap(response.getBitmap());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            },imgProductImage.getWidth(),imgProductImage.getHeight());

            Log.d("image","Url:" + url);
        }
    }
}
