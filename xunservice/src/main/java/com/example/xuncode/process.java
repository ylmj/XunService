package com.example.xuncode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class process {

	// 结束进程树
	// 结束进程树的特性就是在结束一个进程的同时结束由该进程直接或间接创建的子进程
	// pstree -p pid 查看子进程
	// 返回子进程和父进程的id
	public static List<String> killProcessTree(String pid) {

		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		List<processModel> processs = new ArrayList<processModel>();
		int count = 0; // 统计进程数
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
				// 查询子进程
				process = Runtime.getRuntime().exec("pstree  -p " + pid);
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				List<String> pids = new ArrayList<String>();
				String line = null;

				while ((line = reader.readLine()) != null) {
					// System.out.println(line);
					int length = line.length() - 1;
					String id = line.substring(length - 4, length);
					pids.add(id);
					Killprocess(id);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

//修改进程优先级	
	public static void updatePrio(String pid, int prio) {
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

				process = Runtime.getRuntime().exec("renice " + prio + "  " + pid);
				System.out.println("renice " + prio + "  " + pid);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

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

	// get process infomation
	public static List<processModel> Getprocess() {
		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		List<processModel> processs = new ArrayList<processModel>();
		int count = 0; // 统计进程数
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
				process = Runtime.getRuntime().exec("top -b -n 1");
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = null;

				while ((line = reader.readLine()) != null) {
				if(line.indexOf("PID")>0) {
						is = true;
						line = reader.readLine();
					}
					if (is == true) {
						Pattern p = Pattern.compile("\\s+");
						Matcher m = p.matcher(line);
						line = m.replaceAll(",");
						if(line.startsWith(",")) {
							line=line.substring(1,line.length());
						}
						String[] strs = line.split(",");
						processModel pro = new processModel();
						pro.setPID(strs[0]);
					
						pro.setUser(strs[1]);
						pro.setPrio(strs[3]);
						pro.setMemory(strs[5]);
						pro.setState(strs[7]);
						pro.setB_cpu(Double.valueOf(strs[8]));
						pro.setB_memory(Double.valueOf(strs[9]));
						pro.setName(strs[11]);
						processs.add(pro);
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return processs;

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

	public static processPropertiesModel getProcessPropeties(String pid) {

		Runtime runtime = Runtime.getRuntime();
		List<String> tasklist = new ArrayList<String>();
		java.lang.Process process = null;
		List<processModel> processs = new ArrayList<processModel>();

		processPropertiesModel p = new processPropertiesModel();
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

					if (i == 0) {
						int index = line.indexOf("u");
						p.setStartTime(line.substring(index - 9, index).trim());
					} else if (i == 1) {
						String[] arr = line.split(",");
						int index = arr[1].indexOf("r");
						p.setRunTask(arr[1].substring(0, index).trim());
					} else if (i == 2) {
						line = line.substring(line.indexOf(":") + 1);
						String[] arr = line.split(",");
						int index = arr[0].indexOf("u");
						p.setB_userCPU(arr[0].substring(0, index).trim());
						index = arr[1].indexOf("s");
						p.setB_systemCPU(arr[1].substring(0, index).trim());
						index = arr[3].indexOf("i");
						p.setB_freeCPU(arr[3].substring(0, index).trim());

					} else if (i == 3) {
						String[] arr = line.split(",");
						int index = arr[0].indexOf("t");
						int index2 = arr[0].indexOf(":");
						p.setTotalMemary(arr[0].substring(index2 + 1, index).trim());
						index = arr[1].indexOf("u");
						p.setUsedMemary(arr[1].substring(0, index).trim());
						index = arr[2].indexOf("f");
						p.setFreeMemeary(arr[2].substring(0, index).trim());

					} else if (i == 4) {
						String[] arr = line.split(",");
						int index = arr[0].indexOf("t");
						int index2 = arr[0].indexOf(":");
						p.setTotalVirtMemry(arr[0].substring(index2 + 1, index).trim());
					} else if (i == 7) {
						Pattern p1 = Pattern.compile("\\s+");
						Matcher m = p1.matcher(line);
						line = m.replaceAll(",");
						String[] arr = line.split(",");
						p.setShareMemary(arr[7].trim());
						p.setPrio(arr[3].trim());
						p.setUser(arr[2].trim());
					}

					i++;
				}

				p.setPath(findExePath(pid));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return p;

	}

	public static void main(String[] args) throws Exception {
		Getprocess();
		// Killprocess("1239");
		// installTool("iotop");
		// killProcessTree("2887");
		// findExePath("4438");
		//getProcessPropeties("2911");
	}
}
