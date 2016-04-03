/**
 * @author Dariusz Pierzchala
 * 
 * Description: Klasa gniazda obsługi obiektów - zgłoszeń 
 */

import java.util.LinkedList;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;



public class SmoBis extends BasicSimObj 
{
    private LinkedList <Zgloszenie> kolejka;
    private boolean wolne = true;
    private int maxDlKolejki;
    //
	SimEventSemaphore semafor;
	private double prawdopodobienstwoP2;
    public RozpocznijObslugeBis rozpocznijObsluge;
    public ZakonczObslugeBis zakonczObsluge;
    public SprzezenieZwrotne sprzezenieZwrotne;
    //public OdblokujGniazdo odblokuj; 
    //
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public MonitoredVar MVdlKolejki;
    public MonitoredVar MVutraconeZgl;

    /** Creates a new instance of SmoBis 
     * @throws SimControlException */
    public SmoBis(int maxDlKolejki, SimEventSemaphore semafor, double prawdopodobienstwoP2) throws SimControlException
    {
        kolejka = new LinkedList <Zgloszenie>();
        this.maxDlKolejki = maxDlKolejki;
        this.semafor = semafor;
        //
        // Deklaracja zmiennych monitorowanych
        MVczasy_obslugi = new MonitoredVar();
        MVczasy_oczekiwania = new MonitoredVar();
        MVdlKolejki = new MonitoredVar();
        MVutraconeZgl = new MonitoredVar();

        this.prawdopodobienstwoP2 = prawdopodobienstwoP2;
    }

    // Wstawienie zgłoszenia do kolejki
    public boolean dodaj(Zgloszenie zgl)    //LIFO
    {
    	if(liczbaZgl() < maxDlKolejki){
    		kolejka.add(zgl);
    		MVdlKolejki.setValue(kolejka.size());
    		return true;
    	}
        return false;
    }

    // Pobranie zgłoszenia z kolejki
    public Zgloszenie usun()    //LIFO
    {
    	Zgloszenie zgl = (Zgloszenie) kolejka.removeFirst();
        MVdlKolejki.setValue(kolejka.size());
        return zgl;
    }

    // Pobranie zgłoszenia z kolejki
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
	
	public SimEventSemaphore getSemafor() {
		return semafor;
	}

	public void setSemafor(SimEventSemaphore semafor) {
		this.semafor = semafor;
	}
	public int getMaxDlKolejki() {
		return maxDlKolejki;
	}

	public void setMaxDlKolejki(int maxDlKolejki) {
		this.maxDlKolejki = maxDlKolejki;
	}

    public double getPrawdopodobienstwoP2() {
        return prawdopodobienstwoP2;
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