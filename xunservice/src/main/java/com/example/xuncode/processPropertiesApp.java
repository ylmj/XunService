package com.example.xuncode;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;



public class processPropertiesApp extends JFrame{
	 JPanel panel = new JPanel(new BorderLayout());
	 String pid;
	 String name;
	 processPropertiesModel pp;

	
	
	  public processPropertiesApp(String name,String pid) {
		this.setVisible(true);
		this.setSize(600, 290);
		this.setLayout(new BorderLayout());
		this.pid=pid;
		this.name=name;
		this.pp=process.getProcessPropeties(pid);
		this.init();
		
	}
	
    void init() {
    	this.initPanel();
	    this.initTable();
    }
	void initPanel() {
		this.getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	
	void initTable() {
		 Object[][] cellData = {
					{"开始时间",pp.getStartTime()},
					{"使用用户",pp.getUser()},
					{"运行任务数",pp.getRunTask()},
					{"用户空间占用CPU",pp.getB_userCPU()},
					{"内核空间占用CPU",pp.getB_systemCPU()},
					{"空闲CPU",pp.getB_freeCPU()},
					{"物理内存总量",pp.getTotalMemary()},
					{"使用内存",pp.getUsedMemary()},
					{"空闲内存",pp.getFreeMemeary()},
					{"共享内存",pp.getShareMemary()},
					{"虚拟内存总量",pp.getTotalVirtMemry()},
					{"优先级",pp.getPrio()},
					{"文件路径",pp.getPath()}
					};
		 // String[] columnNames = {"属性","   "};
		 
		  String[]columnNames= {"进程 "+name,"PID "+pid};
		  JTable table= new JTable(cellData, columnNames);
	
		  // 把 表头 添加到容器顶部（使用普通的中间容器添加表格时，表头 和 内容 需要分开添加）
		  panel.add(table.getTableHeader(), BorderLayout.NORTH);
	       //把表格内容 添加到容器中心
		  table.getTableHeader().setReorderingAllowed(false);
		  panel.add(table, BorderLayout.CENTER);
		  
		  JTableHeader head1 = table.getTableHeader(); // 创建表格标题对象
	 	  head1.setFont(new Font("楷体", Font.BOLD, 16));// 设置表格字体
		  table.setFont(new Font("Dialog", Font.PLAIN, 14));
		  table.setShowGrid(false);
	      table.setRowHeight(20);// 设置表格行宽
	      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	      //table.getColumn(columnNames[0]).setPreferredWidth(50);
	     
	      //table.getColumnModel().getColumn(0).setPreferredWidth(10); 
		/*
		 * TableColumn col = table.getColumn(1); col.setPreferredWidth(100);
		 * col.setMaxWidth(100); col.setMinWidth(100);
		 */
	      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
	      table.setEnabled(false);//设置表格不可编辑
	      panel.add(table,BorderLayout.CENTER);
	      initColumnSize(table);
	    
	}
	 /**
     * 设置Column的宽度
     */
    private void initColumnSize(JTable table){
        //表格的每一列也是一个组件
        TableColumn tc = null;
        
        for(int i = 0 ;i < table.getColumnCount();i++){
            //注意:这里需要使用TableColumnModel来获取
            //如果直接使用table.getColumn(identifier)会报错,
            tc = table.getColumnModel().getColumn(i);
            tc.setPreferredWidth(50 * (i+1));
        }
    }
}
