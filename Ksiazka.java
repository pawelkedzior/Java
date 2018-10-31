import java.sql.*;

public class Ksiazka {
	
	static public void dodaj(String naz, String im, String msc, String num) throws Exception{
		Connection c = null;
	  	c = DriverManager.getConnection("jdbc:sqlite:test.db");
	  	Statement s=c.createStatement();
	  	s.executeUpdate("INSERT INTO Ksiazka VALUES('"+naz+"','"+im+"','"+msc+"',"+num+");");
	  	s.close();
	  	c.close();
	  	}

	static public Object[][] wyszukaj(String nazwisko, String imie, int co) throws Exception{
  		Connection c = null;
	  	c = DriverManager.getConnection("jdbc:sqlite:test.db");
	  	Statement s=c.createStatement();
	  	ResultSet rs=s.executeQuery("SELECT * FROM Ksiazka;");
		Object[][] dane=null;
		int i=new Integer(0);
		switch(co)
		{
			case 1:
			{
			  	rs = s.executeQuery("SELECT * FROM Ksiazka WHERE Nazwisko='"+nazwisko+"';");
			  	while(rs.next())
			  	{
			         i++;
			  	}
			  	dane=new Object[i][4];
			  	i=0;
			  	rs = s.executeQuery("SELECT * FROM Ksiazka WHERE Nazwisko='"+nazwisko+"';");
			  	while(rs.next())
			  	{
			  		 String nazw = rs.getString("Nazwisko");
			         String imi = rs.getString("Imie");
			         String miejsc  = rs.getString("Miejscowosc");
			         int numer = rs.getInt("Numer");
			         dane[i][0]=nazw;
			         dane[i][1]=imi;
			         dane[i][2]=miejsc;
			         dane[i][3]=numer;
			         i++;
			  	}			
			}
			break;
			case 2:
			{
			  	rs = s.executeQuery("SELECT * FROM Ksiazka WHERE Imie='"+imie+"';");
			  	while(rs.next())
			  	{
			         i++;
			  	}
			  	dane=new Object[i][4];
			  	i=0;
			  	rs = s.executeQuery("SELECT * FROM Ksiazka WHERE Imie='"+imie+"';");
			  	while(rs.next())
			  	{
			  		 String nazw = rs.getString("Nazwisko");
			         String imi = rs.getString("Imie");
			         String miejsc  = rs.getString("Miejscowosc");
			         int numer = rs.getInt("Numer");
			         dane[i][0]=nazw;
			         dane[i][1]=imi;
			         dane[i][2]=miejsc;
			         dane[i][3]=numer;
			         i++;
			  	}	
			}
			break;
			case 3:
			{
			  	rs = s.executeQuery("SELECT * FROM Ksiazka WHERE Imie='"+imie+"' AND Nazwisko='"+nazwisko+"';");
			  	while(rs.next())
			  	{
			         i++;
			  	}
			  	dane=new Object[i][4];
			  	i=0;
			  	rs = s.executeQuery("SELECT * FROM Ksiazka WHERE Imie='"+imie+"' AND Nazwisko='"+nazwisko+"';");	
			  	while(rs.next())
			  	{
			  		 String nazw = rs.getString("Nazwisko");
			         String imi = rs.getString("Imie");
			         String miejsc  = rs.getString("Miejscowosc");
			         int numer = rs.getInt("Numer");
			         dane[i][0]=nazw;
			         dane[i][1]=imi;
			         dane[i][2]=miejsc;
			         dane[i][3]=numer;
			         i++;
			  	}
			}
			break;
		}
	  	rs.close();
		s.close();
		c.close();
		return dane;
	}
	
  public static void main ( String args[] ) throws Exception
  	{
	  	Class.forName("org.sqlite.JDBC");
	  	Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
	  	Object[][] dane;
	  	Statement s=c.createStatement();
	  	ResultSet rs = s.executeQuery("SELECT * FROM Ksiazka;");
	  	int i=new Integer(0);
	  	while(rs.next())
	  	{
	         i++;
	  	}
	  	dane=new Object[i][4];
	  	rs = s.executeQuery("SELECT * FROM Ksiazka;");
	  	i=0;
	  	while(rs.next())
	  	{
	         String nazw = rs.getString("Nazwisko");
	         String imie = rs.getString("Imie");
	         String miejsc  = rs.getString("Miejscowosc");
	         int numer = rs.getInt("Numer");
	         dane[i][0]=nazw;
	         dane[i][1]=imie;
	         dane[i][2]=miejsc;
	         dane[i][3]=numer;
	         i++;
	  	}
	  	rs.close();
	  	s.close();
	  	c.close();
    	Aplikacja.daneBezwzgl=dane.clone();
	  	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Aplikacja.StwIPokGUI(dane);
            }
        });
	}
}
