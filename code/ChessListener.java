package day0512Wuziqi;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import day0426huatuban.MyJPanel;

public class ChessListener implements ActionListener, MouseListener, WuziqiConfig {

	int x1, y1, x, y, number_x, number_y;
	int t = 0;
	//��ɫ���������Ӽ���
	int colorcount = 1;
	int count = 0;
	int index = 0;

	//������ɫ
	Color color = Color.BLACK;
	Shape[] shapes = new Shape[10000];//�ػ�ͻ�����Ҫ������
	
	
	String[][] string = new String[17][17];//���̵ļ�¼
	int[][] number = new int[17][17];//Ȩֵ����

	// ���ݻ��ʵķ���
	Graphics g;
	public void setg(Graphics g) {
		this.g = g;
	}

	// ���ݰ�ť�����������¹�����������shape���ݵ�repaint������
	JButton button1, button2, button3, button4;
	MyChessJPanel chesspanel;
	JProgressBar bar = new JProgressBar();
	JLabel timetext = new JLabel();
	public void setshapes(MyChessJPanel chesspanel, JButton button1, JButton button2, JButton button3,
			JButton button4,JProgressBar bar,JLabel timetext) {
		this.button1 = button1;
		this.button2 = button2;
		this.button3 = button3;
		this.button4 = button4;	
		this.bar = bar;
		this.timetext = timetext;
		this.chesspanel = chesspanel;
		
		chesspanel.shapes = shapes;
	}
	
	Time time1 = new Time("����");
	Time time2 = new Time("����");
	
	String str;
	int o =0;
	@Override
	public void actionPerformed(ActionEvent e) {
		// �������������ұ�֤������������ɫ����ȷ��
		if ("����".equals(e.getActionCommand())) {
			if (color == Color.WHITE) {
				colorcount = 2;
			} else {
				colorcount = 1;
			}
			if ("˫�˶�ս".equals(str)) {
				string[(shapes[index].x - margin_x) / size][(shapes[index].y - margin_y) / size] = "0";
				shapes[index] = null;
				index--;
			} else if ("�˻���ս".equals(str)) {
				string[(shapes[index - 1].x - margin_x) / size][(shapes[index - 1].y - margin_y) / size] = "0";
				string[(shapes[index].x - margin_x) / size][(shapes[index].y - margin_y) / size] = "0";
				shapes[index] = null;
				shapes[index - 1] = null;
				index = index - 2;
			}
			chesspanel.repaint();
		}
		
		else {
			str = e.getActionCommand();
			if (button1 == e.getSource() || button2 == e.getSource()) {
				time1.getbar(bar, timetext);
				time2.getbar(bar, timetext);
				
				if(o==0){
				time1.start();
				o++;
				}
				
				else if(o>=1){
					o=o+2;
					time1.reset();
					time1.setPause(false);
					
				}
				for (int i = 0; i < 17; i++) {
					for (int j = 0; j < 17; j++) {
						string[i][j] = "0";
					}
				}
				
			}
			//���¿�ʼҪ���е���������
			if (button3 == e.getSource()) {
				for (int i = 0; i < shapes.length; i++) {
					shapes[i] = null;
				}
				time1.setPause(true);
				time2.setPause(true);
				
				index = 0;
				chesspanel.repaint();
				colorcount = 1;
				count = 0;
			}
		}
	}
	int p=2;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//���ô����ַ�����ķ���
		Judge ju = new Judge();
		ju.setstring(string);
		if ("˫�˶�ս".equals(str)) {
			//��ȡ����
			x1 = e.getX();
			y1 = e.getY();
			//�ж��������ڣ��ҵó�����λ�ø���
			if (x1 < (COUNT - 1) * size + margin_x + size / 2 && y1 < (COUNT - 1) * size + margin_y + size / 2) {
				number_x = (x1 - margin_x) / size;
				if ((x1 - margin_x) % size < size / 2) {
					x = margin_x + number_x * size;
				} else {
					number_x++;
					x = margin_x + number_x * size;
				}
				number_y = (y1 - margin_y) / size;
				if ((y1 - margin_y) % size < size / 2) {
					y = margin_y + number_y * size;
				} else {
					number_y++;
					y = margin_y + number_y * size;
				}
				if ("0".equals(string[number_x][number_y])) {//��֤�����ӵط�����
					//�ö�������洢ÿ���ӵ���Ϣ
					Shape shape = new Shape();
					shape.x = x;
					shape.y = y;
					//ȷ��������ɫ
					if (colorcount == 1) {
						color = Color.BLACK;
						g.setColor(color);
						shape.color = color;
						g.fillOval(x - 23, y - 23, 46, 46);
						string[number_x][number_y] = "1";//��ɫ���Ӽ�¼
						
						time1.setPause(true);
						if(t==0){
						time2.start();
						t++;
						}
						else if(t>=1){
						if(o>=3){
							 p =1;
							 o=1;
						}
						if(p==1){
							time2.reset();
							p++;
						}
						if(p==2){
						time2.setPause(false);
						}
						}
						colorcount++;
					} else if (colorcount == 2) {
						color = Color.WHITE;
						g.setColor(color);
						shape.color = color;
						g.fillOval(x - 23, y - 23, 46, 46);
						time1.setPause(false);
						time2.setPause(true);
						string[number_x][number_y] = "2";//��ɫ���Ӽ�¼
						colorcount = 1;
					}
					index++;
					shapes[index] = shape;
				} 
			}
			boolean u = ju.judge();//�ж��Ƿ������Ӯ
			if (u==true){
				colorcount=-2;
				time1.setPause(true);
				time2.setPause(true);
			}
		}
		//�˻��ķ���
		if ("�˻���ս".equals(str)) {
			//�������
			if (count == 0) {
				color = Color.BLACK;
				g.setColor(color);
				x1 = e.getX();
				y1 = e.getY();
				if (x1 < (COUNT - 1) * size + margin_x + size / 2 && y1 < (COUNT - 1) * size + margin_y + size / 2) {
					number_x = (x1 - margin_x) / size;
					if ((x1 - margin_x) % size < size / 2) {
						x = margin_x + number_x * size;
					} else {
						number_x++;
						x = margin_x + number_x * size;
					}
					number_y = (y1 - margin_y) / size;
					if ((y1 - margin_y) % size < size / 2) {
						y = margin_y + number_y * size;
					} else {
						number_y++;
						y = margin_y + number_y * size;
					}
					if ("0".equals(string[number_x][number_y])) {
						Shape shape = new Shape();
						shape.x = x;
						shape.y = y;
						shape.color = color;
						index++;
						shapes[index] = shape;
						g.setColor(color);
						g.fillOval(x - 23, y - 23, 46, 46);
						string[number_x][number_y] = "1";
						boolean u = ju.judge();//�ж��Ƿ������Ӯ
						if (u==true){
							count=-2;
						}
						number[number_x][number_y] = 0;//��֤���Ӻ��λ��Ȩֵ��Ӱ������Ȩֵ�Ƚ�
						count++;//��ת����������
					}
				}
				robotchess();//������Ӻ����ÿ��λ�õ�Ȩֵ
			}
			
			// �˻�����
			if (count == 1) {
				color = Color.WHITE;
				g.setColor(color);
				int number1, number2, maxnumber = 0;
				//�����Ƚϵó����Ȩֵλ��
				fg: for (int i = 0; i < 17; i++) {
					for (int j = 0; j < 17; j++) {
						if ("0".equals(string[i][j])) {//ֻ��������λ�õ�Ȩֵ
							number1 = number[i][j];
							maxnumber = number1;
							fd: for (int m = 0; m < 17; m++) {
								for (int n = 0; n < 17; n++) {
									number2 = number[m][n];
									if (number2 > number1) {
										maxnumber = number2;
										break fd;
									}
								}
							}
							//���Ȩֵ������
							if (maxnumber == number1) {
								x = margin_x + i * size;
								y = margin_y + j * size;
								g.fillOval(x - 23, y - 23, 46, 46);
								number[i][j] = 0;
								string[i][j] = "2";
								Shape shape = new Shape();
								shape.x = x;
								shape.y = y;
								shape.color = color;
								index++;
								shapes[index] = shape;
								break fg;
							}
						}
					}
				}
				boolean u = ju.judge();//�ж��Ƿ������Ӯ
				if (u==true){
					count=-2;
				}
				count = 0;
			}
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// ��Ȩֵ����ķ���
	public void robotchess() {
		//����hashmap
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		//���K-Vֵ
		map.put("021", 3);
		map.put("0110", 70);
		map.put("010", 10);
		map.put("012", 0);
		map.put("01110", 400);
		map.put("01112", 100);
		map.put("0220", 60);
		map.put("0221", 20);
		map.put("02220", 300);
		map.put("02221", 200);
		map.put("011112", 600);
		map.put("022220", 900);
		map.put("022221", 900);
		String str1;
		//����ÿ����
		for (int i = 0; i < string.length; i++) {
			for (int j = 0; j < string[i].length; j++) {
				if ("0".equals(string[i][j])) {
					int n = j;
					str1 = string[i][j];
					Integer num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0;
					//���ҷ����Ȩֵ
					for (int m = i; m < string.length; m++) {
						if (m < 16) {
							String str2 = string[m + 1][j];
							str1 = str1.concat(str2);
							if (m >= 0 && m >= i + 1) {
								if (str2 != string[m][j]) {
									num1 = map.get(str1);//hashmap�в���
									if (num1 == null) {
										num1 = 0;
									}
									break;
								}
							}
						}
					}
					//�������Ȩֵ
					str1 = string[i][j];
					for (int m = i; m >= 0; m--) {
						if (m > 0) {
							String str2 = string[m - 1][j];
							str1 = str1.concat(str2);
							if (m < 16 && m <= i - 1) {
								if (str2 != string[m][j]) {
									num2 = map.get(str1);
									if (num2 == null) {
										num2 = 0;
									}
									break;
								}
							}
						}
					}
					//���Ϸ����Ȩֵ
					str1 = string[i][j];
					for (int m = j; m < 17; m++) {

						if (m < 16) {
							String str2 = string[i][m + 1];
							str1 = str1.concat(str2);
							if (m >= 0 && m >= j + 1) {
								if (str2 != string[i][m]) {
									num3 = map.get(str1);
									if (num3 == null) {
										num3 = 0;
									}
									break;
								}
							}
						}
					}
					//���·����Ȩֵ
					str1 = string[i][j];
					for (int m = j; m >= 0; m--) {

						if (m > 0) {
							String str2 = string[i][m - 1];
							str1 = str1.concat(str2);
							if (m < 16 && m <= j - 1) {
								if (str2 != string[i][m]) {
									num4 = map.get(str1);
									if (num4 == null) {
										num4 = 0;
									}
									break;
								}
							}
						}
					}
					//������
					str1 = string[i][j];
					n = j;
					for (int m = i; m < string.length; m++) {
						n = n + 1;
						if (n < 17 && m < 16) {
							String str2 = string[m + 1][n];
							str1 = str1.concat(str2);
							if (m >= 0 && n >= 1 && m >= i + 1) {
								if (str2 != string[m][n - 1]) {
									num5 = map.get(str1);
									if (num5 == null) {
										num5 = 0;
									}
									break;
								}
							}
						}
					}
					//������
					str1 = string[i][j];
					n = j;
					for (int m = i; m < string.length; m++) {
						n = n - 1;
						if (n >= 0 && m < 16) {
							String str2 = string[m + 1][n];
							str1 = str1.concat(str2);
							if (m >= 1 && n < 17 && m >= i + 1) {
								if (str2 != string[m][n + 1]) {
									num6 = map.get(str1);
									if (num6 == null) {
										num6 = 0;
									}
									break;
								}
							}
						}
					}
					//������
					str1 = string[i][j];
					n = j;
					for (int m = i; m >= 0; m--) {
						n = n - 1;
						if (n >= 0 && m > 0) {
							String str2 = string[m - 1][n];
							str1 = str1.concat(str2);
							if (m < 16 && n < 16 && m <= i - 1) {
								if (str2 != string[m][n + 1]) {
									num7 = map.get(str1);
									if (num7 == null) {
										num7 = 0;
									}
									break;
								}
							}
						}
					}
					//������
					str1 = string[i][j];
					n = j;
					for (int m = i; m >= 0; m--) {
						n = n + 1;
						if (n < 17 && m > 0) {
							String str2 = string[m - 1][n];
							str1 = str1.concat(str2);
							if (m < 16 && n >= 1 && m <= i - 1) {
								if (str2 != string[m][n - 1]) {
									num8 = map.get(str1);
									if (num8 == null) {
										num8 = 0;
									}
									break;
								}
							}
						}
					}
					//�˸������Ȩֵ���
					int num = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8;
					number[i][j] = num;//Ȩֵ�������
				}
			}
		}
	}
}
