import java.net.*;
import java.io.*;
import java.util.*;

public class QQServer{
	public static Map<Socket, String> socketMap = Collections.synchronizedMap(
		new HashMap<>());
	public static void main(String[] args) throws Exception{
		//set the port for communication
		ServerSocket ss = new ServerSocket(30000);
		while(true){
			Socket s = ss.accept();
			// socketList.add(s);
			new Thread(new SingleThread(s)).start();
		}
	}
}

class SingleThread implements Runnable{
	Socket s = null;
	BufferedReader br = null;
	SingleThread(Socket s) throws Exception{
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}

	public void run(){
		String content = null;
		while((content = readFromClient()) != null){
			try{
				String[] div = content.split(":");
				QQServer.socketMap.put(s, div[0]);
				for(Map.Entry<Socket, String> entry : QQServer.socketMap.entrySet()){
					if(!entry.getKey().equals(this.s)){
						PrintStream ps = new PrintStream(
							entry.getKey().getOutputStream());
						ps.println(content);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private String readFromClient(){
		try{
			return br.readLine();
		}catch(Exception e){
			QQServer.socketMap.remove(s);
		}
		return null;
	}
}