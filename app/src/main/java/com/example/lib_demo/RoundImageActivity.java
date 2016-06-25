package com.example.lib_demo;

import java.io.File;

import net.nova123.lib.ui.roundimage.RoundImageView;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class RoundImageActivity extends Activity {
	private ImageLoader mImageLoader;

	private TextView mText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roundimage);
		mImageLoader = initImageLoader(this, mImageLoader, "test");
		RoundImageView networkImage = (RoundImageView) findViewById(R.id.roundImage_network);
		RoundImageView oneBorderImage = (RoundImageView) findViewById(R.id.roundImage_one_border);
		RoundImageView twoBorderImage = (RoundImageView) findViewById(R.id.roundImage_two_border);
		mImageLoader
				.displayImage(
						"http://c.hiphotos.baidu.com/image/w%3D2048/sign=744a86ae0d3387449cc5287c6537d8f9/ac345982b2b7d0a28e9adc63caef76094a369af9.jpg",
						networkImage);
		oneBorderImage.setImageResource(R.mipmap.girl);
		twoBorderImage.setImageResource(R.mipmap.girl);
//		mText = (TextView) findViewById(R.id.text_m);
//
//		mText.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View arg0, MotionEvent event) {
//				String msg = "event:" + event.getAction() + " ispressed:";
//				Log.e("moint ", msg);
//				return false;
//			}
//		});
	}

	/**
	 * 初始化图片下载器，图片缓存地址<i>("/Android/data/[app_package_name]/cache/dirName")</i>
	 */
	public ImageLoader initImageLoader(Context context,
									   ImageLoader imageLoader, String dirName) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			// 重新初始化ImageLoader时,需要释放资源.
			imageLoader.destroy();
		}
		imageLoader.init(initImageLoaderConfig(context, dirName));
		return imageLoader;
	}

	/**
	 * 配置图片下载器
	 *
	 * @param dirName
	 *            文件名
	 */
	private ImageLoaderConfiguration initImageLoaderConfig(Context context,
														   String dirName) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(new File(dirName)))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		return config;
	}

	private int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
			// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}
}
