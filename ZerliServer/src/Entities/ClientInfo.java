package Entities;

public class ClientInfo {
	
	 private String ip;
	 private String hostName;
	 private String status;
	 

	public ClientInfo(byte[] ip, String hostName, String status) { 		
		this.ip = ip[0]+ "." + ip[1]+ "." +ip[2]+ "." +ip[3];
		this.hostName = hostName;
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public String getHostName() {
		return hostName;
	}

	public String getStatus() {
		return status;
	}
}
