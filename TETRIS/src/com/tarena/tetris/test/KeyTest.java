package com.tarena.tetris.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * KeyListener �ǽӿ�, �ӿ���һ�ּ��˵ĳ�����, �ӿ�: 1) ȫ���ķ����ǳ��󷽷�, ȫ���������ǳ��� 2)
 * ���Ա���ʵ��(implements �̳�) 3) �ӿڿ��Զ������, ��������ʵ�� 4) �ӿ�ֻ�ܱ�ʵ��, ����ֱ��ʵ���� 5) ʵ�ֽӿ�,
 * �ͱ���ʵ��ȫ���ĳ��󷽷� �ӿڵĳ��󷽷�, �Ƕ������Լ�� ��: KeyListener �ӿ���Լ�������� �������3������ keyPressed
 * keyTyped keyReleased KeyListener�Ǵ�������¼��Ľӿ�, ��Java Swing���û� ��λ�ȡ�����û������Լ��.
 * ����˵: Swing �ڷ����м��� �û�ʱ��, ����õķ���.
 */
public class KeyTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.add(panel);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// �󶨼����¼������
		KeyListener l = new KeyChecker();
		panel.addKeyListener(l);
		panel.requestFocus();// panel���� ��������Ľ���
	}
}

class KeyChecker implements KeyListener {
	@Override
	// e �����а���: ʱ��, �ص�, ����
	public void keyTyped(KeyEvent e) {
		// �����û�
		System.out.println(e.getWhen());
		System.out.println("Typed:" + e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// ��������
		System.out.println("Pressed" + e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// �����ͷ�
		System.out.println("Released" + e.getKeyChar());
	}
}
