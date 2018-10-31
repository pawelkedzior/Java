import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BD {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:sample.db");
		wypelnij(c);
		wez(c);
		c.close();
	}
	
	public static void wypelnij(Connection c) throws SQLException{
		Statement s=c.createStatement();
		s.executeUpdate("DROP TABLE Tabela;");
		s.executeUpdate("CREATE TABLE Tabela (id INT, atr VARCHAR(32) );");
		for (int i=0;i<10;i++){
			s.executeUpdate("INSERT INTO Tabela VALUES ("+i+", 'cośtam_"+i+"');");			
		}
		s.close();
	}
	
	public static void wez(Connection c) throws SQLException{
		Statement s=c.createStatement();
		ResultSet rs=s.executeQuery("SELECT * FROM Tabela WHERE id=3;");
		while(rs.next()){
			System.out.println(rs.getInt("id")+"\t"+rs.getString("atr"));
		}		
		rs.close();
		s.close();		
	}
}
//Książka telefoniczna+dodawanie wpisu+wyszukiwanie po nazwisku+interfejs graficzny