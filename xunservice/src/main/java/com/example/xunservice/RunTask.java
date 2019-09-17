package com.example.xunservice;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Canvas;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Panel;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class RunTask  extends JFrame{

	private static JFrame taskFra;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RunTask window = new RunTask();
					window.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RunTask() {
		initialize();
	}

	/**
	 * Initialize the contents of the taskFra.
	 */
	private void initialize() {
		taskFra = new JFrame();
		taskFra.setTitle("新建任务");
		
		taskFra.getContentPane().setFont(new Font("Dialog", Font.PLAIN, 16));
		taskFra.getContentPane().setLayout(null);
		taskFra.setResizable(false);
		
		JLabel label = new JLabel("迅服务将根据你所键入的名称，为你打开相应的程序、文件或文档。");
		label.setFont(new Font("Dialog", Font.PLAIN, 18));
		label.setBackground(Color.WHITE);
		label.setBounds(94, 70, 542, 29);
		taskFra.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("打开:");
		label_1.setFont(new Font("Dialog", Font.BOLD, 16));
		label_1.setBounds(28, 150, 46, 29);
		taskFra.getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 16));
		textField.setBounds(90, 150, 481, 25);
		taskFra.getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 260, 660, 80);
		taskFra.getContentPane().add(panel);
		
		JButton button = new JButton("确定");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RunTask.chakan(textField.getText());
			}
		});
		button.setLocation(140, 25);
		button.setSize(100, 35);
		panel.add(button);
		button.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		JButton button_1 = new JButton("取消");
		button_1.setLocation(280, 25);
		button_1.setSize(100, 35);
		panel.add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
			}
		});
		button_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		JButton button_2 = new JButton("浏览");
		button_2.setLocation(420, 25);
		button_2.setSize(100, 35);
		panel.add(button_2);
		button_2.setFont(new Font("Dialog", Font.PLAIN, 16));
		button_2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser(".");
				
				if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {  
				     
				}  
			    File file=jfc.getSelectedFile();
			  if(file.isFile()){
			   
			      textField.setText(file.getAbsolutePath());
			  }
		   
			}

		});
		
	
		JLabel jLabel;
		//Icon icon=new ImageIcon(RunTask.class.getResource("/neww/com/wxy/logo.png"));
		System.out.println(RunTask.class);
		//ImageIcon icon=new ImageIcon(RunTask.class.getResource("/com/example/xuncode/logo.png"));
		ImageIcon icon=new ImageIcon(RunTask.class.getResource("/com/example/xunservice/logo.png"));
		icon.setImage(((ImageIcon) icon).getImage().getScaledInstance(180, 100,5));
		jLabel = new JLabel(("GuFeng"),icon,JLabel.CENTER);
		jLabel.setBounds(0, 0, 180, 100);
		//jLabel.setText(text);
		//can't show 

		//icon.paintIcon(jLabel, 0, jLabel.getWidth(), jLabel.getHeight());
		
		jLabel.setBackground(Color.blue);
		jLabel.setForeground(Color.BLACK);
		

		taskFra.getContentPane().add(jLabel);
		taskFra.setSize(660, 340);
		taskFra.setBounds(100, 100, 660, 340);
		//end size
		taskFra.setFont(new Font("Times New Roman", Font.BOLD, 22));
	}
/***查看文件属性***/
	
	public static void chakan(String filename) {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		  String quanxian="";
		try {
			/*
			 * 
			 * 自适应执行查询进程列表命令
			 * 
			 */
			Properties prop = System.getProperties();
			// 获取操作系统名称
			boolean is = false;
			String os = prop.getProperty("os.name");
			if (os != null && os.toLowerCase().indexOf("linux") > -1) {
				// 1.适应与linux

				BufferedReader reader = null;
				// 显示所有进程
				// "名称","PID","优先级","状态","用户名","cpu","内存","磁盘","网络","描述"
				process = Runtime.getRuntime().exec("ls -l "+filename);
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = null;

				while ((line = reader.readLine()) != null) {
					quanxian=line.substring(7,10);
					
				}

			}
		
		if(quanxian.indexOf("x")>=0) {
			RunTask.yunxing(filename);
		}
		else if(quanxian.indexOf("r")>=0) {
			RunTask.open(filename);
		}
		else {
			JOptionPane.showMessageDialog(null, "权限不够！" , "提示", JOptionPane.INFORMATION_MESSAGE);
		}

		} catch (Exception e) {

			e.printStackTrace();

		}
		
		
		
	}
	
		/***查看文件属性***/
	/***运行文件
	 * @return ***/
	
	public static void yunxing(String filename) {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		try {
			/*
			 * 
			 * 自适应执行查询进程列表命令
			 * 
			 */
			Properties prop = System.getProperties();
			// 获取操作系统名称
			boolean is = false;
			String os = prop.getProperty("os.name");
			if (os != null && os.toLowerCase().indexOf("linux") > -1) {
				// 1.适应与linux

				BufferedReader reader = null;
				// 显示所有进程
				process = Runtime.getRuntime().exec(filename);
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = null;


			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		

	}
	
	/***运行文件***/
	
	/***用gedit打开文件***/
	public static void open(String filename) {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		try {
			/*
			 * 
			 * 自适应执行查询进程列表命令
			 * 
			 */
			Properties prop = System.getProperties();
			// 获取操作系统名称
			boolean is = false;
			String os = prop.getProperty("os.name");
			if (os != null && os.toLowerCase().indexOf("linux") > -1) {
				// 1.适应与linux

				BufferedReader reader = null;
				// 显示所有进程
				process = Runtime.getRuntime().exec("gedit  "+filename);
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = null;
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		

	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		taskFra.setVisible(true);
		
	}
	
}
