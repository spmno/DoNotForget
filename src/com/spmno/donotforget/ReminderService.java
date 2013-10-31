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

  
				int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
				if (nowHour == firstForget.getHour()) {
					int nowMinute = calendar.get(Calendar.MINUTE);
					if (nowMinute <= firstForget.getMinute()) {
						calendar.set(Calendar.HOUR_OF_DAY, firstForget.getHour());  
						calendar.set(Calendar.MINUTE, firstForget.getMinute());  
						calendar.set(Calendar.SECOND, 0);
						calendar.add(Calendar.DATE, 1);
					}
				} else if (nowHour > firstForget.getHour()) {
					calendar.set(Calendar.HOUR_OF_DAY, firstForget.getHour());  
					calendar.set(Calendar.MINUTE, firstForget.getMinute());  
					calendar.set(Calendar.SECOND, 0);
					calendar.add(Calendar.DATE, 1);
				} else {
					calendar.set(Calendar.HOUR_OF_DAY, firstForget.getHour());  
					calendar.set(Calendar.MINUTE, firstForget.getMinute());  
					calendar.set(Calendar.SECOND, 0);
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
		//����һ��֪ͨ����ָ���� ͼ�꣬���⣬��ʱ��
		PendingIntent i = PendingIntent.getActivity(this, 0, new Intent(this,ForgetDetailActivity.class), 0);
        //����3��ͨ��Notification.Builder������֪ͨ��ע��������API Level 11֮���֧�֣����Ҫ����Android 2.x�İ汾�����Կ�����ע���ڵĴ���
        Notification notification = new Notification.Builder(this)
                                .setSmallIcon(R.drawable.ic_launcher) //����״̬���е�СͼƬ���ߴ�һ�㽨����24��24�����ͼƬͬ��Ҳ��������״̬��������ʾ�������������Ҫ���������ͼƬ������ʹ��setLargeIcon(Bitmap icon)
                                .setTicker("new Reminder")//������status bar����ʾ����ʾ����
                                .setContentTitle("Reminder")//����������status bar��Activity���������е�NotififyMessage��TextView����ʾ�ı���
                                .setContentText("Notification Text")//TextView����ʾ����ϸ����
                                .setContentIntent(i) //����PendingIntent 
                                .setNumber(1) //��TextView���ҷ���ʾ�����֣��ɷŴ�ͼƬ���������Ҳࡣ���numberͬʱҲ��һ�����кŵ����ң��������������֪ͨ��ͬһID��������ָ����ʾ��һ����
                                .build(); //��Ҫע��build()����API level 16���ӵģ�����ʹ�� getNotificatin()�����

		 
        notification.flags|=Notification.FLAG_AUTO_CANCEL; //�Զ���ֹ
		notification.defaults |= Notification.DEFAULT_SOUND; //Ĭ������
	}
	
}
