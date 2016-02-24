package com.nucyzh.notes.util;

import android.app.Activity;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author:XiYang on 2016/2/20.
 * Email:765849854@qq.com
 * @class description 系统退出管理器
 */
public class ExitManager {

	/** 存放所有打开的Activity */
	private List<Activity> activityList = new LinkedList<Activity>();
	
	/** 单例模式创建的对象的引用 */
	private static ExitManager instance;
	
	/** 判断是否退出 */
	private static Boolean isExit = false;
	
	/**
	 * 私有构造方法
	 */
	private ExitManager() {

	} 
	
	
	/**
	 * 单例模式构造对象
	 * @return 进行退出管理的对象
	 */
	public static ExitManager getInstance() {
		
		if(instance == null){
			instance = new ExitManager();
		}
		return instance;
	}
	
	/**
	 * 将创建的Activity添加到集合中
	 * @param activity  Activity对象
	 */
    public void addActivity(Activity activity){
    	activityList.add(activity);
    }
    
    /**
     * 双击退出
     * @param context 上下文
     */
    public void exit(Activity context){
    	
    	//定时器
		Timer timer = null;
		
		if (isExit == false) {
			isExit = true;
			Toast.makeText(context, "再按一次退出系统", Toast.LENGTH_SHORT).show();
			timer = new Timer();
			timer.schedule(new TimerTask() {
				
				public void run() {
					isExit = false;
				}
			}, 2000);

		} else {
			for(Activity activity:activityList){
				
				if(!activity.isFinishing()){
					
					activity.finish();
				}
			}
		}
    }
    
    /**
     * 直接退出
     */
    public void exit(){
    	
    	for(Activity activity:activityList){
    		if(!activity.isFinishing()){
    			activity.finish();
    		}
		}
    }
    
}
