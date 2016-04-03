/**
 * @author Dariusz Pierzchala
 * 
 * Description: Description: Klasa gniazda obsługi obiektów - zgłoszeń 
 */

import java.util.LinkedList;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimControlException;



public class Smo extends BasicSimObj
{
    private LinkedList <Zgloszenie> kolejka;
    private SmoBis smo2;
    private SmoTer smo3;
	SimEventSemaphore semafor;
	private boolean wolne = true;
    private double prawdopodobienstwoP1;
    public RozpocznijObsluge rozpocznijObsluge;
    public ZakonczObsluge zakonczObsluge;
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public MonitoredVar MVdlKolejki;
    public MonitoredVar MVutraconeZgl;
	
    /** Creates a new instance of Smo 
     * @throws SimControlException */
    public Smo(SmoBis smo, SimEventSemaphore semafor, SmoTer smo2, double prawdopodobienstwoP1) throws SimControlException
    {
        kolejka = new LinkedList <Zgloszenie>();
        // Nastepne SMO
        this.smo2 = smo;
        this.smo3 = smo2;
        // Deklaracja zmiennych monitorowanych
        MVczasy_obslugi = new MonitoredVar();
        MVczasy_oczekiwania = new MonitoredVar();
        MVdlKolejki = new MonitoredVar();
        MVutraconeZgl = new MonitoredVar();
        this.semafor = semafor;
        this.prawdopodobienstwoP1 = prawdopodobienstwoP1;
    }

    // Wstawienie zgłoszenia do kolejki
    public int dodaj(Zgloszenie zgl) //FIFO
    {
        kolejka.addFirst(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return kolejka.size();
    }

    // Pobranie zgłoszenia z kolejki
    public Zgloszenie usun() // FIFO
    {
    	Zgloszenie zgl = (Zgloszenie) kolejka.removeFirst();
        MVdlKolejki.setValue(kolejka.size());
        return zgl;
    }

    // usuniecie zgłoszenia z kolejki
    public boolean usunWskazany(Zgloszenie zgl)
    {
    	Boolean b= kolejka.remove(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return b;
    }
    
    public int liczbaZgl()
    {
        return kolejka.size();
    }

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}
	
    public SmoBis getSmo2() {
		return smo2;
	}

    public SmoTer getSmo3() {
        return smo3;
    }

	public void setSmo2(SmoBis smo2) {
		this.smo2 = smo2;
	}
	
	public SimEventSemaphore getSemafor() {
		return semafor;
	}

	public void setSemafor(SimEventSemaphore semafor) {
		this.semafor = semafor;
	}

    public double getPrawdopodobienstwoP1() {
        return prawdopodobienstwoP1;
    }



	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}