package day0512Wuziqi;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MyChessJPanel extends JPanel implements WuziqiConfig {
	Shape[] shapes;
	//重绘方法
	public void paint(Graphics g) {
		super.paint(g);
		// 棋盘重绘
		for (int i = 0; i < COUNT; i++) {
			g.drawLine(margin_x, margin_y + size * i, (COUNT - 1) * size + margin_x, margin_y + size * i);
			g.drawLine(size * i + margin_x, margin_y, size * i + margin_x, (COUNT - 1) * size + margin_y);
		}
		// 场上棋子重绘
		for (int j = 0; j < shapes.length; j++) {
			if (shapes[j] != null) {
				g.setColor(shapes[j].color);
				g.fillOval(shapes[j].x - 23, shapes[j].y - 23, 46, 46);
			}
		}
	}
}
