package test;

import geo.Action2D;
import igeo.IVec2;
import processing.core.PApplet;

public class Test2D extends PApplet{
	int num = 100;
	IVec2[] pts = new IVec2[num];
	IVec2[] ptsC;
	public void setup(){
		size(500,500);
		smooth();
		for(int i=0;i<num;i++){
			pts[i] = new IVec2(Math.random()*400+50,Math.random()*400+50);
		}
		
		ptsC = Action2D.ConvexHull(pts);
		println(ptsC);

	}
	
	public void draw(){
		pushStyle();
		background(255);
		strokeWeight(2);
		noFill();
		for(int i=0;i<num;i++){
			point((float)pts[i].x,(float)pts[i].y);
		}
		stroke(255,0,0);
		strokeWeight(1);
		beginShape();
		for(int i=0;i<ptsC.length;i++){
			vertex((float)ptsC[i].x,(float)ptsC[i].y);
		}
		endShape(CLOSE);
		popStyle();

	}
	
	
	
}
