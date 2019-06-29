package day0512Wuziqi;

import java.util.HashMap;

import javax.swing.JOptionPane;

public class Judge {

	String[][] string;
	String str3, str4, str5, str6, str7, str8, str9, str10;

	// 传递String数组的方法
	public void setstring(String[][] string) {
		this.string = string;
	}

	// 判断方法
	public boolean judge() {
		boolean t= false;
		judge:
		// 遍历棋盘每个点
		for (int i = 0; i < string.length; i++) {
			for (int j = 0; j < string[i].length; j++) {
				str3 = string[i][j];
				str5 = string[i][j];
				str7 = string[i][j];
				str9 = string[i][j];
				// 判断向右的方向是否满足
				for (int m = i + 1; m < i + 5; m++) {
					if (i <= 12) {
						str4 = string[m][j];
						str3 = str3.concat(str4);
					}
				}
				// 判断向下的方向是否满足
				for (int m = j + 1; m < j + 5; m++) {
					if (j <= 12) {
						str6 = string[i][m];
						str5 = str5.concat(str6);
					}
				}
				int n = j;
				int b = j;
				// 判断向右下的方向
				for (int m = i + 1; m < i + 5; m++) {
					n = n + 1;
					if (i <= 12 && j <= 12) {
						str8 = string[m][n];
						str7 = str7.concat(str8);
					}
				}
				// 右上的方向
				for (int m = i + 1; m < i + 5; m++) {
					b = b - 1;
					if (i <= 12 && j >= 4) {
						str10 = string[m][b];
						str9 = str9.concat(str10);
					}
				}
				if ("11111".equals(str3) || "11111".equals(str5) || "11111".equals(str7) || "11111".equals(str9)) {
					t=true;
					JOptionPane.showMessageDialog(null, "黑棋胜");
					break judge;
				} else if ("22222".equals(str3) || "22222".equals(str5) || "22222".equals(str7)
						|| "22222".equals(str9)) {
					t=true;
					JOptionPane.showMessageDialog(null, "白棋胜");
					break judge;
				}
			}
		}
		return t;
	}
}
