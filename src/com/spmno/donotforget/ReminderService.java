package com.spmno.donotforget;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import util.TimeSelector;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.spmno.donotforget.adapter.ExistForgetListAdapter;
import com.spmno.donotforget.data.DataBaseHelper;
import com.spmno.donotforget.data.Forget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ReminderService extends Service {
	private Timer timer;
	private TimerTask timerTask;
	final int timeInteral = 1000 * 60;
	private final IBinder binder = new ReminderBinder();
	private Forget currentForget;
	public ReminderService() {
	}
	
	@Override 
	public void onCreate() {
		Context appContext = getApplicationContext();
		DataBaseHelper.setContext(appContext);
		timer = new Timer();
		reset();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		timer.cancel();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
		// TODO: Return the communication channel to the service.
		//throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public void reset() {
		timer.cancel();
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				reminder();
			}
			
		};
		DataBaseHelper databaseHelper = DataBaseHelper.getInstance();
		Dao<Forget, Integer> forgetDao = databaseHelper.getForgetDao();
		try {
			QueryBuilder<Forget, Integer> queryBuilder = forgetDao.queryBuilder();
			queryBuilder.where().isNotNull("hour");
			PreparedQuery<Forget> preparedQuery = queryBuilder.prepare();
			//Forget firstForget = forgetDao.queryForFirst(preparedQuery);
			List<Forget> forgetList = forgetDao.query(preparedQuery);
			
			if (forgetList.isEmpty()) {
				return ;
			}
			TimeSelector timeSelector = new TimeSelector();
			currentForget = timeSelector.select(forgetList);
			Calendar calendar = Calendar.getInstance();
			if (!timeSelector.isToday()) {
				calendar.add(Calendar.DATE, 1);
			}
			calendar.set(Calendar.HOUR_OF_DAY, currentForget.getHour());  
			calendar.set(Calendar.MINUTE, currentForget.getMinute());  
			calendar.set(Calendar.SECOND, 0);
			Date time = calendar.getTime(); 
			timer.schedule(timerTask, time);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reminder() {
		NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
		//构建一个通知对象，指定了 图标，标题，和时间
		String forgetName = currentForget.getName();
		Intent intent = new Intent(this,ForgetDetailActivity.class);
		intent.putExtra("name", forgetName);
		PendingIntent i = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //步骤3：通过Notification.Builder来创建通知，注意这是在API Level 11之后才支持，如果要兼容Android 2.x的版本，可以看后面注释内的代码
		String statusBarText = this.getResources().getString(R.string.new_reminder);
		
        Notification notification = new Notification.Builder(this)
                                .setSmallIcon(R.drawable.ic_launcher) //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap icon)
                                .setTicker(statusBarText)//设置在status bar上显示的提示文字
                                .setContentTitle(statusBarText)//设置在下拉status bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                                .setContentText(forgetName)//TextView中显示的详细内容
                                .setContentIntent(i) //关联PendingIntent 
                                .setNumber(1) //在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
                                .build(); //需要注意build()是在API level 16增加的，可以使用 getNotificatin()来替代

		 
        notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
		notification.defaults |= Notification.DEFAULT_SOUND; //默认声音
		manager.notify(0, notification);
		reset();
	}
	
	public class ReminderBinder extends Binder {
		ReminderService getService() {
			return ReminderService.this;
		}
	}
}
