/**
 * @author Dariusz Pierzchala
 *
 * Description: Zdarzenie początkowe aktywności gniazda obsługi. Rozpoczyna obsługę przez losowy czas obiektów - zgłoszeń.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class RozpocznijObslugeTer extends BasicSimEvent<SmoTer, Zgloszenie>
{
    private SmoTer smoParent;
    private SimGenerator generator;

    public RozpocznijObslugeTer(SmoTer parent, double delay) throws SimControlException
    {
        super(parent, delay);
        generator = new SimGenerator();
        this.smoParent = parent;
    }

    public RozpocznijObslugeTer(SmoTer parent) throws SimControlException
    {
        super(parent);
        generator = new SimGenerator();
        this.smoParent = parent;
    }

    @Override
    protected void onInterruption() throws SimControlException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onTermination() throws SimControlException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void stateChange() throws SimControlException {
        if (smoParent.liczbaZgl() > 0) {

            // Zablokuj gniazdo
            smoParent.setWolne(false);
            // Pobierz zgłoszenie
            Zgloszenie zgl = smoParent.findOldest();
            smoParent.usunWskazany(zgl);
            zgl.koniecNiecierpliwieniaTer.interrupt();
            // Wygeneruj czas obsługi
            double czasObslugi = generator.triangular(0.2);
            // Zapamiętaj dane monitorowane
            smoParent.MVczasy_obslugi.setValue(czasObslugi);
            smoParent.MVczasy_oczekiwania.setValue(simTime()- zgl.getCzasOdniesienia());
            System.out.println(simTime() + ": SMO3-Początek obsługi zgl. nr: " + zgl.getTenNr());

            //// Zaplanuj koniec obsługi
            double p3 = generator.nextDouble();
            if (p3 <= smoParent.getPrawdopodobienstwoP3()) { //1-4
                smoParent.zakonczObsluge = new ZakonczObslugeTer(smoParent, czasObslugi, zgl);
                //Oznaczenie zdarzenia do opublikowania w obiekcie Dispatcher
                smoParent.zakonczObsluge.setPublishable(true);
            } else { //4-1
                smoParent.sprzezenieZwrotneTer = new SprzezenieZwrotneTer(smoParent, czasObslugi, zgl);
            }
        }
    }

    @Override
    public Object getEventParams() {
        // TODO Auto-generated method stub
        return null;
    }
}