package com.ecomerce.deepak.ecomerce.interfaces;

/**
 *
 */
public interface IEventListener {
    public void onItemChecked(boolean isChecked,String tag);
    public void  onApplyClicked();
    public void onClearClicked();
}
