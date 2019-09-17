package com.example.xunservice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.swing.DefaultLookup;
import com.example.xuncode.Net;
import com.example.xuncode.NetStat;
import com.example.xuncode.application;
import com.example.xuncode.process;
import com.example.xuncode.processModel;
import com.example.xuncode.processPropertiesApp;
import com.example.xunservice.RunTask;;
@SpringBootApplication
public class XunserviceApplication extends JFrame {
			private static int rate = 10000;
			private JFrame frame;
			private JTable tableNet;
			private JTable tableProcess;
			private JTable tableApplication;
			private JTabbedPane tabbedPane;
			private JPanel panelProcess;
			private JPanel panelApplication;
			private JPanel panelNet;
			static boolean isbai = true;
			static boolean isUpdate = true;// 是否更新：当鼠标左键按下后不再更新进程，当过去5秒后继续更新
			static int focusedRowIndex = -1;// table mouse row number
			static JPopupMenu m_popupMenuProcess;// 弹出式菜单、右键菜单
			static JPopupMenu m_popupMenuApplication;// 弹出式菜单、右键菜单
			static JPopupMenu m_popupMenuNet;// 弹出式菜单、右键菜单
			static XunserviceApplication  window = new XunserviceApplication ();
			static List<processModel> processs;
			static List<processModel> applications;
			static List<Net> nets;
			

			/**
			 * Launch the application.
			 */
			public static void main(String[] args) {
				SpringApplication.run(XunserviceApplication.class, args);
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							window.frame.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int count = 0;
				while(true) {
				System.out.println(isUpdate);
			    if(isUpdate==true)
				{
					try {
						Thread.sleep(rate);
						// 设置暂停的时间 5 秒
						count++;
						window.updateNetTable();
					
						window.updateProcessTable();
					  
						window.updateApplicationTable();
					
						System.out.println(sdf.format(new Date()) + "--循环执行第" + count + "次");

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					

				}
				}
			
			}

			/**
			 * Create the application.
			 */
			public XunserviceApplication () {
				initialize();
			}

			/**
			 * Initialize the contents of the frame.
			 */
			private void initialize() {
				frame = new JFrame("迅服务");
				frame.setFont(new Font("Times New Roman", Font.BOLD, 22));

				// width and height
				frame.getContentPane().setBounds(new Rectangle(0, 0, 1300, 800));
				frame.setSize(1300, 800);
				frame.setBounds(100, 100, 1300, 800);
				// end size
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);
			//	frame.setResizable(false);
				Container contentPane = frame.getContentPane();
				JMenuBar menuBar = new JMenuBar();
				menuBar.setToolTipText("1 2 3 ");
				menuBar.setFont(new Font("Dialog", Font.BOLD, 16));
				menuBar.setBounds(0, 0, 1300, 22);
				JMenu[] menus = new JMenu[] { new JMenu("文件"), new JMenu("查看") };
				for (int i = 0; i < menus.length; i++) {
					menus[i].setFont(new Font("Dialog", Font.PLAIN, 16));
					menuBar.add(menus[i]);
				}
				JMenuItem item1 = new JMenuItem("     运行新任务    ");
				item1.setFont(new Font("Dialog", Font.PLAIN, 16));
				/**** 运行新任务 ***/
				item1.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent evt) {

						RunTask window = new RunTask();
						//taskFra.setVisible(true);
						window.setVisible(true);

					}

				});
				/**** 运行新任务 ***/

				JMenuItem item2 = new JMenuItem("     退出   ");
				item2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});

				item2.setFont(new Font("Dialog", Font.BOLD, 16));
				menus[0].add(item1);
				menus[0].add(item2);

				JMenuItem itemOn = new JMenuItem("     立刻刷新    ");
				itemOn.setFont(new Font("Dialog", Font.PLAIN, 16));
				itemOn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						updateNetTable();
						updateProcessTable();
						updateApplicationTable();
					}
				});
				JMenu itemRate = new JMenu("    速度   ");
				ButtonGroup buttonGroup = new ButtonGroup();
				JRadioButtonMenuItem itemHigh = new JRadioButtonMenuItem("      高  ", false);
				itemHigh.setSize(20, 20);
				itemHigh.setFont(new Font("Dialog", Font.PLAIN, 16));
				JRadioButtonMenuItem itemMid = new JRadioButtonMenuItem("      中   ", true);
				itemMid.setFont(new Font("Dialog", Font.PLAIN, 16));
				JRadioButtonMenuItem itemLow = new JRadioButtonMenuItem("      低    ", false);
				itemLow.setFont(new Font("Dialog", Font.PLAIN, 16));
				JRadioButtonMenuItem itemStop = new JRadioButtonMenuItem("    已暂停    ", false);
				itemStop.setFont(new Font("Dialog", Font.PLAIN, 16));
				buttonGroup.add(itemHigh);
				buttonGroup.add(itemStop);
				buttonGroup.add(itemMid);
				buttonGroup.add(itemLow);
				itemRate.add(itemHigh);
				itemRate.add(itemMid);
				itemRate.add(itemLow);
				itemRate.add(itemStop);
				itemHigh.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						isUpdate = true;
						rate = 9000;
					}
				});
				itemMid.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						isUpdate =true;
						rate = 10000;
					}
				});
				itemLow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						isUpdate = true;
						rate = 1100;
					}
				});
				itemStop.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {						
							isUpdate = false;
					}
				});
				itemRate.setFont(new Font("Dialog", Font.BOLD, 16));
				menus[1].add(itemOn);
				menus[1].add(itemRate);

				frame.getContentPane().add(menuBar);

				tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				tabbedPane.setBounds(0, 23, frame.getWidth(), frame.getHeight());

				// 23
				tabbedPane.setFont(new Font("Dialog", Font.PLAIN, 14));
				frame.getContentPane().add(tabbedPane);
				// add label
				JLabel lblNewLabel = new JLabel("1");
				// add panel

				JPanel panel1 = new JPanel(new BorderLayout());
				panel1.setFont(new Font("Dialog", Font.PLAIN, 14));
				// 表头（列名）
				/**** 进程 **/
				panelProcess = new JPanel(new BorderLayout());
				panelProcess.setFont(new Font("Dialog", Font.PLAIN, 14));
				// 表头（列名）
				String[] ProcesscolumnNames = { "", "名称", "PID", "优先级", "状态", "用户名", "cpu", "内存" };

				// 创建一个表格，指定所有行数据 和 表头
				DefaultTableModel model1 = new DefaultTableModel(ProcesscolumnNames, 0);
				tableProcess = new JTable(model1);
				tableProcess.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				TableModel tableModel1 = tableProcess.getModel();
				tableProcess.getColumnModel().getColumn(0).setCellRenderer(new TableImageCell());
				// "名称","PID","优先级","状态","用户名","cpu","内存","描述"
				processs = new ArrayList<processModel>();
				processs = process.Getprocess();
				int i = 0;

				for (processModel p : processs) {

					Object[] arr = new Object[8];
					ImageIcon icon = new ImageIcon(findPicturePath(p.getName()));
					icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
					arr[0] = icon;
					arr[1] = p.getName();
					arr[2] = p.getPID();
					arr[3] = p.getPrio();
					arr[4] = p.getState();
					arr[5] = p.getUser();
					arr[6] = Double.toString(p.getB_cpu()) + "%";
					if (isbai == true) {
						arr[7] = Double.toString(p.getB_memory()) + "%";
					} else {
						arr[7] = p.getMemory();
					}

					i++;
					// 添加数据到表格
					((DefaultTableModel) tableModel1).addRow(arr);

				}
				tableProcess.getColumnModel().getColumn(0).setCellRenderer(new TableImageCell());
				tableProcess.setEnabled(false);
				tableProcess.setShowGrid(false);
				tableProcess.setRowHeight(22);// 设置表格行宽
				tableProcess.invalidate();
				// JTable对象添加点击事件
				tableProcess.addMouseListener(new java.awt.event.MouseAdapter() {

					public void mouseClicked(java.awt.event.MouseEvent evt) {
						
						jTable1MouseClicked(evt);

					}

				});

				JTableHeader head1 = tableProcess.getTableHeader(); // 创建表格标题对象
				head1.setFont(new Font("楷体", Font.PLAIN, 16));// 设置表格字体

				tableProcess.invalidate();

				tableProcess.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				// 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
				panelProcess.add(tableProcess.getTableHeader(), BorderLayout.NORTH);
				// 把表格内容 添加到容器中心
				panelProcess.add(tableProcess, BorderLayout.CENTER);

				JScrollPane scrollPane = new JScrollPane(panelProcess);
				contentPane.add(scrollPane, BorderLayout.CENTER);

				tabbedPane.addTab("进程", null, scrollPane, null);
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model1);
				tableProcess.setRowSorter(sorter);
				ProcesscreatePopupMenu();// 创建鼠标点击事件
				/**** 进程 **/
				JLabel lblNewLabel_4 = new JLabel("性能");
				tabbedPane.addTab("性能", null, lblNewLabel_4, null);
				/**** 应用 **/
				panelApplication = new JPanel(new BorderLayout());
				panelApplication.setFont(new Font("Dialog", Font.PLAIN, 14));
				// 表头（列名）
				String[] ApplicationcolumnNames = { "", "名称", "PID", "优先级", "状态", "用户名", "cpu", "内存" };

				// 创建一个表格，指定所有行数据 和 表头
				DefaultTableModel model3 = new DefaultTableModel(ApplicationcolumnNames, 0);
				tableApplication = new JTable(model3);
				tableApplication.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				tableApplication.setEnabled(false);
				TableModel tableModel3 = tableApplication.getModel();
				// "名称","PID","优先级","状态","用户名","cpu","内存","描述"
				applications = new ArrayList<processModel>();
				applications = application.readFiles();
				//tableApplication.getColumnModel().getColumn(0).setCellRenderer(new TableLableCell());
				for (processModel p : applications) {
					Object[] arr = new Object[8];
					ImageIcon icon = new ImageIcon(findPicturePath(p.getName()));
					icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
					JLabel jLabel = new JLabel((p.getName()),icon,JLabel.CENTER);
					jLabel.setText(p.getName());
					//arr[0] = jLabel;
					arr[0]=icon;
					arr[1] = p.getName();
					arr[2] = p.getPID();
					arr[3] = p.getPrio();
					arr[4] = p.getState();
					arr[5] = p.getUser();
					arr[6] = Double.toString(p.getB_cpu()) + "%";
					if (isbai == true) {
						arr[7] = Double.toString(p.getB_memory()) + "%";
					} else {
						arr[7] = p.getMemory();
					}

					i++;
					// 添加数据到表格
					((DefaultTableModel) tableModel3).addRow(arr);

				}
		/*
		 * TableColumnModel tcm = tableApplication.getColumnModel(); TableColumn tc =
		 * tcm.getColumn(0); tc.setCellRenderer(new DefaultTableCellRenderer());
		 */
			     tableApplication.getColumnModel().getColumn(0).setCellRenderer(new TableImageCell());
					

				// JTable对象添加点击事件
				tableApplication.addMouseListener(new java.awt.event.MouseAdapter() {

					public void mouseClicked(java.awt.event.MouseEvent evt) {
					
						ApplicationjTable1MouseClicked(evt);
					}

				});

				JTableHeader head3 = tableApplication.getTableHeader(); // 创建表格标题对象
				head3.setFont(new Font("楷体", Font.PLAIN, 16));// 设置表格字体
				tableApplication.setShowGrid(false);
				tableApplication.setRowHeight(22);// 设置表格行宽
				tableApplication.invalidate();

				tableApplication.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				// 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
				panelApplication.add(tableApplication.getTableHeader(), BorderLayout.NORTH);
				panelApplication.add(new JScrollPane(tableApplication));
				// 把表格内容 添加到容器中心
				panelApplication.add(tableApplication, BorderLayout.CENTER);
				JScrollPane scrollApp = new JScrollPane(panelApplication);
				contentPane.add(scrollApp, BorderLayout.CENTER);
				tabbedPane.addTab("应用", null, scrollApp, null);

				RowSorter<TableModel> sorter3 = new TableRowSorter<TableModel>(model3);
				tableApplication.setRowSorter(sorter3);

				ApplicationcreatePopupMenu();// 创建鼠标点击事件
				/**** 应用 **/
				panelNet = new JPanel(new BorderLayout());
				// addNetPanel();

				// 联网

				panelNet.setFont(new Font("Dialog", Font.PLAIN, 14));
				// 表头（列名）
				String[] NetcolumnNames = { "","PID", "进程名", "发送量", "接受量", "本地地址" };

				// 创建一个表格，指定所有行数据 和 表头
				DefaultTableModel model2 = new DefaultTableModel(NetcolumnNames, 0);
				tableNet = new JTable(model2);
				tableNet.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				tableNet.setShowGrid(false);
				tableNet.setRowHeight(20);// 设置表格行宽
				TableModel tableModel2 = tableNet.getModel();
				tableNet.getColumnModel().getColumn(0).setCellRenderer(new TableImageCell());
				nets = NetStat.getInstance().getNetStat();
				for (Net member : nets) {
					Object[] arr = new Object[6];
					ImageIcon icon = new ImageIcon(findPicturePath(member.getNetName()));
					icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
					arr[0] = icon;
					arr[1] = member.getNetPid();
					arr[2] = member.getNetName();
					arr[3] = member.getReceive();
					arr[4] = member.getSend();
					arr[5] = member.getHost();
					((DefaultTableModel) tableModel2).addRow(arr);
					// 添加数据到表格
				}
				
				// JTable对象添加点击事件
				tableNet.addMouseListener(new java.awt.event.MouseAdapter() {

							public void mouseClicked(java.awt.event.MouseEvent evt) {
					
								NetjTable1MouseClicked(evt);

							}

						});

				// 更新表格
				JTableHeader head = tableNet.getTableHeader(); // 创建表格标题对象
				head.setFont(new Font("楷体", Font.PLAIN, 16));// 设置表格字体
				RowSorter<TableModel> sorterNet = new TableRowSorter<TableModel>(model2);
				tableNet.setRowSorter(sorterNet);

				tableNet.invalidate();
				tableNet.setEnabled(false);
				tableNet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
				// 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
				// add scroll
				// 产生一个带滚动条的面板
				// 将带滚动条的面板添加入窗口中

				JScrollPane scrollNet = new JScrollPane(panelNet);
				contentPane.add(scrollNet, BorderLayout.CENTER);
				panelNet.add(tableNet.getTableHeader(), BorderLayout.NORTH);
				// 把表格内容 添加到容器中心
				panelNet.add(tableNet, BorderLayout.CENTER);
				tabbedPane.addTab("联网", null, scrollNet, null);
				NetcreatePopupMenu();// 创建鼠标点击事件
			}
			/***联网**/
			// table点击事件

				private void NetjTable1MouseClicked(java.awt.event.MouseEvent evt) {
					
					NetmouseRightButtonClick(evt);

				}

				// 鼠标右键点击事件

				private void NetmouseRightButtonClick(java.awt.event.MouseEvent evt) {
				

					if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
						
					}
					// 判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键

					else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {

						// 通过点击位置找到点击为表格中的行

						focusedRowIndex = tableNet.rowAtPoint(evt.getPoint());
						
						if (focusedRowIndex == -1) {

							// System.out.println(focusedRowIndex);

						}

						// 将表格所选项设为当前右键点击的行

						tableNet.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);

						// 弹出菜单
						m_popupMenuNet.show(tableNet, evt.getX(), evt.getY());

					}

				}

				


				// 创建鼠标点击事件
				private static void NetcreatePopupMenu() {

					m_popupMenuNet = new JPopupMenu();
					m_popupMenuNet.setFont(new Font("Dialog", Font.PLAIN, 16));

					JMenuItem MenuItem0 = new JMenuItem();
					MenuItem0.setFont(new Font("Dialog", Font.PLAIN, 16));
					MenuItem0.setText("  结束任务  ");
					
					JMenuItem MenuItem6 = new JMenuItem();
					MenuItem6.setText("  属性  ");
					MenuItem6.setFont(new Font("Dialog", Font.PLAIN, 16));
					m_popupMenuNet.add(MenuItem0);
					
					m_popupMenuNet.add(MenuItem6);

					MenuItem0.addActionListener(new java.awt.event.ActionListener() {// 结束进程

						public void actionPerformed(java.awt.event.ActionEvent evt) {
							isUpdate = false;
							// 该操作需要做的事
							String pid = nets.get(focusedRowIndex).getNetPid();
							application.Killprocess(pid);
							window.updateNetTable();
							isUpdate = true;// 操作完成，继续更新

						}
					});

					
					MenuItem6.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent evt) {
							isUpdate = false;
							// 该操作需要做的事
							String pid = nets.get(focusedRowIndex).getNetPid();
							String name = nets.get(focusedRowIndex).getNetName();

							JFrame properties = new processPropertiesApp(name, pid);
							properties.setVisible(true);// 设置界面为可以显示

							isUpdate = true;// 操作完成，继续更新

						}

					});

				}

			
			public void updateNetTable() {
				TableModel tableModel = tableNet.getModel();
				int rows = tableNet.getRowCount();// get table rows

				while (rows >= 1) {// if update not update finish,continue delete rows
					((DefaultTableModel) tableModel).removeRow(0);// rowIndex是要删除的行序号
					rows = tableNet.getRowCount();// get table rows
				}
				 nets = NetStat.getInstance().getNetStat();
				System.out.println(nets.size());
				for (Net member : nets) {
					Object[] arr = new Object[6];
					ImageIcon icon = new ImageIcon(findPicturePath(member.getNetName()));
					icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
					arr[0] = icon;
					arr[1] = member.getNetPid();
					arr[2] = member.getNetName();
					arr[3] = member.getReceive();
					arr[4] = member.getSend();
					arr[5] = member.getHost();
					((DefaultTableModel) tableModel).addRow(arr);
					// 添加数据到表格
				}
				// 更新表格
				tableNet.invalidate();
			}

			/***联网**/
			/*** 进程 ***/
			public void updateProcessTable() {
				TableModel tableModel = tableProcess.getModel();
				int rows = tableProcess.getRowCount();// get table rows

				while (rows >= 1) {// if update not update finish,continue delete rows
					((DefaultTableModel) tableModel).removeRow(0);// rowIndex是要删除的行序号
					rows = tableProcess.getRowCount();// get table rows
				}
				processs = process.Getprocess();
				for (processModel p : processs) {

					Object[] arr = new Object[8];
					ImageIcon icon = new ImageIcon(findPicturePath(p.getName()));
					icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
					arr[0] = icon;
					arr[1]=p.getName();
					arr[2] = p.getPID();
					arr[3] = p.getPrio();
					arr[4] = p.getState();
					arr[5] = p.getUser();
					arr[6] = Double.toString(p.getB_cpu()) + "%";
					if (isbai == true) {
						arr[7] = Double.toString(p.getB_memory()) + "%";
					} else {
						arr[7] = p.getMemory();
					}
//					arr[1] = p.getName();

					// 添加数据到表格
					((DefaultTableModel) tableModel).addRow(arr);

				}

				// 更新表格
				tableProcess.invalidate();
			}
			// table点击事件

			private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

				mouseRightButtonClick(evt);

			}

			// 鼠标右键点击事件

			private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
			
				if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
					
				}
				// 判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键

				else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {

					// 通过点击位置找到点击为表格中的行

					focusedRowIndex = tableProcess.rowAtPoint(evt.getPoint());
					System.out.println("395:" + focusedRowIndex);

					if (focusedRowIndex == -1) {

						// System.out.println(focusedRowIndex);

					}

					// 将表格所选项设为当前右键点击的行

					tableProcess.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);

					// 弹出菜单
					m_popupMenuProcess.show(tableProcess, evt.getX(), evt.getY());

				}

			}

			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					
				}
				if (e.getButton() == MouseEvent.BUTTON3) {
					int selIndex = tableProcess.rowAtPoint(e.getPoint());

				}
			}

			// 创建鼠标点击事件
			private static void ProcesscreatePopupMenu() {

				m_popupMenuProcess = new JPopupMenu();
				m_popupMenuProcess.setFont(new Font("Dialog", Font.PLAIN, 16));

				JMenuItem MenuItem0 = new JMenuItem();
				MenuItem0.setText("  结束进程  ");
				MenuItem0.setFont(new Font("Dialog", Font.PLAIN, 16));
				JMenuItem MenuItem1 = new JMenuItem();
				MenuItem1.setText("  结束进程树  ");
				MenuItem1.setFont(new Font("Dialog", Font.PLAIN, 16));
				JMenu MenuItem2 = new JMenu();

				/*** 设置优先级 ***/
				MenuItem2.setText("  设置优先级  ");
				// Linux系统进程的优先级取值：-20 到 19，数越大优先级越低。
				MenuItem2.setFont(new Font("Dialog", Font.PLAIN, 16));
				JMenuItem m3 = new JMenuItem();
				m3.setFont(new Font("Dialog", Font.PLAIN, 16));
				JMenuItem m4 = new JMenuItem();
				m4.setFont(new Font("Dialog", Font.PLAIN, 14));
				JMenuItem m5 = new JMenuItem();
				m5.setFont(new Font("Dialog", Font.PLAIN, 16));
				JLabel showVal = new JLabel();
				showVal.setFont(new Font("Dialog", Font.PLAIN, 14));
				// 定义一个监听器，用于监听所有滑动条
				ChangeListener listener;
				listener = new ChangeListener() {
					public void stateChanged(ChangeEvent event) {
						isUpdate = false;// 操作完成，继续更新
						// 取出滑动条的值，并在文本中显示出来
						JSlider source = (JSlider) event.getSource();
						String prio = processs.get(focusedRowIndex).getPrio();
						source.setValue(Integer.valueOf(prio));
						showVal.setText("当前的值为：" + source.getValue());
						String pid = processs.get(focusedRowIndex).getPID();
						process.updatePrio(pid, source.getValue());
						isUpdate = true;// 操作完成，继续更新
					}
				};

				JSlider slider = new JSlider(-20, 19);
				// 设置绘制刻度
				slider.setPaintTicks(true);
				// 设置主、次刻度的间距
				slider.setMajorTickSpacing(5);
				slider.setMinorTickSpacing(1);
				// 设置绘制刻度标签，默认绘制数值刻度标签
				slider.setPaintLabels(true);

				slider.addChangeListener(listener);
				JLabel t = new JLabel("值越大，优先级越低，值越小，优先级越大");
				t.setFont(new Font("Dialog", Font.PLAIN, 14));
				m3.add(t);
				m4.add(slider);
				m4.setPreferredSize(new Dimension(340, 70));
				showVal.setText("当前的值为：");

				showVal.setVisible(true);
				m5.add(showVal);
				MenuItem2.add(m3);
				MenuItem2.add(m4);
				MenuItem2.add(m5);

				/*** 设置优先级 end ***/
				/*** 设置设置资源 ***/
				JRadioButtonMenuItem j1 = new JRadioButtonMenuItem();
				j1.setText("值");
				j1.setFont(new Font("Dialog", Font.PLAIN, 16));
				JRadioButtonMenuItem j2 = new JRadioButtonMenuItem();
				j2.setText("百分比");
				j2.setFont(new Font("Dialog", Font.PLAIN, 16));
				if (isbai == true) {
					j2.setSelected(true);
				} else {
					j1.setSelected(true);
				}
				// 定义一个监听器，用于监听所有滑动条
				ChangeListener listener2;
				listener2 = new ChangeListener() {
					public void stateChanged(ChangeEvent event) {
						isUpdate = false;
						// 取出滑动条的值，并在文本中显示出来

						if (event.getSource() == j1) {
							j2.setSelected(false);
							isbai = false;
							window.updateProcessTable();
							isUpdate = true;// 操作完成，继续更新

						} else {
							j1.setSelected(false);
							isbai = true;
							window.updateProcessTable();
							isUpdate = true;// 操作完成，继续更新

						}
					}

				};
				JMenu MenuItem3 = new JMenu("  内存  ");
				MenuItem3.setFont(new Font("Dialog", Font.PLAIN, 16));
				MenuItem3.add(j1);
				MenuItem3.add(j2);
				j1.addChangeListener(listener2);
				j2.addChangeListener(listener2);
				/*** 设置设置资源 end ***/
				JMenuItem MenuItem5 = new JMenuItem();
				MenuItem5.setText("  打开文件所在位置  ");
				MenuItem5.setFont(new Font("Dialog", Font.PLAIN, 16));
				JMenuItem MenuItem6 = new JMenuItem();
				MenuItem6.setText("  属性  ");
				MenuItem6.setFont(new Font("Dialog", Font.PLAIN, 16));
				m_popupMenuProcess.add(MenuItem0);
				m_popupMenuProcess.add(MenuItem1);
				m_popupMenuProcess.add(MenuItem2);
				m_popupMenuProcess.add(MenuItem3);
				m_popupMenuProcess.add(MenuItem5);
				m_popupMenuProcess.add(MenuItem6);

				MenuItem0.addActionListener(new java.awt.event.ActionListener() {// 结束进程

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						System.out.println(processs.size() + "    " + focusedRowIndex);
						// 该操作需要做的事
						String pid = processs.get(focusedRowIndex).getPID();
						process.Killprocess(pid);
						window.updateProcessTable();
						isUpdate = true;// 操作完成，继续更新

					}

				});
				MenuItem1.addActionListener(new java.awt.event.ActionListener() {// 结束进程树

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						// 该操作需要做的事
						String pid = processs.get(focusedRowIndex).getPID();
						process.killProcessTree(pid);
						window.updateProcessTable();
						isUpdate = true;// 操作完成，继续更新

					}

				});
				MenuItem2.addActionListener(new java.awt.event.ActionListener() {// 结束进程树

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						// 该操作需要做的事
						String prio = processs.get(focusedRowIndex).getPrio();
						slider.setValue(Integer.valueOf(prio));
						window.updateProcessTable();
						isUpdate = true;// 操作完成，继续更新

					}

				});

				MenuItem5.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						// 该操作需要做的事
						String pid = processs.get(focusedRowIndex).getPID();
						String path = process.findExePath(pid);
						System.out.println(path);
						File file = new File(path);

						try {
							java.awt.Desktop.getDesktop().open(file.getParentFile());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						window.updateProcessTable();
						isUpdate = true;// 操作完成，继续更新

					}

				});

				MenuItem6.addActionListener(new java.awt.event.ActionListener() {// 结束进程树

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						// 该操作需要做的事
						String pid = processs.get(focusedRowIndex).getPID();
						String name = processs.get(focusedRowIndex).getName();

						JFrame properties = new processPropertiesApp(name, pid);
						properties.setVisible(true);// 设置界面为可以显示

						isUpdate = true;// 操作完成，继续更新

					}

				});

			}

			/*** 进程 ****/

			/**** 应用 *****/

			// table点击事件

			private void ApplicationjTable1MouseClicked(java.awt.event.MouseEvent evt) {

				ApplicationmouseRightButtonClick(evt);

			}

			// 鼠标右键点击事件

			private void ApplicationmouseRightButtonClick(java.awt.event.MouseEvent evt) {
				
				// 判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
				if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
					
				}
				else if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {

					// 通过点击位置找到点击为表格中的行

					focusedRowIndex = tableApplication.rowAtPoint(evt.getPoint());
					System.out.println("653:" + focusedRowIndex);

					if (focusedRowIndex == -1) {

						// System.out.println(focusedRowIndex);

					}

					// 将表格所选项设为当前右键点击的行

					tableApplication.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);

					// 弹出菜单
					m_popupMenuApplication.show(tableApplication, evt.getX(), evt.getY());

				}

			}

			public void updateApplicationTable() {
				TableModel tableModel = tableApplication.getModel();
				int rows = tableApplication.getRowCount();// get table rows

				while (rows >= 1) {// if update not update finish,continue delete rows
					((DefaultTableModel) tableModel).removeRow(0);// rowIndex是要删除的行序号
					rows = tableApplication.getRowCount();// get table rows
				}
				processs = application.readFiles();

				for (processModel p : processs) {
					Object[] arr = new Object[8];
					ImageIcon icon = new ImageIcon(findPicturePath(p.getName()));
					icon.setImage(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
					arr[0] = icon;
					arr[1] = p.getName();
					arr[2] = p.getPID();
					arr[3] = p.getPrio();
					arr[4] = p.getState();
					arr[5] = p.getUser();
					arr[6] = Double.toString(p.getB_cpu()) + "%";
					if (isbai == true) {
						arr[7] = Double.toString(p.getB_memory()) + "%";
					} else {
						arr[7] = p.getMemory();
					}

					// 添加数据到表格
					((DefaultTableModel) tableModel).addRow(arr);

				}
				// 更新表格
				tableApplication.invalidate();
			}

			// 创建鼠标点击事件
			private static void ApplicationcreatePopupMenu() {

				m_popupMenuApplication = new JPopupMenu();
				m_popupMenuApplication.setFont(new Font("Dialog", Font.PLAIN, 16));

				JMenuItem MenuItem0 = new JMenuItem();
				MenuItem0.setFont(new Font("Dialog", Font.PLAIN, 16));
				MenuItem0.setText("  结束任务  ");
				/*** 设置设置资源 ***/
				JRadioButtonMenuItem j1 = new JRadioButtonMenuItem();
				j1.setFont(new Font("Dialog", Font.PLAIN, 16));
				j1.setText("值");
				JRadioButtonMenuItem j2 = new JRadioButtonMenuItem();
				j2.setFont(new Font("Dialog", Font.PLAIN, 16));
				j2.setText("百分比");
				if (isbai == true) {
					j2.setSelected(true);
				} else {
					j1.setSelected(true);
				}
				// 定义一个监听器，用于监听所有滑动条
				ChangeListener listener2;
				listener2 = new ChangeListener() {
					public void stateChanged(ChangeEvent event) {
						isUpdate = false;
						// 取出滑动条的值，并在文本中显示出来

						if (event.getSource() == j1) {
							j2.setSelected(false);
							isbai = false;
							window.updateApplicationTable();
							isUpdate = true;// 操作完成，继续更新

						} else {
							j1.setSelected(false);
							isbai = true;
							window.updateApplicationTable();
							isUpdate = true;// 操作完成，继续更新

						}
					}

				};
				JMenu MenuItem3 = new JMenu("  内存  ");
				MenuItem3.setFont(new Font("Dialog", Font.PLAIN, 16));

				MenuItem3.add(j1);
				MenuItem3.add(j2);
				j1.addChangeListener(listener2);
				j2.addChangeListener(listener2);
				/*** 设置设置资源 end ***/
				JMenuItem MenuItem5 = new JMenuItem();
				MenuItem5.setText("  打开文件所在位置  ");
				MenuItem5.setFont(new Font("Dialog", Font.PLAIN, 16));
				JMenuItem MenuItem6 = new JMenuItem();
				MenuItem6.setText("  属性  ");
				MenuItem6.setFont(new Font("Dialog", Font.PLAIN, 16));
				m_popupMenuApplication.add(MenuItem0);
				m_popupMenuApplication.add(MenuItem3);
				m_popupMenuApplication.add(MenuItem5);
				m_popupMenuApplication.add(MenuItem6);

				MenuItem0.addActionListener(new java.awt.event.ActionListener() {// 结束进程

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						// 该操作需要做的事
						String pid = processs.get(focusedRowIndex).getPID();
						application.Killprocess(pid);
						window.updateApplicationTable();
						isUpdate = true;// 操作完成，继续更新

					}
				});

				MenuItem5.addActionListener(new java.awt.event.ActionListener() {// 结束进程树

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						// 该操作需要做的事
						String pid = processs.get(focusedRowIndex).getPID();
						String path = application.findExePath(pid);
						System.out.println(path);
						File file = new File(path);

						try {
							java.awt.Desktop.getDesktop().open(file.getParentFile());
						} catch (IOException e) {
							window.updateApplicationTable();
							isUpdate = true;// 操作完成，继续更新

						}
					}
				});

				MenuItem6.addActionListener(new java.awt.event.ActionListener() {

					public void actionPerformed(java.awt.event.ActionEvent evt) {
						isUpdate = false;
						// 该操作需要做的事
						String pid = processs.get(focusedRowIndex).getPID();
						String name = processs.get(focusedRowIndex).getName();

						JFrame properties = new processPropertiesApp(name, pid);
						properties.setVisible(true);// 设置界面为可以显示

						isUpdate = true;// 操作完成，继续更新

					}

				});

			}

			/**** 应用 *****/

			/**** 根据进程名称找出图片路径 ***/
			public static String findPicturePath(String name) {
				Runtime runtime = Runtime.getRuntime();
				List<String> tasklist = new ArrayList<String>();
				java.lang.Process process = null;
				List<processModel> processs = new ArrayList<processModel>();
				String path = "";
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
						process = Runtime.getRuntime().exec("locate /usr/share/app-install/icons/" + name + ".png");
						reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
						String line = null;

						while ((line = reader.readLine()) != null) {

							path = line;

						}

						if (path.equals("")) {
							path = "/usr/share/app-install/icons/applications-other.png";
						}

					}

				} catch (Exception e) {

					e.printStackTrace();

				}

				return path;

			}

			/**** 根据进程名称找出图片路径 ***/

		}

		class TableImageCell extends DefaultTableCellRenderer {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				setIcon(null);
				setBorder(null);
				if (value instanceof ImageIcon) {
					setIcon((Icon) value);
					if (isSelected)
						setBorder(new LineBorder(Color.red));
				} else if (value instanceof String)
					setText((String) value);
				else
					setText("");
				return this;
			}
		}
		
	
