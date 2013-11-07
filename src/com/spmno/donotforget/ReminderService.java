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
		//����һ��֪ͨ����ָ���� ͼ�꣬���⣬��ʱ��
		String forgetName = currentForget.getName();
		Intent intent = new Intent(this,ForgetDetailActivity.class);
		intent.putExtra("name", forgetName);
		PendingIntent i = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //����3��ͨ��Notification.Builder������֪ͨ��ע��������API Level 11֮���֧�֣����Ҫ����Android 2.x�İ汾�����Կ�����ע���ڵĴ���
		String statusBarText = this.getResources().getString(R.string.new_reminder);
		
        Notification notification = new Notification.Builder(this)
                                .setSmallIcon(R.drawable.ic_launcher) //����״̬���е�СͼƬ���ߴ�һ�㽨����24��24�����ͼƬͬ��Ҳ��������״̬��������ʾ�������������Ҫ���������ͼƬ������ʹ��setLargeIcon(Bitmap icon)
                                .setTicker(statusBarText)//������status bar����ʾ����ʾ����
                                .setContentTitle(statusBarText)//����������status bar��Activity���������е�NotififyMessage��TextView����ʾ�ı���
                                .setContentText(forgetName)//TextView����ʾ����ϸ����
                                .setContentIntent(i) //����PendingIntent 
                                .setNumber(1) //��TextView���ҷ���ʾ�����֣��ɷŴ�ͼƬ���������Ҳࡣ���numberͬʱҲ��һ�����кŵ����ң��������������֪ͨ��ͬһID��������ָ����ʾ��һ����
                                .build(); //��Ҫע��build()����API level 16���ӵģ�����ʹ�� getNotificatin()�����

		 
        notification.flags|=Notification.FLAG_AUTO_CANCEL; //�Զ���ֹ
		notification.defaults |= Notification.DEFAULT_SOUND; //Ĭ������
		manager.notify(0, notification);
		reset();
	}
	
	public class ReminderBinder extends Binder {
		ReminderService getService() {
			return ReminderService.this;
		}
	}
}
