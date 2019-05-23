package com.lldj.tc.guide;

import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import java.util.List;

/**
 * 
 * @Description: viewPager的adapter
 * @author wlj
 * @date 2014年12月31日 下午1:50:55
 */
public class GuidePagerAdapter extends PagerAdapter {

	private List<View> views = null;

	public GuidePagerAdapter(List<View> views) {
		this.views = views;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {

	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1), 0);
		return views.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

}
