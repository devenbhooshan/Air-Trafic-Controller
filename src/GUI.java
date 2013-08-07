import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class GUI extends JPanel implements MouseListener{

	Graphics g;
	Aeroplanes planes[]=new Aeroplanes[1000];
	final int screen_width=1300;
	final int screen_height=700;
	final int outer_circle_diameter=620;
	final int inner_circle_diameter=600;
	//Timer time=new Timer(5, this);
	int max_planes=200;
	
	public GUI(){
		setBackground(Color.white);
		init();
		//time.start();
		repaint();
		addMouseListener(this);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.g=g;
		drwback(g);
		drawAero(g);
		
		update();
		
	}
	
	private void drawAero(Graphics g) {

		int i=0;
		while(i<200){
			
			if(planes[i].flag==0){
			g.setColor(planes[i].color);	
			g.fillRect(planes[i].x, planes[i].y, planes[i].width, planes[i].height);
			}
			i++;
		}
		
		
	}

	public void drwback(Graphics g){
		
		
		g.setColor(Color.red);
		g.fillOval((int)(screen_width/2)-outer_circle_diameter/2,(int)(screen_height/2)-outer_circle_diameter/2,outer_circle_diameter,outer_circle_diameter);
		g.setColor(Color.green);
		g.fillOval((int)(screen_width/2)-inner_circle_diameter/2,(int)(screen_height/2)-inner_circle_diameter/2,inner_circle_diameter,inner_circle_diameter);
		
		g.setColor(Color.blue);
		g.fillRect(screen_width/2-10, screen_height/2-100, 20,200);
		g.setColor(Color.yellow);
		
		g.fillRect(screen_width/2, screen_height/2-outer_circle_diameter/2, 5,5);
		g.setColor(Color.blue);
	}
	
		
	public void init(){
		
		
		int i=0;
		while(i<200){
			
			double rand=Math.random();
			double theta=((((int)((rand)*10000))%361));
			
			double r=(rand*100000)%600+outer_circle_diameter/2;
			int x=(int) (screen_width/2+Math.cos(theta)*r);
			int y=(int) (screen_height/2+Math.sin(theta)*r);
			planes[i]=new Aeroplanes(x, y, Color.gray, 10, 10,((int)(rand*1000))%16+1,1);
			
			i++;
		}
	}	
	public void update(){
		int i=0;
		int counter_allived_planes=0;
		while(i<200){
			if(planes[i].flag==0){
				counter_allived_planes++;
			}
			i++;
		}
		i=200-1;
		//System.out.println("nn"+counter_allived_planes);
/*		
		if(counter_allived_planes<max_planes){
		
			while(i>=0 && counter_allived_planes<max_planes){
				if(planes[i].flag==1){
					//System.out.println("Deven");
					double rand=Math.random();
					double theta=(((int)(rand*10000)%361));
					double r=(rand*100000)%600+outer_circle_diameter/2;
					int x=(int) (screen_width/2+Math.cos(theta)*r);
					int y=(int) (screen_height/2+Math.sin(theta)*r);
					planes[i].x=x;
					planes[i].y=y;
					planes[i].height=10;
					planes[i].width=10;
					planes[i].speed=((int)(rand*1000))%16+1;
					
					planes[i].flag=0;
					
					counter_allived_planes++;
				}
				i--;
			}
			
		}
		*/
		
		i=0;
		while(i<200){
			
			
			if(checkcollision(planes[i]) || planes[i].oncircle==1) {
				
					
			if(planes[i].onstrip==0)	
			move_on_circle(planes[i]);
			else {
				move_on_strip(planes[i]);
			}
			//	planes[i].flag=1;
			
			
			}
			if(planes[i].x>=1300 || planes[i].x<0 && planes[i].flag==0 && planes[i].oncircle==0)
			{
				double rand=Math.random();
				double theta=(((int)(rand*10000)%361));
				
				double r=(rand*100000)%600+outer_circle_diameter/2;
				int x=(int) (screen_width/2+Math.cos(theta)*r);
				int y=(int) (screen_height/2+Math.sin(theta)*r);
				planes[i].x=x;
				planes[i].y=y;
			}
			else if(planes[i].flag==0 && planes[i].oncircle==0){
			planes[i].x+=planes[i].speed;
			//System.out.println("Hello All");
			}
			if(planes[i].y>=6500 ||planes[i].y<0 && planes[i].flag==0 && planes[i].oncircle==0)
			{
				double theta=(((int)(Math.random()*10000)%361));
				double r=(Math.random()*100000)%600+outer_circle_diameter/2;
				int x=(int) (screen_width/2+Math.cos(theta)*r);
				int y=(int) (screen_height/2+Math.sin(theta)*r);
				planes[i].x=x;
				planes[i].y=y;
			}
			else if(planes[i].flag==0 && planes[i].oncircle==0)
			planes[i].y+=planes[i].speed;
			//System.out.println(planes[i].speed);
			i++;
			
		}
		repaint();
		
		
	}

	private void move_on_strip(Aeroplanes aeroplanes) {
		if(aeroplanes.y<screen_height/2)
		aeroplanes.y+=10;
		else {
			aeroplanes.oncircle=0;
			aeroplanes.onstrip=0;
			aeroplanes.flag=1;
			aeroplanes.color=Color.gray;
					
		}
		
	}

	private void move_on_circle(Aeroplanes planes) {
		if((planes.x<=screen_width/2+5 && planes.x>=screen_width/2-5) && (planes.y<=(screen_height/2+5-outer_circle_diameter/2) && planes.y>=(screen_height/2-5-outer_circle_diameter/2))){
			System.out.println("Dev");
			planes.onstrip=1;
		}else {
		if(planes.oncircle==0){
			
		planes.color=Color.magenta;	
		planes.oncircle=1;
		
		float x=planes.x;
		float y=planes.y;
		float h=screen_width/2;
		float k=screen_height/2;
		float tan_theeta;
		if(x>h && y<k)
			tan_theeta=((k-y)/(h-x));
		else if(x<h && y<k)
			tan_theeta=180+((k-y)/(h-x));
		
		else tan_theeta=-1*(((k-y)/(h-x)));
		double theeta=Math.toDegrees(Math.atan(tan_theeta));
		planes.theta=theeta;
		}
		else {
			planes.theta--;	
		}
		planes.x=(int) (screen_width/2+(outer_circle_diameter/2) *Math.cos(Math.toRadians(planes.theta))); 
		planes.y=(int) (screen_height/2+(outer_circle_diameter/2) *Math.sin(Math.toRadians((planes.theta))));
		//System.out.println(tan_theeta+""+theeta);
		}
		
	}

	private boolean checkcollision(Aeroplanes planes) {
		boolean collide = false;
		Area area1=new Area(new Ellipse2D.Double(planes.x, planes.y, planes.width, planes.height));
		Area area2=new Area(new Ellipse2D.Double((int)(screen_width/2)-outer_circle_diameter/2,(int)(screen_height/2)-outer_circle_diameter/2,outer_circle_diameter,outer_circle_diameter));
		Area area3=new Area( new Ellipse2D.Double((int)(screen_width/2)-inner_circle_diameter/2,(int)(screen_height/2)-inner_circle_diameter/2,inner_circle_diameter,inner_circle_diameter));
		
		area2.subtract(area3);
		Area collide1 = new Area(area1);
		
		collide1.subtract(area2);
        if (!collide1.equals(area1)) {
            collide = true;
        }
        Area collide2 = new Area(area2);
        collide2.subtract(area1);
        if (!collide2.equals(area2)) {
            collide = true;
        }

        return collide;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Hei");
		Aeroplanes plane;
		double rand=Math.random();
		int i=0;
		while(i<200){
			if(planes[i].flag==1)
			{
				plane=planes[i];
				plane.x=e.getX();
				plane.y=e.getY();
				plane.height=10;
				plane.width=10;
				plane.speed=((int)(rand*1000))%16+1;
				
				plane.flag=0;
				break;
			}
			i++;
		}
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


		
}
