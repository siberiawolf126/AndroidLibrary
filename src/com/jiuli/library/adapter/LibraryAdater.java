package com.jiuli.library.adapter;

import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class LibraryAdater extends BaseAdapter {

	private Context context;
	public List<Object> mList;
	public LibraryAdater(Context context,List<Object> mList){
		this.context = context;
		this.mList = mList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	public abstract int getViewLayout();
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(getViewLayout(), parent,false);
		}
		return convertView;
	}
	
	
	
	
	class ViewHolder {  
	    // I added a generic return type to reduce the casting noise in client code  
	    @SuppressWarnings("unchecked")  
	    public <T extends View> T get(View view, int id) {  
	        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();  
	        if (viewHolder == null) {  
	            viewHolder = new SparseArray<View>();  
	            view.setTag(viewHolder);  
	        }  
	        View childView = viewHolder.get(id);  
	        if (childView == null) {  
	            childView = view.findViewById(id);  
	            viewHolder.put(id, childView);  
	        }  
	        return (T) childView;  
	    }  
	};  
	
	

}
