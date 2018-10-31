import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.lang.Integer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Enumeration;
	
public class KIK extends JPanel implements ActionListener {

	private static final long serialVersionUID = -3857093034446144900L;
	protected JFrame ramka, Nowyakcja;
	protected JButton przyc;
	protected BufferedImage p;
	protected BufferedImage kk;
	protected BufferedImage kr;
	protected Icon pusty=new ImageIcon("zdj/pusty.jpg");
	protected Icon krzy=new ImageIcon("zdj/krzy.jpg");
	protected Icon kol=new ImageIcon("zdj/kol.jpg");
	protected Icon teraz=krzy;
	protected Icon nieJa=kol;
	protected int wyniki[][]=new int[3][3];
	protected JTextField pole, pole2;
	protected ButtonGroup grupaPers, grupaSiec, grupa;
	protected boolean czyGosc=false;
	protected boolean kko=false;
	protected boolean czykolej=true;
	Thread t;
	ServerSocket ss;
	Socket s;
	String IP="";
	int port=0;
	PrintWriter dO;
	BufferedReader Od;
	
	
    public KIK() {
        super(new GridBagLayout());
        try{
        p=ImageIO.read(this.getClass().getResourceAsStream("zdj/pusty.jpg"));
        kk=ImageIO.read(this.getClass().getResourceAsStream("zdj/kol.jpg"));
        kr=ImageIO.read(this.getClass().getResourceAsStream("zdj/krzy.jpg"));
        pusty=new ImageIcon(p);
        kol=new ImageIcon(kk);
        krzy=new ImageIcon(kr);
        nieJa=kol;
        teraz=krzy;
        }catch(IOException e){
        	e.printStackTrace();
        }
		GridLayout gl=new GridLayout(3, 3, 2, 2);
		JPanel Panel = new JPanel(gl);
		grupa=new ButtonGroup();
		for (int i=11;i<20;i++){
			String etykietka;
			etykietka=new String(Integer.toString(i));
			przyc=new JButton(pusty);
				przyc.setPreferredSize(new Dimension (50,50));
 		        przyc.setEnabled(false);
				przyc.addActionListener(this);
				przyc.setActionCommand(etykietka);
			grupa.add(przyc);
			Panel.add(przyc);
		}
		add(Panel);
    }
    
    public JMenuBar tworzMenu(){
    	JMenuBar pasekMenu;
        JMenu gra;
        JMenuItem wiersz;
        pasekMenu = new JMenuBar();        
        
        gra = new JMenu("Gra");
        pasekMenu.add(gra);
        wiersz = new JMenuItem("Ustawienia", KeyEvent.VK_U);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
    		wiersz.getAccessibleContext().setAccessibleDescription("Ustawienia gry");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("1");
    	gra.add(wiersz);	
        wiersz = new JMenuItem("Rozpocznij", KeyEvent.VK_R);
        	wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        	wiersz.getAccessibleContext().setAccessibleDescription("Rozpoczyna grę");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("2");
        gra.add(wiersz);
    	gra.addSeparator();
    	wiersz = new JMenuItem("Wyjdź", KeyEvent.VK_W);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Wychodzi z gry");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("3");
		gra.add(wiersz);
		return pasekMenu;
    }

    public void actionPerformed(ActionEvent wyd) {        
        switch (Integer.parseInt(wyd.getActionCommand())){
        	case 1:
        	{
        		Nowyakcja=new JFrame("Ustawienia gry");
        		Nowyakcja.setLayout(new BorderLayout());
        		JPanel Panel2 =new JPanel(new GridLayout(3, 1));
        		JPanel Panel = new JPanel(new GridLayout(3, 1));
        		JLabel et;
        		et=new JLabel("Personalizacja");
        		Panel.add(et);        		
        		JRadioButton kolko,krzyzyk;
        		krzyzyk=new JRadioButton("Gram krzyżykami");
        		if(!kko)
        			krzyzyk.setSelected(true);
        		kolko=new JRadioButton("Gram kółkami");
        		if(kko)
        			kolko.setSelected(true);
                krzyzyk.addActionListener(this);
                kolko.addActionListener(this);
                krzyzyk.setActionCommand("4");
                kolko.setActionCommand("5");
        		Panel.add(krzyzyk);
        		Panel.add(kolko);
        		
        		grupaPers = new ButtonGroup();
            	grupaPers.add(krzyzyk);
            	grupaPers.add(kolko);
        		
        		et=new JLabel("Tryb sieciowy");
        		Panel2.add(et);        		
        		JRadioButton gosp,gosc;
        		gosp=new JRadioButton("Zakładam grę");
        		if(!czyGosc)
        			gosp.setSelected(true);
        		gosc=new JRadioButton("Dołączam do gry");
        		if(czyGosc)
        			gosc.setSelected(true);
                gosp.addActionListener(this);
                gosc.addActionListener(this);
                gosp.setActionCommand("6");
                gosc.setActionCommand("7");
        		Panel2.add(gosp);
        		Panel2.add(gosc);
        		
        		grupaSiec = new ButtonGroup();
            	grupaSiec.add(gosp);
            	grupaSiec.add(gosc);
        		
        		Nowyakcja.add(Panel, BorderLayout.LINE_START);
        		Nowyakcja.add(Panel2, BorderLayout.LINE_END);
        		
        		Panel = new JPanel(new GridLayout(3, 2));  
        		et=new JLabel("Adres IP");
        		Panel.add(et);
        		pole=new JTextField(IP);
        		if(!czyGosc)
        			pole.setEnabled(false);
        		Panel.add(pole);
        		et=new JLabel("Port");
        		Panel.add(et);
        		if(port!=0)
        			pole2=new JTextField(Integer.toString(port));
        		else
        			pole2=new JTextField();
        		Panel.add(pole2);
        		et=new JLabel("");
        		Panel.add(et);
        		JButton przyc;
        		przyc=new JButton("Zapamiętaj ustawienia");
    				przyc.addActionListener(this);
    				przyc.setActionCommand("8");
        		Panel.add(przyc);
        		Nowyakcja.add(Panel, BorderLayout.SOUTH);
        		Nowyakcja.pack();
                Nowyakcja.setLocationRelativeTo(ramka);
        		Nowyakcja.setVisible(true);
        	}
        	break;
        	case 2:
        	{
        		for (Enumeration<AbstractButton> e = grupa.getElements(); e.hasMoreElements();)
        		{
        		       przyc=(JButton) e.nextElement();
        		       przyc.setIcon(pusty);
        		       przyc.setEnabled(true);
        		}
        		for (int i=0;i<9;i++){
            		int x=i%3;
            		int y=(i-x)/3;
            		wyniki[x][y]=0;
        		}
        		boolean czyGrano=false;
        		try
        		{
        			czyGrano=s.isConnected();
        		}
            	catch(NullPointerException ex){
            	}finally
            	{
        			if(!czyGrano){
        				if(!czyGosc){
        					try{
        						ss = new ServerSocket(port);
        						System.out.println(ss.getInetAddress()+" "+ss.getLocalPort());
        						s = ss.accept();        				
        						dO = new PrintWriter(s.getOutputStream(), true);
        						Od = new BufferedReader(new InputStreamReader(s.getInputStream()));
        						odbierzRuch();
        					}catch(IOException e){
        						e.printStackTrace();
        					}
        				}else{
        					try{
        						s = new Socket(IP, port);
        						dO = new PrintWriter(s.getOutputStream(), true);
        						Od = new BufferedReader(new InputStreamReader(s.getInputStream()));
        						odbierzRuch();
        					}catch(UnknownHostException e){
        					}catch(IOException e){
        						e.printStackTrace();
        					}
        				}
        			}
        		}
        	}
        	break;
        	case 3:
        	{
        		System.exit(0);
        	}
        	break;
        	case 4:
        	{
        		teraz=krzy;
        		nieJa=kol;
        		kko=false;
        	}
        	break;
        	case 5:
        	{
        		teraz=kol;
        		nieJa=krzy;
        		kko=true;
        	}
        	break;
        	case 6:
        	{
        		pole.setEnabled(false);
        		czykolej=true;
        		czyGosc=false;
        	}
        	break;
        	case 7:
        	{
        		pole.setEnabled(true);
        		czykolej=false;
        		czyGosc=true;
        	}
        	break;
        	case 8:
        	{  
        		if(czyGosc)
        			IP=pole.getText();
        		try{
        			if(!pole2.getText().equals(""))
        				port=Integer.parseInt(pole2.getText());
        		}catch(NullPointerException e){
        		}catch(NumberFormatException e){
        		}
        		Nowyakcja.setVisible(false);
        	}
        	break;
        }
        if(Integer.parseInt(wyd.getActionCommand())<20&&Integer.parseInt(wyd.getActionCommand())>10){
    		JButton bla=(JButton) wyd.getSource();
    		int blokada=new Integer(Integer.parseInt(wyd.getActionCommand()));
			blokada-=11;
			int x=blokada%3;
			int y=(blokada-x)/3;
        	if(czykolej){
        		if(wyniki[x][y]==0){
        			bla.setIcon(teraz);        			
        			wyniki[x][y]=1;
        			wyslijRuch(blokada);
        			czykolej=false;        			
        			sprawdz(x,y,false);
        		}
        	}
        }
    }
    
    private static void StwIPokGUI() {

        KIK kik= new KIK();
        JFrame ramka = new JFrame("Kółko i krzyżyk");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramka.setJMenuBar(kik.tworzMenu());
        ramka.setSize(400, 400);
        ramka.add(kik);
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
    }
    
    public void wyslijRuch(int wyslij){
    	String dowysl=new String(Integer.toString(wyslij));
    	dO.println(dowysl);
    }
    
    public void odbierzRuch(){
    	t = new Thread(new Runnable() {			
			public void run() {
				while(true)
		    	{
		    		try{
		    			String odgracza=Od.readLine();
		    			try{
		    			if(!odgracza.isEmpty()){
		    				int blokada=new Integer(Integer.parseInt(odgracza));
		    				int x=blokada%3;
		    				int y=(blokada-x)/3;
		    				wyniki[x][y]=2;
		    				for (Enumeration<AbstractButton> e = grupa.getElements(); e.hasMoreElements();)
		    				{
		    					JButton jakiPrzyc;
		    					jakiPrzyc=(JButton) e.nextElement();
		    					if(jakiPrzyc.getActionCommand().equals(Integer.toString(blokada+11))){
		    						jakiPrzyc.setIcon(nieJa);
		    						break;
		    					}
		    				}
		    				sprawdz(x,y,true);
		    				czykolej=true;
		    			}
		    			}catch(NullPointerException e){
		    			}
		    		}catch(IOException e){
		    		}catch(NumberFormatException e){
	    			} 
		    	}
			}
		});	
		t.start();   
    }
    
    public void sprawdz(int x,int y,boolean przec){
    	int czywygr=0;
    	int kto;
    	if(przec)
    		kto=2;
    	else
    		kto=1;
    	
    	//Poziom
    	int temp=x;
    	while(wyniki[temp][y]==kto){
    		czywygr++;
    		if(temp!=2)
    			temp++;
    		else
    			break;
    	}
    	temp=x-1;
    	if(temp>-1)
    	while(wyniki[temp][y]==kto){
    		czywygr++;
    		if(temp!=0)
    			temp--;
    		else
    			break;
    	}
    	if(czywygr>3){
    		if(przec){
    			WYGRANA();
    			return;
    		}else{
    			przegrana();
    			return;
    		}
    	}else if(czywygr==3){
    		if(!przec){
    			WYGRANA();
    			return;
    		}else{
    			przegrana();
    			return;
    		}
    	}
    	
    	//Pion
    	temp=y;
    	czywygr=0;
    	while(wyniki[x][temp]==kto){
    		czywygr++;
    		if(temp!=2)
    			temp++;
    		else
    			break;
    	}
    	temp=y-1;
    	if(temp>-1)
    	while(wyniki[x][temp]==kto){
    		czywygr++;
    		if(temp!=0)
    			temp--;
    		else
    			break;
    	}
    	if(czywygr>3){
    		if(przec){
    			WYGRANA();
    			return;
    		}else{
    			przegrana();
    			return;
    		}
    	}else if(czywygr==3){
    		if(!przec){
    			WYGRANA();
    			return;
    		}else{
    			przegrana();
    			return;
    		}
    	}
    	
    	//Skos malejąco
    	temp=0;
    	czywygr=0;
    	while(wyniki[x+temp][y+temp]==kto){
    		czywygr++;
    		if((x+temp)!=2&&(y+temp)!=2)
    			temp++;
    		else
    			break;
    	}
    	temp=1;
    	if(x-temp>-1&&y-temp>-1)
    	while(wyniki[x-temp][y-temp]==kto){
    		czywygr++;
    		if((x-temp)!=0&&(y-temp)!=0)
    			temp++;
    		else
    			break;
    	}
    	if(czywygr>3){
    		if(przec){
    			WYGRANA();
    			czykolej=true;
    			return;
    		}else{
    			przegrana();
    			czykolej=false;
    			return;
    		}
    	}else if(czywygr==3){
    		if(!przec){
    			WYGRANA();
    			czykolej=true;
    			return;
    		}else{
    			przegrana();
    			czykolej=false;
    			return;
    		}
    	}
    	
    	//Skos rosnąco
    	temp=0;
    	czywygr=0;
    	while(wyniki[x+temp][y-temp]==kto){
    		czywygr++;
    		if((x+temp)!=2&&(y-temp)!=0)
    			temp++;
    		else
    			break;
    	}
    	temp=1;
    	if(x-temp>-1&&y+temp<3)
    	while(wyniki[x-temp][y+temp]==kto){
    		czywygr++;
    		if((x-temp)!=0&&(y+temp)!=2)
    			temp++;
    		else
    			break;
    	}
    	if(czywygr>3){
    		if(przec){
    			WYGRANA();
    			return;
    		}else{
    			przegrana();
    			return;
    		}
    	}else if(czywygr==3){
    		if(!przec){
    			WYGRANA();
    			return;
    		}else{
    			przegrana();
    			return;
    		}
    	}
    	
    	boolean czyRemis=true;
    	for (int i=0;i<9;i++){
    		x=i%3;
    		y=(i-x)/3;
    		if(wyniki[x][y]==0){
    			czyRemis=false;
    			break;
    		}
    	}
    	if(czyRemis){
    		remis();
    	}
    	return;
    }
    
	public void WYGRANA(){
  	  	JOptionPane.showMessageDialog(ramka,"Gratuluję! Wygrałeś!");
  	  	for (Enumeration<AbstractButton> e = grupa.getElements(); e.hasMoreElements();)
		{
		       przyc=(JButton) e.nextElement();
		       przyc.setEnabled(false);
		}
  	  	try{
  	  	KONIEC("Wygrana");
  	  	}catch(SQLException e){
  	  		e.printStackTrace();
	  	}catch(ClassNotFoundException e){
	  	}
    }
    
	public void przegrana(){
    	  JOptionPane.showMessageDialog(ramka,"Niestety... Przegrałeś.");
    	  	for (Enumeration<AbstractButton> e = grupa.getElements(); e.hasMoreElements();)
    		{
    		       przyc=(JButton) e.nextElement();
    		       przyc.setEnabled(false);
    		}
    	  	try{
    	  	KONIEC("Przegrana");
			}catch(SQLException e){
			}catch(ClassNotFoundException e){
			}
    }
	public void remis(){
  	  JOptionPane.showMessageDialog(ramka,"Remis.");
  	  	for (Enumeration<AbstractButton> e = grupa.getElements(); e.hasMoreElements();)
  		{
  		       przyc=(JButton) e.nextElement();
  		       przyc.setEnabled(false);
  		}
  	  	try{
  	  	KONIEC("Remis");
  	  	}catch(SQLException e){
  	  	}catch(ClassNotFoundException e){
  	  	}
	}
	
	public void KONIEC(String czyWygralem) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		Connection c = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement instr=c.createStatement();
			instr.executeUpdate("DROP TABLE Tabela;");
			instr.executeUpdate("CREATE TABLE Tabela (Lp INT, Status VARCHAR(32), "
					           + "x1y1 VARCHAR(4), x2y1 VARCHAR(4), x3y1 VARCHAR(4), "
					           + "x1y2 VARCHAR(4), x2y2 VARCHAR(4), x3y2 VARCHAR(4), "
					           + "x1y3 VARCHAR(4), x2y3 VARCHAR(4), x3y3 VARCHAR(4));");
			String dodajInstr="INSERT INTO Tabela VALUES (";
				ResultSet rs=instr.executeQuery("SELECT MAX(Lp) FROM Tabela;");
				if(rs.next())
					dodajInstr=dodajInstr+(Integer.toString(rs.getInt("MAX(Lp)")+1));
				else{
					dodajInstr=dodajInstr+"1";
				}
				rs.close();
			dodajInstr=dodajInstr+", '"+czyWygralem;
			for (int i=0;i<9;i++){
				int x=i%3;
				int y=(i-x)/3;
				if(wyniki[x][y]==1)
					dodajInstr=dodajInstr+"', 'Ja";
				else if(wyniki[x][y]==2)
						dodajInstr=dodajInstr+"', 'On";
					else
						dodajInstr=dodajInstr+"', 'Nikt";
			}
			dodajInstr=dodajInstr+"');";
			instr.executeUpdate(dodajInstr);
			instr.close();			
		c.close();
	}
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StwIPokGUI();
            }
        });
    }
}
