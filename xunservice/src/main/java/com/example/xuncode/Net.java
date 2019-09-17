package com.example.xuncode;

public class Net {
	
		private String NetPid;
		private	String NetName;
		private String send;
		private String receive;
		private String host;
		public Net() {}
		public Net(String netPid,String netName,String send,String receive,String host)
		{
			this.NetPid=netPid;
			this.NetName=netName;
			this.send=send;
			this.receive=receive;
			this.host=host;
		}
		public String getNetPid() {
			return NetPid;
		}
		public void setNetPid(String netPid) {
			NetPid = netPid;
		}
		public String getNetName() {
			return NetName;
		}
		public void setNetName(String netName) {
			NetName = netName;
		}
		public String getSend() {
			return send;
		}
		public void setSend(String send) {
			this.send = send;
		}
		public String getReceive() {
			return receive;
		}
		public void setReceive(String receive) {
			this.receive = receive;
		}
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		

	

}
