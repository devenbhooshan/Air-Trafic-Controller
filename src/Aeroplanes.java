import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Aeroplanes {
	
	int width;
	int height;
	Color color;
	int x;
	int y;
	int speed;
	int flag;
	int oncircle=0;
	double theta;
	int onstrip=0;
	
	public Aeroplanes(int x,int y,Color color,int width,int height,int speed,int flag){
	this.flag=1;	
	this.x=x;
	this.y=y;
	this.color=color;
	this.height=height;
	this.width=width;
	this.speed=speed; 
	
}





public void init(){
	try {
	ServerSocket server=new ServerSocket(5050);
	Socket s=server.accept();
	BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	int flag=0;
	if(flag==1){
	in.readLine();
	
	server.close();
	}else {
	
		
		
		
	}
	
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}
	
	
}
