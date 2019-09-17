package day0512Wuziqi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;

public class UI implements WuziqiConfig{
	public void showui(){
		
		//����JFrame��������
		JFrame chessjf = new JFrame("������");
		
		//JFrame��������
		chessjf.setSize(1060, 920);
		chessjf.setDefaultCloseOperation(3);
		chessjf.setLocationRelativeTo(null);
		
		//���ò��ɸı��С
//		chessjf.setResizable(false);
		
		//��ť�����������Ĵ���
		JPanel buttonpanel = new JPanel();
		JPanel eastpanel = new JPanel();
		JPanel timepanel = new JPanel();
		MyChessJPanel chesspanel = new MyChessJPanel();
		
		//��ť����
		JButton button1 = new JButton("˫�˶�ս");
		JButton button2 = new JButton("�˻���ս");
		JButton button3 = new JButton("���¿�ʼ");
		JButton button4 = new JButton("����");
		JProgressBar bar = new JProgressBar();
//		JLabel text = new JLabel("ʱ��");
		JLabel timetext = new JLabel();
		
		//��塢��ť��С����
		Dimension btnsize = new Dimension(110,50);
		Dimension eplsize = new Dimension(180,920);
		Dimension barlsize = new Dimension(150,25);
		Dimension bplsize = new Dimension(180,500);
		Dimension tplsize = new Dimension(180,420);
		Dimension chessplsize = new Dimension(width,height);
		button1.setPreferredSize(btnsize);
		button2.setPreferredSize(btnsize);
		button3.setPreferredSize(btnsize);
		button4.setPreferredSize(btnsize);
		bar.setPreferredSize(barlsize);
		button1.setBackground(new Color(200,200,200));
		button2.setBackground(new Color(200,200,200));
		button3.setBackground(new Color(200,200,200));
		button4.setBackground(new Color(200,200,200));
		buttonpanel.setPreferredSize(bplsize);
		chesspanel.setPreferredSize(chessplsize);
		eastpanel.setPreferredSize(eplsize);
		timepanel.setPreferredSize(tplsize);
		
		//�ı����������С

		timetext.setFont(new Font(null, 0, 25));
		timetext.setText("ʱ�䣺 ");
		//bar������
		bar.setMinimum(0);
		bar.setMaximum(600);
		bar.setValue(600);
		bar.setForeground(new Color(70,70,70));
		bar.setBackground(new Color(188,188,188));
				
		//�����������ӱ�����ɫ
		chesspanel.setBackground(new Color(100,100,100));
		buttonpanel.setBackground(new Color(100,100,100));
		timepanel.setBackground(new Color(100,100,100));
		
		//�����Ӱ�ť��JFrame��������
		buttonpanel.add(button1);
		buttonpanel.add(button2);
		buttonpanel.add(button3);
		buttonpanel.add(button4);
//		timepanel.add(text);
		timepanel.add(timetext);
		timepanel.add(bar);
		
		eastpanel.add(buttonpanel);
		eastpanel.add(timepanel);
		
		FlowLayout f=(FlowLayout)buttonpanel.getLayout();
		f.setVgap(40);
		FlowLayout d=(FlowLayout)eastpanel.getLayout();
		d.setVgap(0);
		
		chessjf.add(chesspanel,BorderLayout.CENTER);
		chessjf.add(eastpanel,BorderLayout.EAST);
		
		//����JFrame�ɼ�
		chessjf.setVisible(true);
		
		Time time = new Time("fd");
		time.getbar(bar,timetext);
		
		//��û���g
		Graphics g = chesspanel.getGraphics();
		
		//��Ӽ�����
		ChessListener listener = new ChessListener();
		listener.setshapes(chesspanel,button1,button2,button3,button4,bar,timetext);
		listener.setg(g);
		chesspanel.addMouseListener(listener);
		button1.addActionListener(listener);
		button2.addActionListener(listener);
		button3.addActionListener(listener);
		button4.addActionListener(listener);
	}

	public static void main(String[] args){
		UI ui = new UI();
		ui.showui();
	}
}
