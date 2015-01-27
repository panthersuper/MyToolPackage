package test;

import geo.DrawLink2D;
import igeo.IVec2;
import processing.core.PApplet;

public class Test2DDraw extends PApplet{
	
	int count = 0;
	IVec2 v1 = new IVec2(100,100);
	IVec2 v2 = new IVec2(400,100);
	IVec2 v3 = new IVec2(250,100+150*1.732);

	
	public void setup(){
		size(500,500);
		
	}
	
	public void draw(){
		background(255);
		DrawLink2D.KochCurve(this,v2,v1,count);
		DrawLink2D.KochCurve(this,v3,v2,count);
		DrawLink2D.KochCurve(this,v1,v3,count);

		
	}
	
	public void keyPressed(){
		
		count++;
		
	}
	
}
