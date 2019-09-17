package day0512Wuziqi;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MyChessJPanel extends JPanel implements WuziqiConfig {
	Shape[] shapes;
	//�ػ淽��
	public void paint(Graphics g) {
		super.paint(g);
		// �����ػ�
		for (int i = 0; i < COUNT; i++) {
			g.drawLine(margin_x, margin_y + size * i, (COUNT - 1) * size + margin_x, margin_y + size * i);
			g.drawLine(size * i + margin_x, margin_y, size * i + margin_x, (COUNT - 1) * size + margin_y);
		}
		// ���������ػ�
		for (int j = 0; j < shapes.length; j++) {
			if (shapes[j] != null) {
				g.setColor(shapes[j].color);
				g.fillOval(shapes[j].x - 23, shapes[j].y - 23, 46, 46);
			}
		}
	}
}
