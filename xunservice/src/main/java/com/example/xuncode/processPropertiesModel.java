package com.example.xuncode;

public class processPropertiesModel {
	private String startTime;
	private String user;
	private String runTask;
	private String b_userCPU;
	private String b_systemCPU;
	private String b_freeCPU;
	private String totalMemary;
	private String usedMemary;
	private String freeMemeary;
	private String prio;
	private String shareMemary;
	private String totalVirtMemry;
	private String path;
	@Override
	public String toString() {
		return "processPropertiesModel [startTime=" + startTime + ", user=" + user + ", runTask=" + runTask
				+ ", b_userCPU=" + b_userCPU + ", b_systemCPU=" + b_systemCPU + ", b_freeCPU=" + b_freeCPU
				+ ", totalMemary=" + totalMemary + ", usedMemary=" + usedMemary + ", freeMemeary=" + freeMemeary
				+ ", prio=" + prio + ", shareMemary=" + shareMemary + ", totalVirtMemry=" + totalVirtMemry + ", path="
				+ path + "]";
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRunTask() {
		return runTask;
	}
	public void setRunTask(String runTask) {
		this.runTask = runTask;
	}
	public String getB_userCPU() {
		return b_userCPU;
	}
	public void setB_userCPU(String b_userCPU) {
		this.b_userCPU = b_userCPU;
	}
	public String getB_systemCPU() {
		return b_systemCPU;
	}
	public void setB_systemCPU(String b_systemCPU) {
		this.b_systemCPU = b_systemCPU;
	}
	public String getB_freeCPU() {
		return b_freeCPU;
	}
	public void setB_freeCPU(String b_freeCPU) {
		this.b_freeCPU = b_freeCPU;
	}
	public String getTotalMemary() {
		return totalMemary;
	}
	public void setTotalMemary(String totalMemary) {
		this.totalMemary = totalMemary;
	}
	public String getUsedMemary() {
		return usedMemary;
	}
	public void setUsedMemary(String usedMemary) {
		this.usedMemary = usedMemary;
	}
	public String getFreeMemeary() {
		return freeMemeary;
	}
	public void setFreeMemeary(String freeMemeary) {
		this.freeMemeary = freeMemeary;
	}
	public String getPrio() {
		return prio;
	}
	public void setPrio(String prio) {
		this.prio = prio;
	}
	public String getShareMemary() {
		return shareMemary;
	}
	public void setShareMemary(String shareMemary) {
		this.shareMemary = shareMemary;
	}
	public String getTotalVirtMemry() {
		return totalVirtMemry;
	}
	public void setTotalVirtMemry(String totalVirtMemry) {
		this.totalVirtMemry = totalVirtMemry;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}
