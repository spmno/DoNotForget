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
import android.os.IBinder;

public class ReminderService extends Service {
	private Timer timer;
	private TimerTask timerTask;
	final int timeInteral = 1000 * 60;
	public ReminderService() {
	}
	
	@Override 
	public void onCreate() {
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
			//List<Forget> forgets = forgetDao.queryForAll();
			//ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
			//for (Forget forget : forgets) {

			//}
			QueryBuilder<Forget, Integer> queryBuilder = forgetDao.queryBuilder();
			queryBuilder.where().isNotNull("hour");
			PreparedQuery<Forget> preparedQuery = queryBuilder.prepare();
			Forget firstForget = forgetDao.queryForFirst(preparedQuery);
			if (firstForget != null) {
				Calendar calendar = Calendar.getInstance();

				calendar.set(Calendar.HOUR_OF_DAY, firstForget.getHour());  
				calendar.set(Calendar.MINUTE, firstForget.getMinute());  
				calendar.set(Calendar.SECOND, 0);  
				int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
				if (nowHour == firstForget.getHour()) {
					int nowMinute = calendar.get(Calendar.MINUTE);
					if (nowMinute >= firstForget.getMinute()) {
						calendar.add(Calendar.DATE, 1);
					}
				} else if (nowHour > firstForget.getHour()) {
					calendar.add(Calendar.DATE, 1);
				}
				Date time = calendar.getTime(); 
				timer.schedule(timerTask, time);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public void reminder() {
		NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
		//构建一个通知对象，指定了 图标，标题，和时间
		PendingIntent i = PendingIntent.getActivity(this, 0, new Intent(this,ForgetDetailActivity.class), 0);
        //步骤3：通过Notification.Builder来创建通知，注意这是在API Level 11之后才支持，如果要兼容Android 2.x的版本，可以看后面注释内的代码
        Notification notification = new Notification.Builder(this)
                                .setSmallIcon(R.drawable.ic_launcher) //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap icon)
                                .setTicker("new Reminder")//设置在status bar上显示的提示文字
                                .setContentTitle("Reminder")//设置在下拉status bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                                .setContentText("Notification Text")//TextView中显示的详细内容
                                .setContentIntent(i) //关联PendingIntent 
                                .setNumber(1) //在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
                                .build(); //需要注意build()是在API level 16增加的，可以使用 getNotificatin()来替代

		 
        notification.flags|=Notification.FLAG_AUTO_CANCEL; //自动终止
		notification.defaults |= Notification.DEFAULT_SOUND; //默认声音
	}
	
}
