package com.jiuli.library.logic;

import com.jiuli.library.listener.ActivtyDataRefreshListener;
import com.jiuli.library.utils.logger.Logger;

public abstract class LibraryAppManager {

	private static final String TAG = LibraryAppManager.class.getName();
	ActivtyDataRefreshListener[] mActivtyDataRefreshListeners = new ActivtyDataRefreshListener[getRefreshNum()];
    public void setActivtyDataRefreshListener(ActivtyDataRefreshListener mActivtyDataRefreshListener,int type){
        try{
            if(type>=0 && type<getRefreshNum())
                mActivtyDataRefreshListeners[type] = mActivtyDataRefreshListener;
        }catch(Throwable e){
            Logger.e(TAG + "setActivtyDataRefreshListener", e);
        }
    }
    
    public abstract int getRefreshNum();
    // 刷新指定的页面的数据
    public synchronized void refreshActivityData(int[] array,int[] refresharray,Object[] objs){
        try{
            if(array == null || array.length <=0)
                return ;
            for(int i = 0;i<array.length;i++){
                if(mActivtyDataRefreshListeners[array[i]] != null)
                    if(objs == null)
                        mActivtyDataRefreshListeners[array[i]].onRefresh(refresharray[i],null);
                    else
                        mActivtyDataRefreshListeners[array[i]].onRefresh(refresharray[i],objs[i]);
            }
        }catch(Throwable e){
        	Logger.e(TAG+"refreshActivityData", e);
        }
    }
}
