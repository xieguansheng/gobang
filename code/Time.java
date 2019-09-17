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
		while (true) {		//和resetFlag变量配合使用，break后才能继续新的循环
			for (int i = 600; i >= 0; i--) {
				if (i == 0) {
					JOptionPane.showMessageDialog(null, s + "胜");//假如时间到0了则判断输
				} else {
					if (resetFlag) {	//重新开始新的一局时通过resetFlag变量使重新开始新的一次for循环
						resetFlag = false;
						break;
					}
					if (pauseFlag) {	//暂停通过一个pauseFlag变量使i不变达到一个暂停的效果
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
						timetext.setText("时间： " + Integer.toString(i / 60) + " : " + Integer.toString(i % 60));
						Thread.sleep(1000);		//线程休眠一秒改变一次i的值，即达到计时的效果
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
