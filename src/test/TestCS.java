package test;

import igeo.IVec;

import java.util.ArrayList;

import kuka.CS;

import peasy.PeasyCam;
import processing.core.PApplet;

public class TestCS extends PApplet {
	PeasyCam cam;
	int num = 0;
	ArrayList<CS> list = new ArrayList<>();
    CS cs0 = new CS(new IVec(),180,180,180);
    CS cs1 = new CS(new IVec(10,10,5),10,0,0);
    CS cs2 = new CS(new IVec(20,20,10),20,0,0);
    CS cs3 = new CS(new IVec(30,30,15),30,0,0);
    CS cs4 = new CS(new IVec(40,40,20),40,0,0);
	

	public void setup(){
		size(800, 600, OPENGL);
		cam = new PeasyCam(this, 500);
		cam.setMinimumDistance(50);
		cam.setMaximumDistance(5000);
		
		list.add(cs0);
		list.add(cs1);
		list.add(cs2);
		list.add(cs3);
		list.add(cs4);

/*		println(new CS().A());
		println(new CS().B());
		println(new CS().C());
		
		println(new CS().subNew(new CS()).A());
		println(new CS().subNew(new CS()).B());
		println(new CS().subNew(new CS()).C());
*/
/*		println(cs0.A());
		println(cs0.B());
		println(cs0.C());
*/		println(list.get(num).Angle());

	}

	public void draw(){
		background(255);
		list.get(num).draw(this);
		
		cs0.draw(this);
	}

	public void keyPressed() {
		if (key == 'p' || key == 'P') {
			num += 1;
			if (num > list.size() - 1)
				num = list.size() - 1;
		}
		if (key == 'o' || key == 'O') {
			num -= 1;
			if (num < 0)
				num = 0;
		}
		if (key == 'a' || key == 'A') {
			list.get(num).setA(list.get(num).A()+1);
		}
		if (key == 'b' || key == 'B') {
			list.get(num).setB(list.get(num).B()+1);
		}
		if (key == 'c' || key == 'C') {
			list.get(num).setC(list.get(num).C()+1);
		}
		
		println(list.get(num).Angle());


	}







}
