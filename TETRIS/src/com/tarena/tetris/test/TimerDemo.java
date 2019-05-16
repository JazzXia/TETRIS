package com.tarena.tetris.test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器(Timer)演示 TimerTask 定时器任务 定时器: 在一个特点的时间, 执行某件事情(方法) TimerTask 就是被执行的任务,
 * 是抽象类, 包含 一个抽象的方法run(), 就是被定时执行的方法 使用定时器 1) 创建定时器对象 2) 创建被执行的任务, 实现被执行的方法 3)
 * 将被执行的任务注册到 定时器对象上,定时执行
 * 
 * 取消定时器: 1) 在定时器上调用cancel() 可以关闭定时器.
 */
public class TimerDemo {
	public static void main(String[] args) {
		final Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("HI");
			}
		};
		timer.schedule(task, 1000, 1000);
		// schedule 时间计划
		timer.schedule(new TimerTask() {
			public void run() {
				timer.cancel();
			}
		}, 1000 * 10);
	}
}
