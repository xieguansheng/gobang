package day0512Wuziqi;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Time extends Thread {
	JProgressBar bar = new JProgressBar();
	JPanel timepanel = new JPanel();
	JLabel timetext;
	boolean pauseFlag;
	boolean resetFlag;
	public void getbar(JProgressBar bar, JLabel timetext) {
		this.bar = bar;
		this.timetext = timetext;
	}
	String s;
	public Time(String s) {
		this.s = s;
	}

	@Override
	public void run() {
		while (true) {		//��resetFlag�������ʹ�ã�break����ܼ����µ�ѭ��
			for (int i = 600; i >= 0; i--) {
				if (i == 0) {
					JOptionPane.showMessageDialog(null, s + "ʤ");//����ʱ�䵽0�����ж���
				} else {
					if (resetFlag) {	//���¿�ʼ�µ�һ��ʱͨ��resetFlag����ʹ���¿�ʼ�µ�һ��forѭ��
						resetFlag = false;
						break;
					}
					if (pauseFlag) {	//��ͣͨ��һ��pauseFlag����ʹi����ﵽһ����ͣ��Ч��
						i++;
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					try {
						bar.setValue(i);
						timetext.setText("ʱ�䣺 " + Integer.toString(i / 60) + " : " + Integer.toString(i % 60));
						Thread.sleep(1000);		//�߳�����һ��ı�һ��i��ֵ�����ﵽ��ʱ��Ч��
						System.out.println(bar.getValue());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void reset() {
		resetFlag = true;
	}
	public void setPause(boolean flag) {
		pauseFlag = flag;
	}
}
