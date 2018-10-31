import java.util.Scanner;
import java.lang.Math;

public class elipsy{

public static boolean overlap(double a, double b, double[] C1, double[] C2, double alpha)
{
	double G=2+Math.pow((a/b-b/a),2)*Math.pow(Math.sin(alpha),2);
	double[] r={(C2[0]-C1[0]),(C2[1]-C1[1])};
	double[] u1={1,0};
	double[] u1prim={0,1};
	double f1=1+G-Math.pow(((r[0]*u1[0]+r[1]*u1[1])/a), 2)-Math.pow(((r[0]*u1prim[0]+r[1]*u1prim[1])/b), 2);
	double[] u2={Math.cos(alpha),Math.sin(alpha)};
	double[] u2prim={Math.sin(alpha),Math.cos(alpha)};
	double f2=1+G-Math.pow(((r[0]*u2[0]+r[1]*u2[1])/a), 2)-Math.pow(((r[0]*u2prim[0]+r[1]*u2prim[1])/b), 2);
	double psi=4*(Math.pow(f1, 2)-3*f2)*(Math.pow(f2, 2)-3*f1)-Math.pow((9-f1*f2),2);
	if ((psi>0)&&((f1<0)||(f2<0)))
		return false;
	else
		return true;
}
	
public static void main(String[] args) throws Exception
	{
	Scanner in = new Scanner(System.in);
	System.out.println("Aby sprawdziæ, czy elipsy siê przecinaj¹ podaj d³ugoœæ d³u¿szej pó³osi.");
	double dluga=in.nextDouble();
	System.out.println("A teraz krótszej.");
	double krotka=in.nextDouble();
	System.out.println("Podaj miarê k¹ta pomiêdzy prostymi na których znajduj¹ siê d³u¿sze pó³osie elips w radianach.");
	double kat=in.nextDouble();
	System.out.println("Teraz podaj pierwsz¹ ze wspó³rzêdnych œrodka pierwszej elipsy.");
	double a1=in.nextDouble();
	System.out.println("A teraz drug¹ wspó³rzêdn¹.");
	double a2=in.nextDouble();
	double[] w1= {a1, a2};
	System.out.println("Teraz podaj wspó³rzêdne œrodka drugiej elipsy. Zacznij od pierwszej");
	a1=in.nextDouble();
	System.out.println("Teraz podaj drug¹.");
	a2=in.nextDouble();
	double[] w2= {a1, a2};
	boolean czy=overlap(dluga, krotka, w1, w2, kat);
	if(!czy)
			System.out.println("Elipsy s¹ roz³¹czne.");
	else
			System.out.println("Elipsy s¹ styczne lub przecinaj¹ siê.");
	in.close();
	}
}
