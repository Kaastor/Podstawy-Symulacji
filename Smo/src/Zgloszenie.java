import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

/**
 * Description: Klasa zgloszenia obsługiwanego w gnieździe obsługi.
 *
 * @author Dariusz Pierzchala
 */

public class Zgloszenie extends BasicSimObj
{
    double czasOdniesienia;
    static int nr=0;
    int tenNr;
    public Smo smo;
    public SmoTer smoTer;
    public SmoBis smoBis;
    public KoniecNiecierpliwienia koniecNiecierpliwosci;
    public KoniecNiecierpliwieniaTer koniecNiecierpliwieniaTer;
    public KoniecNiecierpliwieniaBis koniecNiecierpliwieniaBis;


    public int getTenNr() {
        return tenNr;
    }

    public void setTenNr() {
        this.tenNr = nr++;
    }

    public Zgloszenie(double Czas, Smo smo) throws SimControlException
    {
        czasOdniesienia = Czas;
        setTenNr();
        this.smo = smo;
        this.smoTer = smo.getSmo3();
        this.smoBis = smo.getSmo2();
        StartNiecierpliwienia stN = new StartNiecierpliwienia(this);
    }

    public void setCzasOdniesienia(double t)
    {
        czasOdniesienia = t;
    }

    public double getCzasOdniesienia()
    {
        return czasOdniesienia;
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