package com.example.xuncode;

import javax.swing.Icon;

public class processModel {
   // "名称","PID","状态","用户名","cpu","内存","磁盘","网络","描述"
   private String name;//"名称
   private String PID;// 进程id
   private String state;//进程状态。D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程
   private String user;//进程所有者
   private double b_cpu;//进程所有者
   private double b_memory;//进程使用的物理内存百分比
   private String memory;//进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
   private String picturePath;
   Icon icon;
   
 
   private String describe;
   private String  prio;
   
   
   
public Icon getIcon() {
	return icon;
}
public void setIcon(Icon icon) {
	this.icon = icon;
}
public String getPicturePath() {
	return picturePath;
}
public void setPicturePath(String picturePath) {
	this.picturePath = picturePath;
}
public String getPrio() {
	return prio;
}
public void setPrio(String prio) {
	this.prio = prio;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPID() {
	return PID;
}
public void setPID(String pID) {
	PID = pID;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getUser() {
	return user;
}
public void setUser(String user) {
	this.user = user;
}

public String getMemory() {
	return memory;
}
public void setMemory(String memory) {
	this.memory = memory;
}

public String getDescribe() {
	return describe;
}
public void setDescribe(String describe) {
	this.describe = describe;
}
public double getB_cpu() {
	return b_cpu;
}
public void setB_cpu(double b_cpu) {
	this.b_cpu = b_cpu;
}
public double getB_memory() {
	return b_memory;
}
public void setB_memory(double b_memory) {
	this.b_memory = b_memory;
}
@Override
public String toString() {
	return "processModel [name=" + name + ", PID=" + PID + ", state=" + state + ", user=" + user + ", b_cpu=" + b_cpu
			+ ", b_memory=" + b_memory + ", memory=" + memory + ", describe=" + describe + ", prio=" + prio + "]";
}



   
}
