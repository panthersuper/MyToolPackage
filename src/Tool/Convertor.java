package Tool;

public class Convertor {
	
	
	public static float[] CMYKToRGB(float[] cmyk){
		float c = cmyk[0];
		float m = cmyk[1];
		float y = cmyk[2];
		float k = cmyk[3];
		
		float cc = (c*(1-k)+k);
		float mm = (m*(1-k)+k);
		float yy = (y*(1-k)+k);

		float r = (1-cc)*255;
		float g = (1-mm)*255;
		float b = (1-yy)*255;

		return new float[]{r,g,b};
	}
	
	public static float[] RGBToCMYK(float[] rgb){
		float r = rgb[0];
		float g = rgb[1];
		float b = rgb[2];
		
		float cc = 1-r/255;
		float mm = 1-g/255;
		float yy = 1-b/255;
		
		float var_K = 1;
		
		if(cc<var_K)var_K = cc;
		if(mm<var_K)var_K = mm;
		if(yy<var_K)var_K = yy;

		float c = (cc-var_K)/(1-var_K);		
		float m = (mm-var_K)/(1-var_K);		
		float y = (yy-var_K)/(1-var_K);		
		float k = var_K;
		
		return new float[]{c,m,y,k};
	}
}
