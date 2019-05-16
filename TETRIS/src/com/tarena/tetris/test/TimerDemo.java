package com.tarena.tetris.test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ʱ��(Timer)��ʾ TimerTask ��ʱ������ ��ʱ��: ��һ���ص��ʱ��, ִ��ĳ������(����) TimerTask ���Ǳ�ִ�е�����,
 * �ǳ�����, ���� һ������ķ���run(), ���Ǳ���ʱִ�еķ��� ʹ�ö�ʱ�� 1) ������ʱ������ 2) ������ִ�е�����, ʵ�ֱ�ִ�еķ��� 3)
 * ����ִ�е�����ע�ᵽ ��ʱ��������,��ʱִ��
 * 
 * ȡ����ʱ��: 1) �ڶ�ʱ���ϵ���cancel() ���Թرն�ʱ��.
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
		// schedule ʱ��ƻ�
		timer.schedule(new TimerTask() {
			public void run() {
				timer.cancel();
			}
		}, 1000 * 10);
	}
}
