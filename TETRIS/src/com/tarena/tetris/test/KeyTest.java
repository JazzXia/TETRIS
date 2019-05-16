package com.tarena.tetris.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * KeyListener 是接口, 接口是一种极端的抽象类, 接口: 1) 全部的方法是抽象方法, 全部的属性是常量 2)
 * 可以被类实现(implements 继承) 3) 接口可以定义变量, 引用子类实例 4) 接口只能被实现, 不能直接实例化 5) 实现接口,
 * 就必须实现全部的抽象方法 接口的抽象方法, 是对子类的约定 如: KeyListener 接口中约定了子类 必须包含3个方法 keyPressed
 * keyTyped keyReleased KeyListener是处理键盘事件的接口, 是Java Swing对用户 如何获取键盘敲击情况的约定.
 * 就是说: Swing 在发现有键盘 敲击时候, 会调用的方法.
 */
public class KeyTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.add(panel);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// 绑定键盘事件到面板
		KeyListener l = new KeyChecker();
		panel.addKeyListener(l);
		panel.requestFocus();// panel请求 键盘输入的焦点
	}
}

class KeyChecker implements KeyListener {
	@Override
	// e 对象中包含: 时间, 地点, 人物
	public void keyTyped(KeyEvent e) {
		// 按键敲击
		System.out.println(e.getWhen());
		System.out.println("Typed:" + e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// 按键按下
		System.out.println("Pressed" + e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// 按键释放
		System.out.println("Released" + e.getKeyChar());
	}
}
