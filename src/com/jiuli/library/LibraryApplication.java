package com.jiuli.library;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.jiuli.library.comm.LibraryComm;
import com.jiuli.library.comm.LibraryGlobal;
import com.jiuli.library.utils.logger.LogLevel;
import com.jiuli.library.utils.logger.Logger;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public abstract class LibraryApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		if (LibraryComm.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}
		super.onCreate();
		
		LibraryGlobal.mContext = getApplicationContext();
		
		initImageLoader(getApplicationContext());
		
		 Logger.init(getPackageName())               // default PRETTYLOGGER or use just init()
         .setMethodCount(3)            // default 2
         .hideThreadInfo()            // default shown
         .setLogLevel(LogLevel.FULL)  // default LogLevel.FULL
         .setMethodOffset(2);           // default 0
		
		Logger.d("====>"+getPackageName());
	}
	
	
	
	public void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		File cacheDir = StorageUtils.getOwnCacheDirectory(LibraryGlobal.mContext,getOwnCacheDirectory());
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.memoryCacheExtraOptions(480, 800); // max width, max height，即保存的每个缓存文件的最大长宽
		config.threadPoolSize(3);//线程池内加载的数量
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)); // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.memoryCacheSize(2 * 1024 * 1024); // 2MiB
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.discCacheFileCount(100); //缓存的文件数量
        config.discCache(new UnlimitedDiskCache(cacheDir));//自定义缓存路径
        config.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
        config.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000));// connectTimeout (5 s), readTimeout (30 s)超时时间
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
	
	public abstract String getOwnCacheDirectory();
	
	

}
