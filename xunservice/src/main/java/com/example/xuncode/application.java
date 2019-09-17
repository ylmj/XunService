package com.example.xuncode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class application {
	public static List<processModel> readFiles() {
		File file=new File("/usr/share/applications");
		File[] files = file.listFiles();
		List<processModel> processes=new ArrayList<processModel>();
        for (File f : files)
        {
            if (f.isFile())
            {
            	String fileName=f.getName();
            	
            	int index=fileName.indexOf(".");
            	String name=fileName.substring(0,index);
                List<String>pids=application.findPid(name);
                if(pids.size()!=0) {
                	//application.findPicturePath2(fileName);
                	for(String pid:pids) {
                		processModel p=application.findProcess(pid);
                		p.setPID(pid);
                		p.setName(name);
                		p.setIcon(application.findPicturePath(pid));
                		processes.add(p);
                		//System.out.println(p.getPicturePath());
                	}
                	
                }
            }
        }
        return processes;
	}
	
	//kill -9 [PID]   结束进程
		public static void Killprocess(String pid) {

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
					// "名称","PID","优先级","状态","用户名","cpu","内存","磁盘","网络","描述"
					process = Runtime.getRuntime().exec("kill -9 " + pid);
					System.out.println("kill process:" + pid);

				}

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	
	public static String findPicturePath2(String fileName) {
		ArrayList<String> arrayList = new ArrayList<>();
		try {
			File file = new File("/usr/share/applications/"+fileName);
			System.err.println("/usr/share/applications/"+fileName);
			InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file),"utf-8");
			BufferedReader bf = new BufferedReader(inputReader); 
			String str;
			int i=0;
			while ((str = bf.readLine()) != null) {
				System.out.println(str);
			}
			bf.close();
			inputReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 

	           
		return null;
	}
	
	public static String findExePath(String pid) {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
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

				process = Runtime.getRuntime().exec("ls -ail /proc/" + pid + "/exe");
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = null;

				while ((line = reader.readLine()) != null) {

					int first = line.indexOf(">");
					path = line.substring(first + 2, line.length());

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return path;
	}
	
	public static List<String> findPid(String name) {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		List<String> pids = new ArrayList<String>();
		int count = 0; // 统计进程数
		try {
			Properties prop = System.getProperties();
			// 获取操作系统名称
			boolean is = false;
			String os = prop.getProperty("os.name");
			if (os != null && os.toLowerCase().indexOf("linux") > -1) {	
				BufferedReader reader = null;
				process = Runtime.getRuntime().exec(" pgrep -f  "+name);
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;

				while ((line = reader.readLine()) != null) {
                   pids.add(line);
					
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return pids;
		
	}
	
	public static processModel findProcess(String pid) {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		processModel p = new processModel();

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

				process = Runtime.getRuntime().exec("top -p  " + pid + "  -b -n 1");
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = null;

				int i = 0;

				while ((line = reader.readLine()) != null) {
					
					//"名称", "PID", "优先级", "状态", "用户名", "cpu", "内存", "描述" 
					if (i == 7) {
						Pattern p1 = Pattern.compile("\\s+");
						Matcher m = p1.matcher(line);
						line = m.replaceAll(",");
						if(line.startsWith(",")) {
							line=line.substring(1,line.length());
						}
						String[] strs = line.split(",");
						p.setUser(strs[1]);
						p.setPrio(strs[3]);
						p.setMemory(strs[5]);
						p.setState(strs[7]);
						p.setB_cpu(Double.valueOf(strs[8]));
						p.setB_memory(Double.valueOf(strs[9]));
						p.setName(strs[11]);
					
					}

					i++;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return p;
		
		
	}
	
	public static Icon findPicturePath(String pid) {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		String path = "";
		 Icon icon=null;
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
				process = Runtime.getRuntime().exec("ls -ail /proc/" + pid + "/exe");
				  reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					int first = line.indexOf(">");
				    path = line.substring(first + 2,line.length());
				    FileSystemView fsv=new JFileChooser().getFileSystemView();
				    icon=fsv.getSystemIcon(new File(path));
				   
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return icon;
	}
	
	
	public static void main(String[] args) {
		application.readFiles();
		//application.findPicturePath("2730");
	}


}
