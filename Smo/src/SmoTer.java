/**
 * Created by Przemek on 2016-01-19.
 */
/**
 * @author Dariusz Pierzchala
 *
 * Description: Description: Klasa gniazda obsługi obiektów - zgłoszeń
 */


import java.util.Arrays;
import java.util.LinkedList;
import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimControlException;



public class SmoTer extends BasicSimObj {
    private LinkedList<Zgloszenie> kolejka;
    private Smo smo2;
    SimEventSemaphore semafor;
    private double prawdopodobienstwoP3;
    private boolean wolne = true;
    public RozpocznijObslugeTer rozpocznijObsluge;
    public ZakonczObslugeTer zakonczObsluge;
    public SprzezenieZwrotneTer sprzezenieZwrotneTer;
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public MonitoredVar MVdlKolejki;
    public MonitoredVar MVutraconeZgl;

    /**
     * Creates a new instance of Smo
     *
     * @throws SimControlException
     */
    public SmoTer(SimEventSemaphore semafor, double prawdopodobienstwoP3) throws SimControlException {
        // Utworzenie wewnętrznej listy w kolejce//
        kolejka = new LinkedList<Zgloszenie>(); //pusta kolejka priorytetowa
        // Nastepne SMO
        //smo2 = smo;
        // Deklaracja zmiennych monitorowanych
        MVczasy_obslugi = new MonitoredVar();
        MVczasy_oczekiwania = new MonitoredVar();
        MVdlKolejki = new MonitoredVar();
        MVutraconeZgl = new MonitoredVar();
        this.semafor = semafor;
        this.prawdopodobienstwoP3 = prawdopodobienstwoP3;
    }

    // Wstawienie zgłoszenia do kolejki
    public int dodaj(Zgloszenie zgl) //PRIORYTETOWA
    {
        kolejka.add(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return kolejka.size();
    }

    // Pobranie zgłoszenia z kolejki
    public Zgloszenie usun() // PRIORYTETOWA
    {
        Zgloszenie zgl = (Zgloszenie) kolejka.removeFirst(); // zwraca głowe listy
        kolejka.remove(zgl); //usuwa zgl z kolejki
        MVdlKolejki.setValue(kolejka.size());
        return zgl;
    }

    // usuniecie wskazanego (tu najstarszego) zgłoszenia z kolejki
    public boolean usunWskazany(Zgloszenie zgl) {
        Boolean b = kolejka.remove(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return b;
    }

    public Zgloszenie findOldest() { //priorytet maja najstarsze
        Zgloszenie oldest = kolejka.getFirst();
        for (Zgloszenie zgl : kolejka) {
            if (zgl.getTenNr() < oldest.getTenNr())
                oldest = zgl;
        }
        return oldest;
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

    public double getPrawdopodobienstwoP3() {
        return prawdopodobienstwoP3;
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
