package kuka;

import igeo.IVec;
import processing.core.PApplet;

public class CS {
	/*
	 * 坐标系 Coodinate system* 彭文哲 PENG Wenzhe* generatorpong@gmai.com
	 */

	private IVec Vbase; // base XYZ向量
	private IVec Angle; // ABC向量
	private IVec vx; // x vector
	private IVec vy; // y vector
	private IVec vz; // z vector
	private Plain xy, yz, xz;// xy,yz,xz平面
	private double X, Y, Z, A, B, C;// 坐标参数

	public CS() {
		this.Vbase = new IVec();
		this.vx = new IVec(1, 0, 0);
		this.vy = new IVec(0, 1, 0);
		this.vz = new IVec(0, 0, 1);
		this.xy = new Plain(new IVec(0, 0, 0), vz);
		this.yz = new Plain(new IVec(0, 0, 0), vx);
		this.xz = new Plain(new IVec(0, 0, 0), vy);
		this.X = Vbase.x;
		this.Y = Vbase.y;
		this.Z = Vbase.z;
		this.A = 180;
		this.B = 180;
		this.C = 180;
		this.Angle = new IVec(A, B, C);
	}

	public CS(IVec v) {
		this.Vbase = v.dup();
		this.vx = new IVec(1, 0, 0);
		this.vy = new IVec(0, 1, 0);
		this.vz = new IVec(0, 0, 1);
		this.xy = new Plain(Vbase, vz);
		this.yz = new Plain(Vbase, vx);
		this.xz = new Plain(Vbase, vy);
		this.X = Vbase.x;
		this.Y = Vbase.y;
		this.Z = Vbase.z;
		this.A = 180;
		this.B = 180;
		this.C = 180;
		this.Angle = new IVec(A, B, C);
	}
	
	public CS(double A, double B, double C){
		this.Vbase = new IVec();
		IVec vX = new IVec(1, 0, 0);
		IVec vY = new IVec(0, 1, 0);
		IVec vZ = new IVec(0, 0, 1);

		// A转后得到新向量
		IVec vX1;
		IVec vY1;
		vX1 = vX.rot(new IVec(), vZ, A * Math.PI / 180);
		vY1 = vY.rot(new IVec(), vZ, A * Math.PI / 180);

		// B转后得到新向量
		IVec vX2;
		IVec vZ2;
		vX2 = vX1.rot(new IVec(), vY1, B * Math.PI / 180);
		vZ2 = vZ.rot(new IVec(), vY1, B * Math.PI / 180);

		// C转后得到新向量
		IVec vY3;
		IVec vZ3;
		vY3 = vY1.rot(new IVec(), vX2, C * Math.PI / 180);
		vZ3 = vZ2.rot(new IVec(), vX2, C * Math.PI / 180);

		this.vx = vX2;
		this.vy = vY3;
		this.vz = vZ3;
		xy = new Plain(Vbase, vz);
		yz = new Plain(Vbase, vx);
		xz = new Plain(Vbase, vy);
		this.X = Vbase.x;
		this.Y = Vbase.y;
		this.Z = Vbase.z;
		this.A = A;
		this.B = B;
		this.C = C;
		this.Angle = new IVec(A, B, C);
			
	}
	
	public CS(IVec Vbase, IVec vx, IVec vy) {
		this.Vbase = Vbase.dup();
		this.vx = vx.dup().unit();
		this.vy = vy.dup().unit();
		this.vz = vx.cross(vy).unit();
		xy = new Plain(Vbase, vz);
		yz = new Plain(Vbase, vx);
		xz = new Plain(Vbase, vy);
		this.X = Vbase.x;
		this.Y = Vbase.y;
		this.Z = Vbase.z;
		this.A = ABC(new CS(), this).x;
		this.B = ABC(new CS(), this).y;
		this.C = ABC(new CS(), this).z;
		this.Angle = new IVec(A, B, C);
	}

	public CS(double X,double Y, double Z, double A, double B, double C){
		// OCS为基座标
				this.Vbase = new IVec(X,Y,Z);

				IVec vX = new IVec(1, 0, 0);
				IVec vY = new IVec(0, 1, 0);
				IVec vZ = new IVec(0, 0, 1);

				// A转后得到新向量
				IVec vX1;
				IVec vY1;
				vX1 = vX.rot(new IVec(), vZ, A * Math.PI / 180);
				vY1 = vY.rot(new IVec(), vZ, A * Math.PI / 180);

				// B转后得到新向量
				IVec vX2;
				IVec vZ2;
				vX2 = vX1.rot(new IVec(), vY1, B * Math.PI / 180);
				vZ2 = vZ.rot(new IVec(), vY1, B * Math.PI / 180);

				// C转后得到新向量
				IVec vY3;
				IVec vZ3;
				vY3 = vY1.rot(new IVec(), vX2, C * Math.PI / 180);
				vZ3 = vZ2.rot(new IVec(), vX2, C * Math.PI / 180);

				this.vx = vX2;
				this.vy = vY3;
				this.vz = vZ3;
				xy = new Plain(Vbase, vz);
				yz = new Plain(Vbase, vx);
				xz = new Plain(Vbase, vy);
				this.X = Vbase.x;
				this.Y = Vbase.y;
				this.Z = Vbase.z;
				this.A = A;
				this.B = B;
				this.C = C;
				this.Angle = new IVec(A, B, C);
	}
	
	public CS(IVec Vbase, double A, double B, double C) {
		// OCS为基座标
		this.Vbase = Vbase.dup();

		IVec vX = new IVec(1, 0, 0);
		IVec vY = new IVec(0, 1, 0);
		IVec vZ = new IVec(0, 0, 1);

		// A转后得到新向量
		IVec vX1;
		IVec vY1;
		vX1 = vX.rot(new IVec(), vZ, A * Math.PI / 180);
		vY1 = vY.rot(new IVec(), vZ, A * Math.PI / 180);

		// B转后得到新向量
		IVec vX2;
		IVec vZ2;
		vX2 = vX1.rot(new IVec(), vY1, B * Math.PI / 180);
		vZ2 = vZ.rot(new IVec(), vY1, B * Math.PI / 180);

		// C转后得到新向量
		IVec vY3;
		IVec vZ3;
		vY3 = vY1.rot(new IVec(), vX2, C * Math.PI / 180);
		vZ3 = vZ2.rot(new IVec(), vX2, C * Math.PI / 180);

		this.vx = vX2;
		this.vy = vY3;
		this.vz = vZ3;
		xy = new Plain(Vbase, vz);
		yz = new Plain(Vbase, vx);
		xz = new Plain(Vbase, vy);
		this.X = Vbase.x;
		this.Y = Vbase.y;
		this.Z = Vbase.z;
		this.A = A;
		this.B = B;
		this.C = C;
		this.Angle = new IVec(A, B, C);
	}

	public double X() {
		return this.X;
	}

	public double Y() {
		return this.Y;
	}

	public double Z() {
		return this.Z;
	}

	public double A() {
		return this.A;
	}

	public double B() {
		return this.B;
	}

	public double C() {
		return this.C;
	}

	public void setA(double a) {
		this.rot(-this.A, 0, 0);
		this.A = a;
		if(this.A<-180)this.A+=360;
		if(this.A>180)this.A-=360;

		this.rot(this.A, 0, 0);
	}

	public void setB(double b) {
		this.rot(0, -this.B, 0);
		this.B = b;
		if(this.B<-180)this.B+=360;
		if(this.B>180)this.B-=360;

		this.rot(0, this.B, 0);
	}

	public void setC(double c) {
		this.rot(0, 0, -this.C);
		this.C = c;
		if(this.C<-180)this.C+=360;
		if(this.C>180)this.C-=360;

		this.rot(0, 0, this.C);
	}

	public IVec Vbase() {
		return this.Vbase;
	}

	public IVec Angle() {
		return this.Angle;
	}

	public void draw(PApplet app) {
		app.pushStyle();
		app.fill(0, 0, 0);
		IVec v = this.Vbase;
		app.stroke(255, 0, 0);
		app.line((float) v.x, -(float) v.y, (float) v.z, (float) (vx.dup().scale(100).x + v.x),
				-(float) (vx.dup().scale(100).y + v.y), (float) (vx.dup().scale(100).z + v.z));
		app.stroke(0, 255, 0);
		app.line((float) v.x, -(float) v.y, (float) v.z, (float) (vy.dup().scale(100).x + v.x),
				-(float) (vy.dup().scale(100).y + v.y), (float) (vy.dup().scale(100).z + v.z));
		app.stroke(0, 0, 255);
		app.line((float) v.x, -(float) v.y, (float) v.z, (float) (vz.dup().scale(100).x + v.x),
				-(float) (vz.dup().scale(100).y + v.y), (float) (vz.dup().scale(100).z + v.z));

		app.sphere(2);
		// app.box((float)this.x, (float)this.y,(float)this.z);
		app.popStyle();
	}

	public void draw(PApplet app, float n) {
		app.pushStyle();
		app.fill(0, 0, 0);
		IVec v = this.Vbase;
		app.stroke(255, n, n);
		app.line((float) v.x, -(float) v.y, (float) v.z, (float) (vx.dup().scale(100).x + v.x),
				-(float) (vx.dup().scale(100).y + v.y), (float) (vx.dup().scale(100).z + v.z));
		app.stroke(n, 255, n);
		app.line((float) v.x, -(float) v.y, (float) v.z, (float) (vy.dup().scale(100).x + v.x),
				-(float) (vy.dup().scale(100).y + v.y), (float) (vy.dup().scale(100).z + v.z));
		app.stroke(n, n, 255);
		app.line((float) v.x, -(float) v.y, (float) v.z, (float) (vz.dup().scale(100).x + v.x),
				-(float) (vz.dup().scale(100).y + v.y), (float) (vz.dup().scale(100).z + v.z));

		app.sphere(2);
		// app.box((float)this.x, (float)this.y,(float)this.z);
		app.popStyle();
	}

	/*
	 * public void draw(PApplet app, float n) { vx.dup().scale(100).draw(Vbase,
	 * app, 255, n, n); vy.dup().scale(100).draw(Vbase, app, n, 255, n);
	 * vz.dup().scale(100).draw(Vbase, app, n, n, 255); }
	 */

	public CS posNew(IVec v){
		//反回坐标为v的CS， 角度不改变
		return this.addNew(new CS(v.dup().sub(this.Vbase)));
	}
		
	public CS subNew(CS OCS) {
		// 计算相对于基坐标的相对坐标
		// OCS：基座标
		IVec Rel_base = this.Vbase.dup().sub(OCS.Vbase);
		return new CS(Rel_base, ABC(OCS, this).x, ABC(OCS, this).y, ABC(OCS, this).z);
		// return new CS(Rel_base, this.A-OCS.A(), this.B-OCS.B(),
		// this.C-OCS.C());
	}

	public CS addNew(CS cs) {
		// 计算加上cs后的新坐标
		IVec basenew = this.Vbase.dup().add(cs.Vbase);
		return new CS(basenew, this.A, this.B, this.C).rotNew(cs.A(), cs.B(), cs.C());
	}

	public void rot(double A, double B, double C) {
		// 将CS按照a，b，c旋转之后得到的坐标系

		CS cs = this.rotNew(A, B, C);
		
		vx.set(cs.vx);
		vy.set(cs.vy);
		vz.set(cs.vz);
		xy = new Plain(this.Vbase, vz);
		yz = new Plain(this.Vbase, vx);
		xz = new Plain(this.Vbase, vy);
		this.A = cs.A();
		this.B = cs.B();
		this.C = cs.C();
		this.Angle = new IVec(this.A, this.B, this.C);
	}

	public CS rotNew(double A, double B, double C) {
		// 将CS按照a，b，c旋转之后得到的坐标系

		// A转后得到新向量
		IVec vX1;
		IVec vY1;
		vX1 = vx.dup().rot(new IVec(), vz, A * Math.PI / 180);
		vY1 = vy.dup().rot(new IVec(), vz, A * Math.PI / 180);

		// B转后得到新向量
		IVec vX2;
		IVec vZ2;
		vX2 = vX1.dup().rot(new IVec(), vY1, B * Math.PI / 180);
		vZ2 = vz.dup().rot(new IVec(), vY1, B * Math.PI / 180);

		// C转后得到新向量
		IVec vY3;
		IVec vZ3;
		vY3 = vY1.dup().rot(new IVec(), vX2, C * Math.PI / 180);
		vZ3 = vZ2.dup().rot(new IVec(), vX2, C * Math.PI / 180);

		return new CS(this.Vbase, vX2, vY3);
	}

	public static IVec ABC(CS OCS, CS TCS) {
		// Generator
		// input Original coodinate system
		// input Target coodinate system
		IVec ABC;
		double a, b, c;
		IVec Nx, Ny, Nz;

		Ny = Plain.PinterP(TCS.yz, OCS.xy).vd;

		Nz = OCS.vz;
		Nx = Ny.cross(Nz);
		a = OCS.vy.angle(Ny) * (180 / Math.PI);
		b = Nx.angle(TCS.vx) * (180 / Math.PI);
		c = Ny.angle(TCS.vy) * (180 / Math.PI);
		if (OCS.vy.cross(Ny).angle(Nz) > 0.001)
			a = -a;
		if (Nx.cross(TCS.vx).angle(Ny) > 0.001)
			b = -b;
		if (Ny.cross(TCS.vy).angle(TCS.vx) > 0.001)
			a = -a;

		ABC = new IVec(a, b, c);

		return ABC;
	}
	
	public static IVec midP(CS cs1, CS cs2){
		//求两坐标的中间点
		IVec v = new IVec();
		v = cs1.Vbase.dup().add(cs2.Vbase);
		v.scale(1f/2);
		return v;
	}
	
	public double dist(IVec v){
		return this.Vbase.dist(v);
	}
	
	public double dist(CS cs){
		return this.Vbase.dist(cs.Vbase);
	}
	
	public String toString(){
		String s = "";
		s += "(";
		s += this.Vbase.toString();
		s += this.Angle.toString();
		s += ")";
		return s;
		
		
		
	}
	
}
