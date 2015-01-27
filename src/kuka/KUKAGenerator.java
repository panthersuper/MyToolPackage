package kuka;

import igeo.IVec;
import igeo.IVec2;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;

import processing.core.PApplet;

public class KUKAGenerator {
	static PrintWriter OutDATC;// dat文件
	static PrintWriter OutSRCC;// src文件
	static BigInteger ss = new BigInteger("010", 2);// 转换为十进制
	static BigInteger tt = new BigInteger("001010", 2);// 转换为十进制
	static double E1 = 1000;// E轴
	static double E0 = 1000;// E轴
	static double R1 = 1450; // 加工大半径
	static double R2 = 1050; // 加工小半径
	static String folder;
	static String location;
	static int ToolNum = 6;// 工具
	static int BaseNum = 6;// 基座标
	static CS base = new CS();// 基座标
	static CS tool = new CS();// 基座标
	//static CS tran = new CS(new IVec(600, 400, 0), -90, 0, 0);// 通过移动E轴改变方向过程中的位置/角度
	static CS tran = new CS(new IVec(0,0,1000), -90, 0, 180);// 通过移动E轴改变方向过程中的位置和角度
	static ArrayList<CS> list = new ArrayList<CS>();
	
	
	public static void setLocation(String l, String f) {
		folder = f;
		location = l;
	}

	public static void setBase(CS cs, int num) {
		base = cs;
		BaseNum = num;
	}

	public static void setTool(CS cs, int num) {
		tool = cs;
		ToolNum = num;
	}

	public static void setTran(CS pos) {
		tran = pos;
	}

	public static void setST(String s, String t) {
		ss = new BigInteger(s, 2);
		tt = new BigInteger(t, 2);
	}

	public static void getMachCode(ArrayList<CS> l, boolean b, boolean t, PApplet app) {
		// b： list中的坐标是否为相对工作坐标系base坐标(或世界坐标)
		// t: list中坐标是否为相对工具坐标系tool坐标
		list = (ArrayList<CS>)l.clone();
		updateList(list, b, t);
		DAT(app);
		SRC(app, list);
	}

	public static void DAT(PApplet app) {
		// Write the .dat to the file
		OutDATC = app.createWriter(location + folder + "/" + "run"+folder + ".dat");

		OutDATC.println("&ACCESS RVP");
		OutDATC.println("&REL 1"); // 待测
		OutDATC.println("&PARAM TEMPLATE = C:" + "\\" + "KRC" + "\\" + "Roboter" + "\\"
				+ "Template" + "\\" + "vorgabe");

		OutDATC.println("&PARAM EDITMASK = *");
		OutDATC.println("DEFDAT " + folder + "run");
		OutDATC.println(";FOLD EXTERNAL DECLARATIONS;%{PE}%MKUKATPBASIS,%CEXT,%VCOMMON,%P");
		OutDATC.println(";FOLD BASISTECH EXT;%{PE}%MKUKATPBASIS,%CEXT,%VEXT,%P");
		OutDATC.println("EXT  BAS (BAS_COMMAND  :IN,REAL  :IN )");
		OutDATC.println("DECL INT SUCCESS");
		OutDATC.println(";ENDFOLD (BASISTECH EXT)");
		OutDATC.println(";FOLD USER EXT;%{E}%MKUKATPUSER,%CEXT,%VEXT,%P");
		OutDATC.println(";Make here your modifications");
		OutDATC.println("");
		OutDATC.println(";ENDFOLD (USER EXT)");
		OutDATC.println(";ENDFOLD (EXTERNAL DECLARATIONS)");
		OutDATC.println("DECL BASIS_SUGG_T LAST_BASIS={POINT1[] \"P2                      \",POINT2[] \"P2                      \",CP_PARAMS[] \"CPDAT0                  \",PTP_PARAMS[] \"PDAT2                   \",CONT[] \"                        \",CP_VEL[] \"2.0                     \",PTP_VEL[] \"100                     \",SYNC_PARAMS[] \"SYNCDAT                 \",SPL_NAME[] \"S0                      \"}");
		OutDATC.println("");
		OutDATC.println("enddat");
		OutDATC.flush(); // Writes the remaining data to the file
		OutDATC.close(); // Finishes the file
	}

	public static void SRC(PApplet app, ArrayList<CS> list) {
		// Write the .src to the file
		OutSRCC = app.createWriter(location + folder + "/" + "run"+folder + ".src");
		srcSet();
		PTPHome();
		OutSRCC.println("$BASE=BASE_DATA[" + BaseNum + "]");
		OutSRCC.println("$TOOL=TOOL_DATA[" + ToolNum + "]");

		OutSRCC.println("BAS (#Vel_ptp,100 )");
		OutSRCC.println("BAS (#Vel_cp,25 )");
		OutSRCC.println("$APO.CVEL=4");
		OutSRCC.println("$APO.CORI=3");
		OutSRCC.println("$APO.CDIS=10");
		srcCut(list);

		PTPHome();
		OutSRCC.println("END");
		OutSRCC.flush(); // Writes the remaining data to the file
		OutSRCC.close(); // Finishes the file
	}

	public static void srcSet() {
		OutSRCC.println("&ACCESS RVP");
		OutSRCC.println("&REL 1");// 待测
		OutSRCC.println("&PARAM TEMPLATE = C:" + "\\" + "KRC" + "\\" + "Roboter" + "\\"
				+ "Template" + "\\" + "vorgabe");
		OutSRCC.println("&PARAM EDITMASK = *");
		OutSRCC.println("DEF " + folder + "run" + "( )");
		OutSRCC.println("");
		OutSRCC.println(";FOLD INI");
		OutSRCC.println("  ;FOLD BASISTECH INI");
		OutSRCC.println("    GLOBAL INTERRUPT DECL 3 WHEN $STOPMESS==TRUE DO IR_STOPM ( )");
		OutSRCC.println("    INTERRUPT ON 3 ");
		OutSRCC.println("    BAS (#INITMOV,0 )");
		OutSRCC.println("  ;ENDFOLD (BASISTECH INI)");
		OutSRCC.println("  ;FOLD USER INI");
		OutSRCC.println("    ;Make your modifications here");
		OutSRCC.println("");
		OutSRCC.println("  ;ENDFOLD (USER INI)");
		OutSRCC.println(";ENDFOLD (INI)");
	}

	public static void PTPHome() {
		OutSRCC.println(";FOLD PTP HOME  Vel= 100 % DEFAULT;%{PE}%MKUKATPBASIS,%CMOVE,%VPTP,%P 1:PTP, 2:HOME, 3:, 5:100, 7:DEFAULT");
		OutSRCC.println("$BWDSTART = FALSE");
		OutSRCC.println("PDAT_ACT=PDEFAULT");
		OutSRCC.println("FDAT_ACT=FHOME");
		OutSRCC.println("BAS (#PTP_PARAMS,100 )");
		OutSRCC.println("$H_POS=XHOME");
		OutSRCC.println("PTP  XHOME");
		OutSRCC.println(";ENDFOLD");
	}

	public static void srcCut(ArrayList<CS> l) {
		// 开始转换过程

		OutSRCC.println("BAS (#Vel_ptp,100 )");
		OutSRCC.println("BAS (#Vel_cp,25 )");
		OutSRCC.println("$APO.CVEL=4");
		OutSRCC.println("$APO.CORI=3");
		OutSRCC.println("$APO.CDIS=10");

		for (int j = 0; j < l.size(); j++) {

			JudgeMove(j, l);

			// JudgeMove(j, list);
		}

	}

	public static void JudgeMove(int i, ArrayList<CS> list) {
		// 进行左右可达判断，并进行相应移动
		// 判断前一时刻和当前时刻的左右位置，确定相应E轴位置
		// i为当前位置

		if (i != 0) {// 当前点不是第一点
			boolean nowR = JudgeAngRP(list.get(i));
			boolean nowL = JudgeAngLP(list.get(i));
			boolean befR = JudgeAngRP(list.get(i - 1));
			boolean befL = JudgeAngLP(list.get(i - 1));
			
			if (nowR) {// 当前时刻从右到左
				if (befR) {// 前一时刻从右到左

					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 姿态正常，直接移动

				} else if (befL) {// 前一时刻从左到右
					IVec v =CS.midP(list.get(i), list.get(i-1)).add(tran.Vbase());
					CS mid = tran.posNew(new IVec(v.x(),v.y(),tran.Z()));//两者的中间位置点加上tran后的位置
					E1MatchP(mid);// 使E1满足
					Move(mid, E1); // 姿态正常，直接移动
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 移动

				} else {// 前一时刻处于其他姿态
					
					if(onTrail(list.get(i - 1), E1)){//前一时刻的位置在轨道上方
						IVec v =CS.midP(list.get(i), list.get(i-1)).add(tran.Vbase());
						CS mid = tran.posNew(new IVec(v.x(),v.y(),tran.Z()));//两者的中间位置点加上tran后的位置
						E1MatchP(mid);// 使E1满足
						Move(mid, E1); // 姿态正常，直接移动
                    }
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 移动

				}

			} else if (nowL) {// 当前时刻从左到右
				if (befR) {// 前一时刻从右到左
					IVec v =CS.midP(list.get(i), list.get(i-1)).add(tran.Vbase());
					CS mid = tran.posNew(new IVec(v.x(),v.y(),tran.Z()));//两者的中间位置点加上tran后的位置
					E1MatchP(mid);// 使E1满足
					Move(mid, E1); // 姿态正常，直接移动
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 移动

				} else if (befL) {// 前一时刻从左到右
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 姿态正常，直接移动

				} else {// 前一时刻处于其他姿态
					/*if(onTrail(list.get(i - 1), E1)){//前一时刻的位置在轨道上方
					IVec v =CS.midP(list.get(i), list.get(i-1)).add(tran.Vbase());
					CS mid = tran.posNew(new IVec(v.x(),v.y(),tran.Z()));//两者的中间位置点加上tran后的位置
					E1MatchP(mid);// 使E1满足
					Move(mid, E1); // 姿态正常，直接移动
					}*/
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 移动

				}

			} else {// 当前时刻处于其他姿态
				if (befR) {// 前一时刻从右到左
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 姿态正常，直接移动

				} else if (befL) {// 前一时刻从左到右
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 姿态正常，直接移动

				} else {// 前一时刻处于其他姿态
					E1MatchP(list.get(i));// 使E1满足
					Move(i, list, E1); // 姿态正常，直接移动

				}

			}
		} else {// 当前点为第一点
			E1MatchP(list.get(i));// 使E1满足
			Move(i, list, E1); // 姿态正常，直接移动

		}

	}

	private static void E1MatchP(CS cs) {
		// 改变E值，使得能够达到位置cs
		double E;

		int count = 0;

		if (JudgeAngRP(cs)) {// 右侧朝左
			E = xToe(cs.X()) + 1500;
			while (cs.dist(basePos(E)) > R1 && count < 30) {
				E -= 100;
				count++;
			}

		} else if (JudgeAngLP(cs)) {// 左侧朝右
			E = xToe(cs.X()) - 1500;
			while (cs.dist(basePos(E)) > R1 && count < 30) {
				E += 100;
				count++;
			}
		} else {

			E = xToe(cs.X());
			while (cs.dist(basePos(E)) < R2 && count < 30) {
				E -= 100;
				count++;

			}

		}
		if (count < 30)
			E1 = E;
	}

	public static boolean JudgeAngRP(CS cs) {
		// 判断第i坐标对应的ABC值角度范围，若超出所定右侧范围须改变E1轴方向
		CS csj = cs.subNew(new CS(180, 0, 0));
		boolean gg = (Math.abs(csj.B() - 180)%360 < 30) && (Math.abs(csj.A() - 180)%360 < 30);

		return gg;
	}

	public static boolean JudgeAngLP(CS cs) {
		// 判断第i坐标对应的ABC值角度范围，若超出所定左侧范围须改变E1轴方向
		CS csj = cs.subNew(new CS(0, 0, 0));

		boolean gg = Math.abs(csj.B() - 180)%360 < 30 && Math.abs(csj.A() - 180)%360 < 30;
		return gg;
	}

	public static void MoveXY(IVec2 v2, IVec v2_1, double E1) {
		// 移动到v2 包含E轴信息
		E0 = E1;

		OutSRCC.println("lin {x " + v2.x + ",y " + v2.y + ",a " + v2_1.x() + ",b " + v2_1.y()
				+ ",c " + v2_1.z() + ",E1 " + E1 + "} C_VEL");

	}

	public static void Move(IVec v2, IVec v2_1, double E1) {
		// 移动到v2 包含E轴信息
		E0 = E1;

		OutSRCC.println("lin {x " + v2.x + ",y " + v2.y + ",z " + v2.z + ",a " + v2_1.x() + ",b "
				+ v2_1.y() + ",c " + v2_1.z() + ",E1 " + E1 + "} C_VEL");

	}

	public static void Move(CS cs, double E1) {
		// 移动到v2 包含E轴信息
		E0 = E1;

		OutSRCC.println("lin {x " + cs.Vbase().x() + ",y " + cs.Vbase().y() + ",z "
				+ cs.Vbase().z() + ",a " + cs.Angle().x() + ",b " + cs.Angle().y() + ",c "
				+ cs.Angle().z() + ",E1 " + E1 + "} C_VEL");

	}

	public static void Move(CS cs) {
		// 移动到v2 包含E轴信息

		OutSRCC.println("lin {x " + cs.Vbase().x() + ",y " + cs.Vbase().y() + ",z "
				+ cs.Vbase().z() + ",a " + cs.Angle().x() + ",b " + cs.Angle().y() + ",c "
				+ cs.Angle().z() + "} C_VEL");

	}

	public static void MoveXY(IVec2 v2, IVec v2_1) {
		// 移动到v2 不包含E轴信息

		OutSRCC.println("lin {x " + v2.x + ",y " + v2.y + ",a " + v2_1.x() + ",b " + v2_1.y()
				+ ",c " + v2_1.z() + "} C_VEL");
	}

	public static void MoveXY(int i, ArrayList<CS> list, double E1) {
		// 移动到v2 不包含E轴信息

		OutSRCC.println("lin {x " + list.get(i).Vbase().x() + ",y " + list.get(i).Vbase().y()
				+ ",a " + list.get(i).Angle().x() + ",b " + list.get(i).Angle().y() + ",c "
				+ list.get(i).Angle().z() + ",E1 " + E1 + "} C_VEL");
	}

	public static void Move(int i, ArrayList<CS> list, double E1) {
		// 移动 包含E轴移动
		E0 = E1;
		if (i == 0) {
			OutSRCC.println("ptp {x " + (list.get(0).Vbase()).x() + ",y "
					+ (list.get(0).Vbase()).y() + ",z " + (list.get(0).Vbase().z() - 100) + ",a "
					+ list.get(0).Angle().x() + ",b " + list.get(0).Angle().y() + ",c "
					+ list.get(0).Angle().z() + ",s " + ss + ",t " + tt + ",E1 " + E1 + "} C_DIS");
		} else {
			OutSRCC.println("lin {x " + list.get(i).Vbase().x() + ",y " + list.get(i).Vbase().y()
					+ ",z " + list.get(i).Vbase().z() + ",a " + list.get(i).Angle().x() + ",b "
					+ list.get(i).Angle().y() + ",c " + list.get(i).Angle().z() + ",E1 " + E1
					+ "} C_VEL");
		}
	}

	public void Move(int i, ArrayList<CS> list) {
		// 移动 不包含E轴移动
		if (i == 0)
			OutSRCC.println("ptp {x " + (list.get(0).Vbase()).x() + ",y "
					+ (list.get(0).Vbase()).y() + ",z " + (list.get(0).Vbase().z() - 100) + ",a "
					+ list.get(0).Angle().x() + ",b " + list.get(0).Angle().y() + ",c "
					+ list.get(0).Angle().z() + ",s " + ss + ",t " + tt + "} C_DIS");
		else {
			OutSRCC.println("lin {x " + list.get(i).Vbase().x() + ",y " + list.get(i).Vbase().y()
					+ ",z " + list.get(i).Vbase().z() + ",a " + list.get(i).Angle().x() + ",b "
					+ list.get(i).Angle().y() + ",c " + list.get(i).Angle().z() + "} C_VEL");

		}

	}

	private static IVec deltY(IVec v) {
		// 补偿E1对y的影响
		IVec vv;
		vv = new IVec(v.x(), v.y(), v.z());
		vv.y = v.y() - (E1 - 1000) * 2.5f / 860f;

		return vv;
	}

	private static IVec deltX(IVec v) {
		// 补偿E1对x的影响
		IVec vv;
		vv = new IVec(v.x(), v.y(), v.z());
		vv.x = v.x() - (E1 - 1000) * 2f / 1800f;

		return vv;
	}

	private static double eTox(double e) {
		// E坐标对应相对坐标上x轴位置
		return e - base.X();
	}

	private static double xToe(double x) {
		// 相对坐标上x轴位置对应E坐标
		return x + base.X();
	}

	private static IVec basePos(double e) {
		// 机械手的基座位置的相对坐标
		return new IVec(eTox(e), -base.Y(), 300);
	}

	private static IVec RealPos(CS cs, boolean w){
		//cs在世界坐标中的绝对位置
		if(!w){//cs为相对基座标的位置
			return cs.Vbase().dup().add(base.Vbase());
			
		}
		else
			return cs.Vbase().dup();
	}
	
	private static boolean onTrail(CS cs, double E){
		//cs为相对坐标
		//判断cs是否在机械手的轨道上方且低于一定高度
		
		if(Math.abs(cs.Y()-basePos(E).y())<500 && cs.Z()<1000){
			return true;
		}
		else return false;
		
		
	}
	
	private static void updateList(ArrayList<CS> list, boolean b, boolean t) {
		// 根据tool，base坐标参数更新list中的坐标为机器码中的坐标
		// 需要测试验证
		// b 是否相对于base坐标
		// t 是否相对于工具坐标
		// 更新ABC数据

		
		for (int i = 0; i < list.size(); i++) {
			CS cs = list.get(i).subNew(new CS());
			list.set(i, cs);
		}
		

		if (!b && !t)
			for (int i = 0; i < list.size(); i++) {
				CS cs = list.get(i).subNew(base).addNew(tool);
				list.set(i, cs);
			}
		else if (!t && b)
			for (int i = 0; i < list.size(); i++) {
				CS cs = list.get(i).addNew(tool);

				list.set(i, cs);
			}
		else if (t && !b) {
			for (int i = 0; i < list.size(); i++) {
				CS cs = list.get(i).subNew(base);

				list.set(i, cs);
			}
		}
		

	}
}
