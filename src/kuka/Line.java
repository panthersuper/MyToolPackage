package kuka;

import igeo.IVec;

public class Line {
    IVec v0; //Base point
    IVec vd; //direction vector
	
    public Line(){
	}
    
	public Line(IVec v0, IVec vd){
		this.v0 = v0;
		this.vd = vd;
	}
	
	public void setV0(IVec v0){
		this.v0= v0;
	}
	
	public void setVd(IVec vd){
		this.vd= vd;
	}

}
