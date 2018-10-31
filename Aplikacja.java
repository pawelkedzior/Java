import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Aplikacja extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
    protected JFrame Nowyakcja;
    static JFrame ramka;
    protected JTextField tPole, tPole1;
    protected JTextField tPole2, tPole3, tPole4, tPole5;
    JTable table;
    public static Object[][] daneBezwzgl;
    String[] Kolumny = {"Nazwisko",
            "Imiê",
            "Miejscowoœæ",
            "Numer"};
    
    public Aplikacja(Object[][] dane) {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel tekstObj=new JLabel("Nazwisko:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,5,0,10);
        c.gridx = 0;
        c.gridy = 0;
        add(tekstObj, c);
        tPole=new JTextField(15);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,0,0,0);
        c.gridx = 1;
        c.gridy = 0;
        add(tPole, c);
        tekstObj=new JLabel("Imiê:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,5,0,10);
        c.gridx = 2;
        c.gridy = 0;
        add(tekstObj, c);
        tPole1=new JTextField(15);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,0,0,5);
        c.gridx = 3;
        c.gridy = 0;
        add(tPole1, c);
        JButton przyc=new JButton("Szukaj");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,20,5,10);
        c.gridx = 2;
        c.gridwidth = 2;
        c.gridy = 1;
        przyc.addActionListener(this);
        przyc.setActionCommand("2");
        add(przyc, c);
        table = new JTable(dane, Kolumny);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5,7,5,7);
        c.gridx = 0;
        c.gridwidth = 4;
        c.gridy = 2;
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));    
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,c);
    }
    
    public JMenuBar tworzMenu(){
    	JMenuBar pasekMenu;
        JMenu baza;
        JMenuItem wiersz;
        pasekMenu = new JMenuBar();

//Menu "Plik"        
        
        baza = new JMenu("Baza");
        pasekMenu.add(baza);
        wiersz = new JMenuItem("Nowy wpis", KeyEvent.VK_N);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    		wiersz.getAccessibleContext().setAccessibleDescription("Tworzy nowy wpis");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("1");
    	baza.add(wiersz);	
        wiersz = new JMenuItem("Przywróæ bazê", KeyEvent.VK_O);
    		wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
            wiersz.addActionListener(this);
            wiersz.setActionCommand("3");
    	baza.add(wiersz);
    	baza.addSeparator();
    	wiersz = new JMenuItem("WyjdŸ", KeyEvent.VK_W);
			wiersz.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
			wiersz.getAccessibleContext().setAccessibleDescription("Wychodzi z programu");
            wiersz.addActionListener(this);
            wiersz.setActionCommand("6");
		baza.add(wiersz);

		return pasekMenu;
    }

    public void actionPerformed(ActionEvent wyd) {        
        switch (Integer.parseInt(wyd.getActionCommand())){
        	case 1:
        	{
        		Nowyakcja=new JFrame("Dodawanie wpisu");
        		JPanel Panel = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
        		JLabel et;
        		et=new JLabel("Nazwisko:");
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,0,10);
                c.gridx = 0;
                c.gridy = 0;
        		Panel.add(et,c);
        		tPole2=new JTextField(15);
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,0,5);
                c.gridx = 1;
                c.gridy = 0;
        		Panel.add(tPole2,c);
        		et=new JLabel("Imiê:");
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,0,10);
                c.gridx = 0;
                c.gridy = 1;
        		Panel.add(et,c);
        		tPole3=new JTextField(15);
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,0,5);
                c.gridx = 1;
                c.gridy = 1;
        		Panel.add(tPole3,c);
        		et=new JLabel("Miejscowoœæ:");
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,0,10);
                c.gridx = 0;
                c.gridy = 2;
        		Panel.add(et,c);
        		tPole4=new JTextField(15);
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,0,5);
                c.gridx = 1;
                c.gridy = 2;
        		Panel.add(tPole4,c);
        		et=new JLabel("Numer:");
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,0,10);
                c.gridx = 0;
                c.gridy = 3;
        		Panel.add(et,c);
        		tPole5=new JTextField(15);
        		c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(5,5,10,5);
                c.gridx = 1;
                c.gridy = 3;
        		Panel.add(tPole5,c);
        		Nowyakcja.add(Panel, BorderLayout.NORTH);
        		Panel = new JPanel(new GridBagLayout());
        		JButton przyc;
        		przyc=new JButton("Zapisz");
    				przyc.addActionListener(this);
    				przyc.setActionCommand("5");
    				c.fill = GridBagConstraints.HORIZONTAL;
                    c.insets = new Insets(5,10,10,5);
                    c.gridx = 0;
                    c.gridy = 4;
        		Panel.add(przyc,c);
        		przyc=new JButton("Anuluj");
        			przyc.addActionListener(this);
        			przyc.setActionCommand("4");
        			c.fill = GridBagConstraints.HORIZONTAL;
                    c.insets = new Insets(5,5,10,10);
                    c.gridx = 1;
                    c.gridy = 4;
        		Panel.add(przyc,c);
        		Nowyakcja.add(Panel, BorderLayout.SOUTH);
        		Nowyakcja.pack();
                Nowyakcja.setLocationRelativeTo(ramka);
        		Nowyakcja.setVisible(true);
        	}
        	break;
        	case 2:
        	{
        		String nazwisko=tPole.getText();
        		String imie=tPole1.getText();
        		if((!nazwisko.isEmpty())||(!imie.isEmpty()))
        		{
        			int co;
        			if((nazwisko.isEmpty())&&(!imie.isEmpty())){
        				co=2;
        			}else
        			{
        				if((!nazwisko.isEmpty())&&(imie.isEmpty())){
        					co=1;
        				}else{
        					co=3;
        				}
        			}
        			try{
        		        Object[][] cos=Ksiazka.wyszukaj(nazwisko,imie,co);
        		        ramka.setVisible(false);
        		        StwIPokGUI(cos);
        		    }
        			catch(Exception e){        			
        			}
        		}else{
        			JOptionPane.showMessageDialog(Nowyakcja, "Musisz podaæ przynajmniej imiê lub nazwisko","Podaj nazwisko/imiê", JOptionPane.INFORMATION_MESSAGE);
        		}
        	}
        	break;
        	case 3:
        	{
		        ramka.setVisible(false);
		        ramka=null;
		        StwIPokGUI(daneBezwzgl);
        	}
        	break;
        	case 4:
        	{
        		Nowyakcja.setVisible(false);
        	}
        	break;
        	case 5:
        	{
        		String naz=tPole2.getText();
        		String im=tPole3.getText();
        		String msc=tPole4.getText();
        		String num=tPole5.getText();
        		if(!naz.isEmpty()&&!im.isEmpty()&&!msc.isEmpty()&&!num.isEmpty()){
        			try{
        				Ksiazka.dodaj(naz, im, msc, num);
        			}catch(Exception e){
        				e.printStackTrace();
        			}
        		Nowyakcja.setVisible(false);        		
        		}else{
        			JOptionPane.showMessageDialog(Nowyakcja, "Musisz podaæ przynajmniej imiê lub nazwisko","Niekompletne dane",JOptionPane.ERROR_MESSAGE);
        		}
        	}
        	break;
        	case 6:
        	{
        		System.exit(0);
        	}
        	break;
        	default:
        	{
        	}
        	break;
        }        
    }

    public static void StwIPokGUI(Object[][] dane) {
        ramka = new JFrame("Ksi¹¿ka telefoniczna");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Aplikacja pol = new Aplikacja(dane);
        pol.setOpaque(true);
        ramka.setJMenuBar(pol.tworzMenu());
        ramka.add(pol);
        ramka.pack();
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
    }
}
