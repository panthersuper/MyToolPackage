package kuka;

import igeo.IVec;
import processing.core.PApplet;

public class Plain {

	IVec v0; //Base point
	IVec v1; //Z vector
	double a,b,c,d;//平面方程系数
	
	public Plain(IVec v0, IVec v1){
		this.v0 = v0;
		this.v1 = v1;
		a = v1.x;
		b = v1.y;
		c = v1.z;
		d = Math.abs(v1.x * v0.x + v1.y * v0.y + v1.z * v0.z);
	}
	
	//P390
	
	public static Line PinterP(final Plain p1, final Plain p2){
		//get the interect line of two plains
		double s1,s2,a,b;
		s1 = p1.d;
		s2 = p2.d;
		double n1n2dot = p1.v1.dot(p2.v1);
		double n1normsqr = p1.v1.dot(p1.v1);
		double n2normsqr = p2.v1.dot(p2.v1);
		a = (s2*n1n2dot-s1*n2normsqr)/(n1n2dot*n1n2dot-n1normsqr*n2normsqr);
		b = (s1*n1n2dot-s2*n2normsqr)/(n1n2dot*n1n2dot-n1normsqr*n2normsqr);
        
		IVec v0 = new IVec(a*p1.v1.x+b*p2.v1.x,a*p1.v1.y+b*p2.v1.y,a*p1.v1.z+b*p2.v1.z);
		IVec vd = p1.v1.cross(p2.v1).unit();
		Line l = new Line(v0,vd);
		return l;
	}
	
	
	
}
