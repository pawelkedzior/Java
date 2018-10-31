import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Pole extends JPanel implements ActionListener/*, DocumentListener*/{

	private static final long serialVersionUID = 1L;
	protected JTextArea tPrzest;
    protected String nazwapliku="";
    protected JFrame Nowyakcja;
    protected JTextField tPole, tPole1;
    protected ButtonGroup grupa;
    int indeks;
    final Highlighter podsw;
    final static Color  kolorPodsw = Color.cyan;
    final Highlighter.HighlightPainter malarz;
    
    public Pole() {
        super(new GridBagLayout());
        tPrzest = new JTextArea(40, 60);
    	podsw = new DefaultHighlighter();
        malarz = new DefaultHighlighter.DefaultHighlightPainter(kolorPodsw);
        tPrzest.setHighlighter(podsw);
        tPrzest.setEditable(true);
        tPrzest.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(tPrzest);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    
    public JMenuBar tworzMenu(){
    	JMenuBar pasekMenu;
        JMenu plik;
        JMenuItem wiersz;
        pasekMenu = new JMenuBar();

//Menu "Plik"        
        
        plik = new JMenu("Plik");
        pasekMenu.add(plik);
        wiersz = new JMenuItem("Nowy", KeyEvent.VK_N);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    		wiersz.getAccessibleContext().setAccessibleDescription("Tworzy nowy plik");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("1");
    	plik.add(wiersz);	
        wiersz = new JMenuItem("Otwórz", KeyEvent.VK_O);
        	wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        	wiersz.getAccessibleContext().setAccessibleDescription("Otwiera plik");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("2");
        plik.add(wiersz);
        wiersz = new JMenuItem("Zapisz", KeyEvent.VK_Z);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
    		wiersz.getAccessibleContext().setAccessibleDescription("Zapisuje plik");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("3");
    	plik.add(wiersz);
    	wiersz = new JMenuItem("Zapisz jako", KeyEvent.VK_J);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Zapisuje plik jako");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("4");
		plik.add(wiersz);
    	plik.addSeparator();
    	wiersz = new JMenuItem("WyjdŸ", KeyEvent.VK_W);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Wychodzi z programu");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("5");
		plik.add(wiersz);

//Menu "Edycja"		
		
		plik = new JMenu("Edycja");
		pasekMenu.add(plik);
		wiersz = new JMenuItem("ZnajdŸ", KeyEvent.VK_J);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Znajduje wystêpowanie ci¹gu znaków w pliku");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("6");
		plik.add(wiersz);	
		plik.addSeparator();
		wiersz = new JMenuItem("Zamieñ", KeyEvent.VK_A);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.ALT_MASK));
    		wiersz.getAccessibleContext().setAccessibleDescription("Zamienia pierwsze wyst¹pienie ci¹gu znaków na inny podany ci¹g");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("7");
    	plik.add(wiersz);
    	wiersz = new JMenuItem("Zamieñ wszystkie", KeyEvent.VK_M);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Zamienia wszystkie wyst¹pienia danego ci¹gu znaków na inny podany ci¹g");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("8");
		plik.add(wiersz);
		return pasekMenu;
    }

    public void actionPerformed(ActionEvent wyd) {        
        switch (Integer.parseInt(wyd.getActionCommand())){
        	case 1:
        	{
        		Nowyakcja=new JFrame("Co chcesz zrobiæ?");
        		Nowyakcja.setLayout(new BorderLayout());
        		JPanel Panel = new JPanel(new GridLayout(1, 1));
        		JLabel et;
        		et=new JLabel("Co chcesz zrobiæ przed zamkniêciem obecnie otwartego pliku?");
        			et.setLocation(25,25);
        		Panel.add(et);
        		Nowyakcja.add(Panel, BorderLayout.NORTH);
        		Panel = new JPanel(new GridLayout(1, 3));
        		JButton przyc;
        		przyc=new JButton("Zapisz");
    				przyc.addActionListener(this);
    				przyc.setActionCommand("11");
        		Panel.add(przyc);
        		przyc=new JButton("Nie Zapisuj");
    				przyc.addActionListener(this);
    				przyc.setActionCommand("12");
        		Panel.add(przyc);
        		przyc=new JButton("Anuluj");
        			przyc.addActionListener(this);
        			przyc.setActionCommand("10");
        		Panel.add(przyc);
        		Nowyakcja.add(Panel, BorderLayout.SOUTH);
        		Nowyakcja.pack();
        		Nowyakcja.setVisible(true);
        	}
        	break;
        	case 2:
        	{
        		Nowyakcja=new JFrame("Co chcesz zrobiæ?");
        		Nowyakcja.setSize(200, 100);
        		Nowyakcja.setLocation(250,150);
        		Nowyakcja.setLayout(new BorderLayout());
        		JPanel Panel = new JPanel(new GridLayout(1, 2));
        		JLabel et;
        		et=new JLabel("Podaj œcie¿kê do pliku.");
        			et.setLocation(25,25);
        		Panel.add(et);
        		tPole=new JTextField(30);
        		Panel.add(tPole);
        		Nowyakcja.add(Panel, BorderLayout.NORTH);
        		Panel = new JPanel(new GridLayout(1, 2));
        		JButton przyc;
        		przyc=new JButton("Otwórz");
                	przyc.addActionListener(this);
                	przyc.setActionCommand("9");
        		Panel.add(przyc);
        		przyc=new JButton("Anuluj");
                	przyc.addActionListener(this);
                	przyc.setActionCommand("10");
        		Panel.add(przyc);
        		Nowyakcja.add(Panel);
        		Nowyakcja.pack();
        		Nowyakcja.setVisible(true);
        	}
        	break;
        	case 3:
        	{
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
        		System.exit(0);
        	}
        	break;
        	case 6:
        	{
                Znajdujemy();
        	}
        	break;
        	case 7:
        	{
        		Zamieniamy();	
        	}
        	break;
        	case 8:
        	{       
        		ZamieniamyW();
        	}
        	break;
        	case 9:
        	{
        		nazwapliku=tPole.getText();
        		try{
        			FileReader fr = new FileReader(nazwapliku);
        			BufferedReader br=new BufferedReader(fr);
        			String line;
        				while  ((line = br.readLine()) != null) {
        					tPrzest.append(line+"\n");
        				}
        			br.close();
        		} catch(IOException e){
                    e.printStackTrace();
        		}
        		Nowyakcja.setVisible(false);
        	}
        	break;
        	case 10:
        	{
        		Nowyakcja.setVisible(false);
        	}
        	break;
        	case 11:
        	{
        		Zapisz();
        		nazwapliku="";
        		tPrzest.setText("");
        		Nowyakcja.setVisible(false);
        	}
        	break;
        	case 12:
        	{
        		tPrzest.setText("");
        		nazwapliku="";
        		Nowyakcja.setVisible(false);
        	}
        	break;  
        	case 13:
        	{
        		if(tPole1.getText()!=""&&tPole.getText()!=""){
        			nazwapliku=tPole1.getText()+tPole.getText();
        			Zapisz();
        			Nowyakcja.setVisible(false);
        		}
        	}
        	break;     
        	case 22:
        	{
        		String komenda = grupa.getSelection().getActionCommand();
                //tPole.getDocument().addDocumentListener(this);
                if (komenda.equals("20")) {
                	szukaj("Dó³");
                } else {
                	szukaj("Góra");
                }
        	}
        	break;   
        	case 23:
        	{
        		zamianaW();
        	}
        	break;   
        	case 24:
        	{
        		szukaj("Dó³");
        	}
        	break;   
        	case 25:
        	{
        		zamiana();
        	}
        	break;
        	default:
        	{
            	JMenuItem source = (JMenuItem)(wyd.getSource());
                String s = "Action event detected."
                           + "\n"
                           + "    Event source: " + source.getText();
                tPrzest.append(s + "\n");
                tPrzest.setCaretPosition(tPrzest.getDocument().getLength());
        	}
        	break;
        }        
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
    
    public void Zapisz(){
    	if((nazwapliku=="")/*||(!plikIstn(nazwapliku))*/){
    		ZapiszJako();
    	}else{
    		try {
    			String tekst = tPrzest.getText();
    			FileOutputStream file = new FileOutputStream(nazwapliku);
    			byte[] buf = tekst.getBytes();
    			file.write(buf, 0, buf.length);
    			file.close();
    		}catch(NullPointerException e){
        	}catch(FileNotFoundException e){
        	}catch(IOException e){
        		e.printStackTrace();
        	}		
    	}
    }
    
    public void ZapiszJako(){
		Nowyakcja=new JFrame("Co chcesz zrobiæ?");
		Nowyakcja.setLayout(new BorderLayout());
		JPanel Panel = new JPanel(new GridLayout(2, 1));
		JPanel Panel2 = new JPanel(new GridLayout(2, 1));
		JLabel et;
		et=new JLabel("Œcie¿ka do pliku:");
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
		Nowyakcja.add(Panel, BorderLayout.SOUTH);
		Nowyakcja.pack();
		Nowyakcja.setVisible(true);
    }
    
    public void Znajdujemy(){
    	JFrame Nowyakcja = new JFrame("Szukaj");
    	JLabel etykieta = new JLabel("ZnajdŸ:", JLabel.LEFT);
		Nowyakcja.setLayout(new BorderLayout());
		JPanel Panel = new JPanel(new GridLayout(2, 1));
    		Panel.add(etykieta);
    	tPole=new JTextField(15);
    		Panel.add(tPole);
    	JRadioButton dol,gora;
    		dol=new JRadioButton("Szukaj w dó³");
    		dol.setSelected(true);
    		gora=new JRadioButton("Szukaj w górê");
            dol.addActionListener(this);
            gora.addActionListener(this);
            dol.setActionCommand("20");
            gora.setActionCommand("21");
            
            JPanel radioPanel = new JPanel(new GridLayout(2, 1));
            	radioPanel.add(dol);
            	radioPanel.add(gora);
            
            grupa = new ButtonGroup();
        	grupa.add(dol);
        	grupa.add(gora);
        
        Nowyakcja.add(Panel, BorderLayout.LINE_START);
    	Nowyakcja.add(radioPanel, BorderLayout.LINE_END);
    	JButton prz;
    	prz=new JButton("ZnajdŸ nastêpny");
    		prz.addActionListener(this);
    		prz.setActionCommand("22");
    	Nowyakcja.add(prz, BorderLayout.SOUTH);
    	Nowyakcja.pack();
    	Nowyakcja.setVisible(true);
    }
    
    public void szukaj(String tryb){
    	podsw.removeAllHighlights();
    	String s = tPole.getText();
        if (s.length() <= 0) {
            return;
        }
        String zawart=tPrzest.getText();
        if (tryb=="Dó³"){
        	indeks = zawart.indexOf(s, tPrzest.getCaretPosition());
        	if (indeks>=0){
                try {
                    int end = indeks + s.length();
                    podsw.addHighlight(indeks, end, malarz);
                    tPrzest.setCaretPosition(end);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            } else{
                //TODO
            }
        }
        else{
        	indeks = zawart.lastIndexOf(s, tPrzest.getCaretPosition());
        	if (indeks>=0){
                try {
                    int end = indeks + s.length();
                    podsw.addHighlight(indeks, end, malarz);
                    tPrzest.setCaretPosition(indeks-1);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            } else{
                //TODO
            }        	
        }
    }
    
    public void Zamieniamy(){
    	JFrame Nowyakcja = new JFrame("Zamieñ Wszystkie");
    	JLabel etykieta = new JLabel("Zamieñ:", JLabel.LEFT);
		Nowyakcja.setLayout(new BorderLayout());
		JPanel Panel = new JPanel(new GridLayout(3, 2));
    		Panel.add(etykieta);
    	tPole=new JTextField(15);
    		Panel.add(tPole);
    	etykieta = new JLabel("Na:", JLabel.LEFT);
			Panel.add(etykieta);
		tPole1=new JTextField(15);
			Panel.add(tPole1);
        Nowyakcja.add(Panel, BorderLayout.NORTH);
    	JButton prz;
    	prz=new JButton("ZnajdŸ");
    		prz.addActionListener(this);
    		prz.setActionCommand("24");
    	Panel.add(prz);
    	prz=new JButton("Zamieñ");
			prz.addActionListener(this);
			prz.setActionCommand("25");
		Panel.add(prz);
    	Nowyakcja.pack();
    	Nowyakcja.setVisible(true);
		tPrzest.setCaretPosition(0);
    }
    
    public void zamiana(){
    	String s,na,zawart;
    	s = tPole.getText();
    	na = tPole1.getText();
    	zawart = tPrzest.getText();
    	String tymcz,tymcz2;
    	int pozPocz=tPrzest.getCaretPosition()-s.length();
    	tymcz=zawart.substring(0,pozPocz);
    	tymcz2=zawart.substring(pozPocz);
    	tymcz2=tymcz2.replaceFirst(s,na);
    	zawart=tymcz+tymcz2;
    	tPrzest.setText(zawart);
    	tPrzest.setCaretPosition(pozPocz);
    }
    
    public void ZamieniamyW(){

    	JFrame Nowyakcja = new JFrame("Zamieñ Wszystkie");
    	JLabel etykieta = new JLabel("Zamieñ:", JLabel.LEFT);
		Nowyakcja.setLayout(new BorderLayout());
		JPanel Panel = new JPanel(new GridLayout(2, 2));
    		Panel.add(etykieta);
    	tPole=new JTextField(15);
    		Panel.add(tPole);
    	etykieta = new JLabel("Na:", JLabel.LEFT);
			Panel.add(etykieta);
		tPole1=new JTextField(15);
			Panel.add(tPole1);
        Nowyakcja.add(Panel, BorderLayout.NORTH);
    	JButton prz;
    	prz=new JButton("PotwierdŸ");
    		prz.addActionListener(this);
    		prz.setActionCommand("23");
    	Nowyakcja.add(prz, BorderLayout.SOUTH);
    	Nowyakcja.pack();
    	Nowyakcja.setVisible(true);
    }
    
    public void zamianaW(){
    	String s,na,zawart;
    	s = tPole.getText();
    	na = tPole1.getText();
    	zawart = tPrzest.getText();
    	int pozPocz=tPrzest.getCaretPosition();
    	zawart=zawart.replaceAll(s,na);
    	tPrzest.setText(zawart);
    	tPrzest.setCaretPosition(pozPocz);
    }

    private static void StwIPokGUI() {
        JFrame ramka = new JFrame("Notatnik");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Pole pol = new Pole();
        ramka.setJMenuBar(pol.tworzMenu());
        ramka.add(pol);
        ramka.pack();
        ramka.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StwIPokGUI();
            }
        });
    }
}
