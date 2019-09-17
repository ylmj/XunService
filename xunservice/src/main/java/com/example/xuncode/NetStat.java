package com.example.xuncode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;


public class NetStat {
	private static NetStat INSTANCE = new NetStat();  
	    public NetStat(){  

	    }  

	    public static NetStat getInstance(){  
	        return INSTANCE;  
	    }  
	/*
	 * use netstat 显示 PID 和进程名称
	 * netstat -p 可以与其它开关一起使用，
	 * 就可以添加 “PID/进程名称” 到 netstat 输出中，
	 * 这样 debugging 的时候可以很方便的发现特定端口运行的程序。
	 * */
	
	public List<Net> getNetStat()
	{
		Process pro1;  
        Runtime r = Runtime.getRuntime();  
        List<Net> nets=new ArrayList<Net>();
        try {  
            String command = "netstat -pt";  
            //Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name
            //tcp        0      0 192.168.137.130:42330   ec2-52-42-122-173:https ESTABLISHED 4316/firefox  
           // long startTime = System.currentTimeMillis();  
            pro1 = r.exec(command);  
            BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));  
            String line = null;  
            //long inSize1 = 0, outSize1 = 0;  
            while((line=in1.readLine()) != null){     
                line = line.trim();  
                if(line.startsWith("tcp")){  
                  // System.out.println(line);
                	Net n=new Net();
                    String[] temp = line.split("\\s+");
                   // System.out.println("PID\t进程名\t接受量\t发送量\t本地地址");
                    if(temp[6].contains("/"))
                    {
                    	//System.out.println(line);	
                    n.setNetPid(temp[6].split("/")[0]);
                    n.setNetName(temp[6].split("/")[1]);
                    n.setReceive(temp[1]);
                    n.setSend(temp[2]);
                    n.setHost(temp[3]);
                    nets.add(n);
                    }
                    else
                    {
                    	continue;
                    }
                    
                   // System.out.println(temp[6].split("/")[0]+"\t"+temp[6].split("/")[1]+"\t"+temp[1]+"\t"+temp[2]+"\t"+temp[3]);
                  //  inSize1 = Long.parseLong(temp[1].substring(5)); //Receive bytes,单位为Byte  
                   // outSize1 = Long.parseLong(temp[9]);             //Transmit bytes,单位为Byte  
                    continue;
                }           
               
            }
            in1.close();  
            pro1.destroy(); 
        } catch (IOException e) {  
            StringWriter sw = new StringWriter();  
            e.printStackTrace(new PrintWriter(sw));  
            System.out.println("NetUsage发生InstantiationException. " + e.getMessage());
            System.out.println(sw.toString());
        } 
             
		return nets;
	}
	
	  public static void main(String[] args) throws InterruptedException {
		  NetStat.getInstance().getNetStat();
		/*
		 * while(true){ NetStat.getInstance().getNetStat(); Thread.sleep(5000); }
		 */
	    }  

}
