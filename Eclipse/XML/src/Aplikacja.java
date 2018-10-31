import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.util.GregorianCalendar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class Aplikacja extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static Object blokada;
	protected static JFrame ramka;
	protected static JMenuItem wierszUst;
	protected static JTextPane tPrzest;
	protected static StyledDocument dok;
	protected static JTextArea tPrzest2;
	protected static JButton przyc;
	protected static ButtonGroup grupaSiec;
	protected static JFrame Nowyakcja;
    protected static JTextField tPole, tPole1, tPole2;
    protected static boolean czyGosc=true;
    protected static boolean czyWysylac=false;
    protected static int port=5555;
    protected static String IP="localhost";
    protected static String pseudo="";
    protected static String wiadomoscDoWysl="";
    protected static String nazwapliku="";
    protected static ServerSocket ss;
	protected static ArrayList<Socket> sock;
	protected static GregorianCalendar teraz;
	protected LinkedList<String> pseudonimy;
	protected LinkedList<Boolean> doWys;
	Thread t;  
	
    public Aplikacja() {
        super(new GridBagLayout());
        tPrzest = new JTextPane();
        tPrzest2 = new JTextArea(8, 50);
        tPrzest.setPreferredSize(new Dimension(300, 400));
        tPrzest.setMargin(new Insets(0,0,0,5));
        dok = tPrzest.getStyledDocument();
        ustawStyle(dok);
        tPrzest.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(tPrzest);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.VERTICAL;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 5.0;
        c.weighty = 5.0;
        add(scrollPane, c);
		GridLayout gl=new GridLayout(1, 5, 10, 10);
        JPanel panel=new JPanel(gl);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
    		przyc=new JButton("Zapisz");
    		przyc.setPreferredSize(new Dimension(80,30));
    		przyc.addActionListener(this);
    		przyc.setActionCommand("3");
		panel.add(przyc);
			przyc=new JButton("Archiwum");
    		przyc.setPreferredSize(new Dimension(50,20));
    		przyc.addActionListener(this);
    		przyc.setActionCommand("4");
		panel.add(przyc);
    		przyc=new JButton("Czyść");
    		przyc.setPreferredSize(new Dimension(80,30));
    		przyc.addActionListener(this);
    		przyc.setActionCommand("5");
    	panel.add(przyc);
        add(panel, c);
        tPrzest2.setEditable(true);
        tPrzest2.addKeyListener(new WciskamEnter());
		tPrzest2.setEnabled(false);
        tPrzest2.setLineWrap(true);
        scrollPane = new JScrollPane(tPrzest2);
        add(scrollPane, c);
        panel=new JPanel(gl);
        	panel.add(new JLabel(""));
        	panel.add(new JLabel(""));
        	panel.add(new JLabel(""));
        	panel.add(new JLabel(""));
    		przyc=new JButton("Wyślij");
    		przyc.setPreferredSize(new Dimension(80,30));
    		przyc.addActionListener(this);
    		przyc.setActionCommand("8");
    	panel.add(przyc);
        add(panel, c);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }
    
	private JMenuBar tworzMenu(){
    	JMenuBar pasekMenu;
        JMenu plik;
        JMenuItem wiersz;
        pasekMenu = new JMenuBar();

//Menu "Rozmowa"
        
        plik = new JMenu("Rozmowa");
        pasekMenu.add(plik);
        wierszUst = new JMenuItem("Ustawienia", KeyEvent.VK_U);
    		wierszUst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
            wierszUst.addActionListener(this);
            wierszUst.setActionCommand("1");
    	plik.add(wierszUst);
		plik.addSeparator();	
        wiersz = new JMenuItem("Rozpocznij", KeyEvent.VK_R);
        	wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
            wiersz.addActionListener(this);
            wiersz.setActionCommand("2");
        plik.add(wiersz);
        wiersz = new JMenuItem("Zapisz", KeyEvent.VK_Z);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
            wiersz.addActionListener(this);
            wiersz.setActionCommand("3");
    	plik.add(wiersz);
    	wiersz = new JMenuItem("Archiwum", KeyEvent.VK_A);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
            wiersz.addActionListener(this);
            wiersz.setActionCommand("4");
		plik.add(wiersz);	
        wiersz = new JMenuItem("Czyść", KeyEvent.VK_C);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    		wiersz.addActionListener(this);
    		wiersz.setActionCommand("5");
    	plik.add(wiersz);
    	plik.addSeparator();	
        wiersz = new JMenuItem("Wyjdź z pokoju", KeyEvent.VK_P);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    		wiersz.addActionListener(this);
    		wiersz.setActionCommand("6");
    	plik.add(wiersz);
    	plik.addSeparator();
    	wiersz = new JMenuItem("Zakończ", KeyEvent.VK_K);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
            wiersz.addActionListener(this);
            wiersz.setActionCommand("7");
		plik.add(wiersz);
		return pasekMenu;
    }

    private void ustawStyle(StyledDocument dok) {
    	Style domysl = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

    	Style zwykly = dok.addStyle("zwykły", domysl);
    	//StyleConstants.setFontFamily(domysl, "SansSerif");
    	
    	Style s = dok.addStyle("kursywa", zwykly);
    	StyleConstants.setItalic(s, true);

    	s = dok.addStyle("pogrubienie", zwykly);
    	StyleConstants.setBold(s, true);
	}
	
    private Color jakiKolor(String ps){
    	int numer=pseudonimy.indexOf(ps)%10;
    	Color kolor=new Color(0);
    	switch(numer)
    	{
    		case 0:{
    			kolor=Color.BLACK;
    		}
    		break;
    		case 1:{
    			kolor=Color.BLUE;
    		}
    		break;
    		case 2:{
    			kolor=Color.CYAN;
    		}
    		break;
    		case 3:{
    			kolor=Color.GREEN;
    		}
    		break;
    		case 4:{
    			kolor=Color.MAGENTA;
    		}
    		break;
    		case 5:{
    			kolor=Color.ORANGE;
    		}
    		break;
    		case 6:{
    			kolor=Color.PINK;
    		}
    		break;
    		case 7:{
    			kolor=Color.LIGHT_GRAY;
    		}
    		break;
    		case 8:{
    			kolor=Color.RED;
    		}
    		break;
    		case 9:{
    			kolor=Color.YELLOW;
    		}
    		break;
    	}
    	return kolor;
    }
    
    private Style ustawKolor(Style s, String ps){
    	Style nowy=s;
    	String zmien=ps.substring(1,ps.length()-2);
    	StyleConstants.setForeground(nowy, jakiKolor(zmien));
    	return nowy;    	
    }
    
    private void Ustawienia(){
		Nowyakcja=new JFrame("Ustawienia czatu");
		Nowyakcja.setLayout(new BorderLayout());
		JPanel Panel2 =new JPanel(new GridLayout(3, 1));
		JPanel Panel = new JPanel(new GridLayout(3, 1));
		JLabel et;
		et=new JLabel("Personalizacja");
		Panel.add(et);
		et=new JLabel("Pseudonim:");
		Panel.add(et); 
		tPole2=new JTextField(17);
		tPole2.setText(pseudo);
		Panel.add(tPole2);
		
		et=new JLabel("Tryb sieciowy");
		Panel2.add(et);        		
		JRadioButton gosp,gosc;
		gosp=new JRadioButton("Zakładam pokój");
		if(!czyGosc)
			gosp.setSelected(true);
		gosc=new JRadioButton("Dołączam do pokoju");
		if(czyGosc)
			gosc.setSelected(true);
        gosp.addActionListener(this);
        gosc.addActionListener(this);
        gosp.setActionCommand("9");
        gosc.setActionCommand("10");
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
		tPole=new JTextField();
		tPole.setText(IP);
		if(!czyGosc)
			tPole.setEnabled(false);
		Panel.add(tPole);
		et=new JLabel("Port");
		Panel.add(et);
		tPole1=new JTextField();
		if(port!=0)
			tPole1.setText(Integer.toString(port));

		Panel.add(tPole1);
		et=new JLabel("");
		Panel.add(et);
		JButton przyc;
		przyc=new JButton("Zapamiętaj ustawienia");
			przyc.addActionListener(this);
			przyc.setActionCommand("11");
		Panel.add(przyc);
		Nowyakcja.add(Panel, BorderLayout.SOUTH);
		Nowyakcja.pack();
        Nowyakcja.setLocationRelativeTo(ramka);
		Nowyakcja.setVisible(true);
    }
    
    public class WciskamEnter implements KeyListener {

		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
			int klawisz=e.getKeyCode();
			int modyfikator=e.getModifiersEx();
			if(klawisz==10&&modyfikator==0){
				tPrzest2.setText("");
			}
		}

		public void keyPressed(KeyEvent e) {
			int klawisz=e.getKeyCode();
			if(klawisz==10){
				int modyfikator=e.getModifiersEx();
				if(modyfikator==0){
					if(!(tPrzest2.getText().equals("")))
	        		{
	        			String wiadomosc;
	        			teraz=new GregorianCalendar(new SimpleTimeZone(3600000,"GMT+1"),
	        					new Locale("pl"));
	        			wiadomosc=dataDoString(teraz)+" "+pseudo+":\t"+tPrzest2.getText();
        				wiadomosc=wiadomosc.substring(0,wiadomosc.length());
	        			wyslijWiad(wiadomosc);
	        			tPrzest2.setText("");
	        			if(!czyGosc){
	        				try{
	        				if(!tPrzest.getText().isEmpty())
	        					dok.insertString(dok.getLength(),"---------------------------------"
	        								   + "-------------------------------------------------"
	        								   + "-------------------------------------------------"
	        								   + "-----\n",dok.getStyle("zwykły"));
	        				String kopia=String.copyValueOf(wiadomosc.toCharArray());
	        				String inneForm[]=odczytajPola(kopia);
	        				for(int i=0;i<inneForm.length-1;i++)
	        					dok.insertString(dok.getLength(),inneForm[i]+(i==2?"\n":""),
	        						i!=1?dok.getStyle("kursywa"):ustawKolor(dok.getStyle("pogrubienie"),
	        								inneForm[i]));
	        				if(inneForm[2].contains("\n")){
	        					inneForm=inneForm[2].split("\n");
	        					int i=0;
	        					for(String s: inneForm){
	        						if(i==0){
	        							dok.insertString(dok.getLength(),s+"\n",dok.getStyle("zwykły"));
	        						}else{
	        							dok.insertString(dok.getLength(),"\t\t"+s+"\n",
	        									dok.getStyle("zwykły"));
	        						}
	        						i++;
	        					}
	        				}else{
	        					dok.insertString(dok.getLength(),inneForm[2]+"\n",
	            						dok.getStyle("zwykły"));
	        				}
	        				}catch(BadLocationException ex){	        					
	        				}
	        				tPrzest.setCaretPosition(tPrzest.getDocument().getLength());
	        			}
	        		}else
	        			tPrzest2.setText("");
				}
				else if(modyfikator==64)
				{
					tPrzest2.append("\n");
				}
			}			
		}
    }
    
    public void Zapisz(){
    	if((nazwapliku=="")/*||(!plikIstn(nazwapliku))*/){
    		ZapiszJako();
    	}else{
    		XML.Czat cz=new XML.Czat();
    		XML.spakujRozmowe(cz, nazwapliku);	
    	}
    }
    
    public void ZapiszJako(){
		Nowyakcja=new JFrame("Gdzie zapisać plik?");
		Nowyakcja.setLayout(new BorderLayout());
		JComponent.setDefaultLocale(new Locale("pl","PL"));
		JFileChooser wybieracz = new TlumaczJFC();
	    FileNameExtensionFilter filtr = new FileNameExtensionFilter("Pliki XML", "xml");
	    wybieracz.setFileFilter(filtr);
	   
	    //wybieracz.setApproveButtonText("Zapisz");
	    //wybieracz.setDialogTitle("Zapisz");
	    wybieracz.setAcceptAllFileFilterUsed(false);
	    int returnVal = wybieracz.showOpenDialog(Nowyakcja);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            wybieracz.getSelectedFile().getName());
	    }
		/*JPanel Panel = new JPanel(new GridLayout(2, 1));
		JPanel Panel2 = new JPanel(new GridLayout(2, 1));
		JLabel et;
		et=new JLabel("�cie�ka do pliku:");
			et.setLocation(25,25);
		Panel.add(et);
		tPole1=new JTextField(30);
		tPole1.setText("");
		Panel2.add(tPole1);
		et=new JLabel("Nazwa pliku:");
		Panel.add(et);
		tPole=new JTextField(30);
		tPole.setText("");
		Panel2.add(tPole);
		Nowyakcja.add(Panel, BorderLayout.LINE_START);
		Nowyakcja.add(Panel2, BorderLayout.LINE_END);
		Panel = new JPanel(new GridLayout(1, 4));
		Panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		JButton przyc;
		przyc=new JButton("Zapisz");
        	przyc.addActionListener(this);
        	przyc.setActionCommand("13");
		Panel.add(przyc);
		przyc=new JButton("Anuluj");
        	przyc.addActionListener(this);
        	przyc.setActionCommand("10");
		Panel.add(przyc);
		JLabel przyc1=new JLabel("");
			Panel.add(przyc1);
		JLabel przyc2=new JLabel("");
			Panel.add(przyc2);
		Nowyakcja.add(Panel, BorderLayout.SOUTH);*/
		Nowyakcja.pack();
		Nowyakcja.setVisible(true);
    }
	
    protected void wyslijWiad(String wiadomosc) {
    	String linijki[];
    	if(wiadomosc.contains("\n"))
    	{
    		linijki=wiadomosc.split("\n");		
    	}else{
    		linijki=new String[1];
    		linijki[0]=wiadomosc;
    	}
    	for(String wiadom: linijki){
    		synchronized(blokada){
    			wiadomoscDoWysl=wiadom;
    			if(!czyGosc)
    				for (int i = 0; i < doWys.size(); i++) {
    					doWys.set(i, true);
    				}
    			czyWysylac=true;
    			try{				
    					blokada.wait();    
    			}catch(InterruptedException e){
    			}
    		}
    	}
	}

    private boolean nieWyslano(){
    	for(Boolean b: doWys)
    		if(b)
    			return true;
    	return false;
    }
    
	public void odbierzWiadomosc(Socket s, int numer){
    	t = new Thread(new Runnable() {			
			public void run() {
				try{
					BufferedReader Od = new BufferedReader(new InputStreamReader(s.getInputStream()));
					if(czyGosc){
						while(true)
						{
							String odebrWiad="";
							try{
								odebrWiad=Od.readLine();
							}catch(IOException e){
							}catch(NumberFormatException e){
							}
							try{
								if(!odebrWiad.isEmpty()){
									if(odebrWiad.startsWith("P:")){
										odebrWiad=odebrWiad.substring(2);
										odczytajPseudonimy(odebrWiad);
									}else{
									try{
									if(!tPrzest.getText().isEmpty()&&odebrWiad.startsWith("["))
										dok.insertString(dok.getLength(), "---------------------------"
													 + "----------------------------------------------"
													 + "----------------------------------------------"
													 + "-----------------\n",
													 dok.getStyle("zwykły"));
									if(odebrWiad.startsWith("[")){
										String inneForm[]=odczytajPola(odebrWiad);
										for(int i=0;i<inneForm.length;i++)
											dok.insertString(dok.getLength(),inneForm[i]+(i==2?"\n":""),
			        							i!=1?dok.getStyle(i==0?"kursywa":"zwykły"):
			        								ustawKolor(dok.getStyle("pogrubienie"), 
			        										   inneForm[i]));
										}
									else
										dok.insertString(dok.getLength(), "\t\t"+odebrWiad+"\n", 
												dok.getStyle("zwykły"));
									}catch(BadLocationException ex){
									}
									tPrzest.setCaretPosition(tPrzest.getDocument().getLength());
									}
								}
							}catch(NullPointerException e){
								break;
							}
						}
					}else{
						while(true)
						{
							String odebrWiad="";
							try{
								odebrWiad=Od.readLine();
							}catch(IOException e){
							}catch(NumberFormatException e){
							}
							try{
								if(!odebrWiad.isEmpty()){
									if(odebrWiad.startsWith("P:")){
										odebrWiad=odebrWiad.substring(2);
										pseudonimy.add(odebrWiad);
										String wyslijPseudo="P:";
										for(String pseudon: pseudonimy)
											wyslijPseudo=wyslijPseudo+pseudon+", ";
										wyslijPseudo=wyslijPseudo.substring(0,wyslijPseudo.length()-2);
										wyslijWiad(wyslijPseudo);
									}else{
									try{
									if(!tPrzest.getText().isEmpty()&&odebrWiad.startsWith("["))
										dok.insertString(dok.getLength(),"----------------------------"
			        								 + "----------------------------------------------"
			        								 + "----------------------------------------------"
			        								 + "----------------\n",
			        								 dok.getStyle("zwykły"));
									if(odebrWiad.startsWith("[")){
										String inneForm[]=odczytajPola(odebrWiad);
				        				for(int i=0;i<inneForm.length;i++)
				        					dok.insertString(dok.getLength(),inneForm[i]+(i==2?"\n":""),
				        							i!=1?dok.getStyle(i==0?"kursywa":"zwykły"):
				        								ustawKolor(dok.getStyle("pogrubienie"), 
				        										   inneForm[i]));
									}
									else
										dok.insertString(dok.getLength(), "\t\t"+odebrWiad+"\n", 
												dok.getStyle("zwykły"));
									}catch(BadLocationException ex){
									}
									tPrzest.setCaretPosition(tPrzest.getDocument().getLength());
									wyslijWiad(odebrWiad);
									}
								}
							}catch(NullPointerException e){
								break;
							}
						}					
					}
				}catch(IOException e){
				}
			}
		});	
		t.start();
		t = new Thread(new Runnable() {			
			public void run() {
				try{
					PrintWriter dO = new PrintWriter(s.getOutputStream(), true);
						while(true)
						{
							synchronized(blokada){
								if(czyWysylac){
									if(czyGosc){
										dO.println(wiadomoscDoWysl);
										czyWysylac=false;
										wiadomoscDoWysl=null;
										blokada.notify();
									}
									else{
										if(doWys.get(numer)){
											dO.println(wiadomoscDoWysl);											
											doWys.set(numer, false);
											czyWysylac=nieWyslano();
											if(!czyWysylac){
												wiadomoscDoWysl=null;
												blokada.notify();
											}
										}
									}
								}
							}
						}
				}catch(IOException e){
				}
			}
		});	
		t.start();      
    }
	
	public void actionPerformed(ActionEvent wyd) {        
        switch (Integer.parseInt(wyd.getActionCommand())){
        	case 1:
        	{
        		Ustawienia();
        	}
        	break;
        	case 2:
        	{
        		pseudonimy=new LinkedList<String>();
        		try{
        		if(!tPole2.getText().equals(""))
        		{
        			if(!czyGosc){
        				t=new Thread(new Runnable(){
        					public void run(){
        						try{
        							pseudonimy.add(pseudo);
                					ss = new ServerSocket(port);
                					wierszUst.setEnabled(false);
                					int i=0;
                					doWys=new LinkedList<Boolean>();
                					while(true){
                						Socket s = ss.accept();
                						sock.add(s);
                						doWys.add(false);
                						odbierzWiadomosc(s, i);
                						i++;
                						tPrzest2.setEnabled(true);
                					}
                				}catch(IOException e){
                					e.printStackTrace();
                				}
        					}
        				});
        				t.start();
        			}else{
        				try{
        					Socket s = new Socket(IP, port);
        					odbierzWiadomosc(s, 0);
        					wyslijWiad("P:"+pseudo);
        					wierszUst.setEnabled(false);
        					tPrzest2.setEnabled(true);
        					sock.add(s);
        				}catch(UnknownHostException e){
        				}catch(IOException e){
        					e.printStackTrace();
        				}
        			}
        		}else{
        	  	  	JOptionPane.showMessageDialog(ramka,"Najpierw podaj pseudonim!");
        		}
        		}catch(NullPointerException e){
        		}
        	}
        	break;
        	case 3:
        	{
        		//XML.Czat cz=new XML.Czat();
        		//XML.spakujRozmowe(cz, nazwapliku);
        		Zapisz();
        	}
        	break;
        	case 4:
        	{
        		ZapiszJako();
        	}
        	break;
        	case 5:
        	{
        		tPrzest.setText("");
        	}
        	break;
        	case 6:
        	{
                try {
                	if(!czyGosc){
                		wyslijWiad("Serwer zerwał połączenie. Wiadomości nie będą odbierane ani wysyłane.");
					}
                	for(Socket s: sock)
                		s.close();
					ss.close();
					wierszUst.setEnabled(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	break;
        	case 7:
        	{
        		System.exit(0);
        	}
        	break;
        	case 8:
        	{
        		if(!(tPrzest2.getText().equals("")))
        		{
        			String wiadomosc;
        			teraz=new GregorianCalendar(new SimpleTimeZone(3600000,"GMT+1"),new Locale("pl"));
        			wiadomosc=dataDoString(teraz)+" "+pseudo+":\t"+tPrzest2.getText();
        			tPrzest2.setText("");
        			if(!czyGosc){
        				try{
        				if(!tPrzest.getText().isEmpty())
        					dok.insertString(dok.getLength(), "------------------------------"
        								 + "-------------------------------------------------"
        								 + "-------------------------------------------------"
        								 + "--------\n", dok.getStyle("zwykły"));
        				String kopia=String.copyValueOf(wiadomosc.toCharArray());
        				String inneForm[]=odczytajPola(kopia);
        				for(int i=0;i<inneForm.length-1;i++)
        					dok.insertString(dok.getLength(),inneForm[i]+(i==2?"\n":""),
        						i!=1?dok.getStyle("kursywa"):ustawKolor(dok.getStyle("pogrubienie"),
        								inneForm[i]));
        				if(inneForm[2].contains("\n")){
        					inneForm=inneForm[2].split("\n");
        					int i=0;
        					for(String s: inneForm){
        						if(i==0){
        							dok.insertString(dok.getLength(),s+"\n",dok.getStyle("zwykły"));
        						}else{
        							dok.insertString(dok.getLength(),"\t\t"+s+"\n",
    	            						dok.getStyle("zwykły"));
        						}
        						i++;
        					}
        				}else{
        					dok.insertString(dok.getLength(),inneForm[2]+"\n",
            						dok.getStyle("zwykły"));
        				}
        				}catch(BadLocationException ex){        					
        				}
        				tPrzest.setCaretPosition(tPrzest.getDocument().getLength());
        			}
        			wyslijWiad(wiadomosc);
        		}
        	}
        	break;
        	case 9:
        	{
        		tPole.setEnabled(false);
        		czyGosc=false;
        	}
        	break;
        	case 10:
        	{
        		tPole.setEnabled(true);
        		czyGosc=true;
        	}
        	break;  
        	case 11:
        	{
        		if(czyGosc)
        			IP=tPole.getText();
        		pseudo=tPole2.getText();
        		try{
        			if(!tPole1.getText().equals(""))
        				port=Integer.parseInt(tPole1.getText());
        		}catch(NullPointerException e){
        		}catch(NumberFormatException e){
        		}
        		Nowyakcja.setVisible(false);
        	}
        	break;   
        	default:
        	{
        		JMenuItem source = (JMenuItem)(wyd.getSource());
                String s = "Action event detected."
                           + "\n"
                           + "    Event source: " + source.getText();
                System.out.println(s + "\n");
        	}
        	break;
        }        
    }

	public static String[] odczytajPola(String wiad){
		String pola[]=new String[3];
		int nast;
		nast=wiad.indexOf(']')+1;
		pola[0]=wiad.substring(0, nast);
		pola[1]=wiad.substring(nast,wiad.indexOf('\t')+1);
		nast=wiad.indexOf('\t')+1;
		pola[2]=wiad.substring(nast);
		return pola;
	}
	
	private void odczytajPseudonimy(String lista){
		int indeks=lista.indexOf(',');
		while(indeks>=0){			
			String nowy=lista.substring(0,indeks);
			lista=lista.substring(indeks+2);
			if(!pseudonimy.contains(nowy))
				pseudonimy.add(nowy);
			indeks=lista.indexOf(',');
		}
		pseudonimy.add(lista);
	}
    
	public String dataDoString(GregorianCalendar kg){
    	String data;
    	data="[";
    	if(kg.get(GregorianCalendar.DAY_OF_MONTH)<10)
    		data=data+"0";
    	data=data+kg.get(GregorianCalendar.DAY_OF_MONTH)+"-";
    	if(kg.get(GregorianCalendar.MONTH)<10)
    		data=data+"0";
    	data=data+kg.get(GregorianCalendar.MONTH)+"-"+kg.get(GregorianCalendar.YEAR)+" ";
    	if(kg.get(GregorianCalendar.HOUR)<10)
    		data=data+"0";
    	data=data+kg.get(GregorianCalendar.HOUR)+":";
    	if(kg.get(GregorianCalendar.MINUTE)<10)
    		data=data+"0";
    	data=data+kg.get(GregorianCalendar.MINUTE)+":";
    	if(kg.get(GregorianCalendar.SECOND)<10)
    		data=data+"0";
    	data=data+kg.get(GregorianCalendar.SECOND)+"]";
    	return data;
    }
	
   /* public boolean plikIstn(String nazwa){
    	try{
    	FileInputStream plik=new FileInputStream(nazwa);
    	plik.close();
    	}catch(NullPointerException e){
    		return false;
    	}catch(FileNotFoundException e){
    		return false;
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return true;
    }*/   

    public static void StwIPokGUI() {
        ramka = new JFrame("Czat");
        ramka.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Aplikacja pol = new Aplikacja();
        ramka.setJMenuBar(pol.tworzMenu());
        ramka.add(pol);
        JRootPane rootPane = ramka.getRootPane();
        rootPane.setDefaultButton(przyc);
        ramka.pack();
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
    }
    

    public static void main(String[] args) {
    	try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
    		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        //UIManager.put("swing.boldMetal", Boolean.FALSE);
    	blokada=new Object();
    	try{
    	ss=new ServerSocket();
    	}catch(IOException e){
    	}
    	sock=new ArrayList<Socket>();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StwIPokGUI();
            }
        });
        try{
        Thread.sleep(3000);
        }catch(InterruptedException e){        	
        }
        while(true){
        	if(!ramka.isVisible()){
        		try{
        			if(!czyGosc&&!ss.isBound())
        				ss.close();
        			for(Socket s: sock)
        				s.close();
        		}catch(IOException e){        			
        		}
        		System.exit(0);
        	}else{
        		try{
        			Thread.sleep(5000);
        		}catch(InterruptedException e){        	
        		}
        	}
        }
    }
}
