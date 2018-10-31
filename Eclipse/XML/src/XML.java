import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

public class XML{
	
	@XmlRootElement
	static class Czat{
		@XmlElement(name="wiadomosc")
		List<Wiadomosc> wiadomosci;
		Czat(){
			wiadomosci=new ArrayList<Wiadomosc>();
		}
		
		public void wrzuc(Wiadomosc wiad){
			wiadomosci.add(wiad);
		}
		
		public String toString(){
			String rozmowa=new String();
			for(ListIterator<Wiadomosc> i=wiadomosci.listIterator();i.hasNext();){
				rozmowa=rozmowa+i.next()+"\n";
			}
			return rozmowa;
		}
	}

	static class Wiadomosc {
		@XmlAttribute
		String Data;
		@XmlAttribute
		String Autor;
		@XmlAttribute
		String Tresc;
		
		Wiadomosc(){
			super();
		}	
		
		public String kiedy(){
			return Data;
		}
		
		//@XmlElement
		public void powiedzKiedy(String data){
			this.Data=data;
		}
		
		public String wezAutora() {
			return Autor;
		}
		
		//@XmlElement
		public void ustawAutora(String pseudo) {
			this.Autor = pseudo;
		}
		
		public String czytajTresc() {
			return Tresc;
		}	
		
		//@XmlElement
		public void wysylanieTresci(String Wia) {
			this.Tresc = Wia;
		}

		public String toString() {
			return this.Data+" "+this.Autor + ":\t" + this.Tresc;
		}
	}
	
	public static void spakujRozmowe(Czat cz, String sciezka){
		String rozmowa=Aplikacja.tPrzest.getText();
		double dlugosc=rozmowa.length();
		StringReader czytacz=new StringReader(rozmowa);
		File f = new File(sciezka);
		char c;
		String linia="";
		try
		{
			JAXBContext ctx = JAXBContext.newInstance(Czat.class);
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				for (double d=0;d<dlugosc-20;d++){
					c=(char)czytacz.read();
					String wiad="";
					if(c!='\n')
						linia=linia+c;
					else{
						if(!linia.startsWith("-----"))
						{
							wiad=wiad+"\n"+linia;
							linia="";							
						}else{
							System.out.println("Wypisuję "+d);
							String pola[]=Aplikacja.odczytajPola(wiad);
							Wiadomosc nowa=new Wiadomosc();
							nowa.powiedzKiedy(pola[0]);
							nowa.ustawAutora(pola[1].substring(1, pola[1].length()-2));
							nowa.wysylanieTresci(pola[2]);
							cz.wrzuc(nowa);
							linia="";
							wiad="";
							marshaller.marshal(cz, f);
						}
					}
				}
//			marshaller.marshal(cz, f);
		}catch(IOException|JAXBException e){			
		}
	}
	
	public static void rozpakujDoArchiwum() throws JAXBException {
		Czat p = new Czat();	 
		File f = new File("czat.xml");
		JAXBContext ctx = JAXBContext.newInstance(Czat.class);		 
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		p = (Czat)unmarshaller.unmarshal(f);
		System.out.println(p);
	}
}
