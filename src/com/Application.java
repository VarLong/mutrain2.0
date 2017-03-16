package com;


public class Application  extends android.app.Application{
	@Override
	public void onCreate() {	
//		initImageLoader(getApplicationContext());
	}
//	public static void initImageLoader(Context context) {
//		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "UTrain/imageloader/Cache");
//		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
//		config.threadPriority(Thread.NORM_PRIORITY - 2);
//		config.denyCacheImageMultipleSizesInMemory();
//		config.threadPoolSize(3);//线程池内加载的数量   
//		config.memoryCacheSize(5 * 1024 * 1024); // 5 MiB
//		config.discCacheSize(50 * 1024 * 1024) ;
//		config.tasksProcessingOrder(QueueProcessingType.LIFO);
//		config.writeDebugLogs(); // Remove for release app
//		config.discCache(new UnlimitedDiscCache(cacheDir));
//		config.memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) ;//你可以通过自己的内存缓存实现 
//		// Initialize ImageLoader with configuration.
//		ImageLoader.getInstance().init(config.build());
//	}
}

