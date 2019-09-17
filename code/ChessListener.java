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
	//颜色计数和落子计数
	int colorcount = 1;
	int count = 0;
	int index = 0;

	//先手颜色
	Color color = Color.BLACK;
	Shape[] shapes = new Shape[10000];//重绘和悔棋需要的数据
	
	
	String[][] string = new String[17][17];//棋盘的记录
	int[][] number = new int[17][17];//权值数组

	// 传递画笔的方法
	Graphics g;
	public void setg(Graphics g) {
		this.g = g;
	}

	// 传递按钮，将保存了下过的棋子数组shape传递到repaint方法中
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
	
	Time time1 = new Time("白棋");
	Time time2 = new Time("黑棋");
	
	String str;
	int o =0;
	@Override
	public void actionPerformed(ActionEvent e) {
		// 悔棋的清除操作且保证接下来落子颜色的正确性
		if ("悔棋".equals(e.getActionCommand())) {
			if (color == Color.WHITE) {
				colorcount = 2;
			} else {
				colorcount = 1;
			}
			if ("双人对战".equals(str)) {
				string[(shapes[index].x - margin_x) / size][(shapes[index].y - margin_y) / size] = "0";
				shapes[index] = null;
				index--;
			} else if ("人机对战".equals(str)) {
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
			//重新开始要进行的数据清理
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
		//调用传递字符数组的方法
		Judge ju = new Judge();
		ju.setstring(string);
		if ("双人对战".equals(str)) {
			//获取坐标
			x1 = e.getX();
			y1 = e.getY();
			//判断在棋盘内，且得出落子位置格数
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
				if ("0".equals(string[number_x][number_y])) {//保证在无子地方落子
					//用对象数组存储每颗子的信息
					Shape shape = new Shape();
					shape.x = x;
					shape.y = y;
					//确定落子颜色
					if (colorcount == 1) {
						color = Color.BLACK;
						g.setColor(color);
						shape.color = color;
						g.fillOval(x - 23, y - 23, 46, 46);
						string[number_x][number_y] = "1";//黑色落子记录
						
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
						string[number_x][number_y] = "2";//白色落子记录
						colorcount = 1;
					}
					index++;
					shapes[index] = shape;
				} 
			}
			boolean u = ju.judge();//判断是否产生输赢
			if (u==true){
				colorcount=-2;
				time1.setPause(true);
				time2.setPause(true);
			}
		}
		//人机的方法
		if ("人机对战".equals(str)) {
			//玩家落子
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
						boolean u = ju.judge();//判断是否产生输赢
						if (u==true){
							count=-2;
						}
						number[number_x][number_y] = 0;//保证落子后的位置权值不影响后面的权值比较
						count++;//跳转到电脑落子
					}
				}
				robotchess();//玩家落子后分析每个位置的权值
			}
			
			// 人机落子
			if (count == 1) {
				color = Color.WHITE;
				g.setColor(color);
				int number1, number2, maxnumber = 0;
				//遍历比较得出最大权值位置
				fg: for (int i = 0; i < 17; i++) {
					for (int j = 0; j < 17; j++) {
						if ("0".equals(string[i][j])) {//只分析无子位置的权值
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
							//最大权值处落子
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
				boolean u = ju.judge();//判断是否产生输赢
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

	// 求权值数组的方法
	public void robotchess() {
		//创建hashmap
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		//添加K-V值
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
		//遍历每个点
		for (int i = 0; i < string.length; i++) {
			for (int j = 0; j < string[i].length; j++) {
				if ("0".equals(string[i][j])) {
					int n = j;
					str1 = string[i][j];
					Integer num1 = 0, num2 = 0, num3 = 0, num4 = 0, num5 = 0, num6 = 0, num7 = 0, num8 = 0;
					//向右方向的权值
					for (int m = i; m < string.length; m++) {
						if (m < 16) {
							String str2 = string[m + 1][j];
							str1 = str1.concat(str2);
							if (m >= 0 && m >= i + 1) {
								if (str2 != string[m][j]) {
									num1 = map.get(str1);//hashmap中查找
									if (num1 == null) {
										num1 = 0;
									}
									break;
								}
							}
						}
					}
					//向左方向的权值
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
					//向上方向的权值
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
					//向下方向的权值
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
					//向右下
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
					//向右上
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
					//向左上
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
					//向左下
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
					//八个方向的权值相加
					int num = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8;
					number[i][j] = num;//权值数组存入
				}
			}
		}
	}
}
