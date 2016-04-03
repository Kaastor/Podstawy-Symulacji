/**
 * @author Dariusz Pierzchala
 * 
 * Description: Zdarzenie początkowe aktywności gniazda obsługi. Rozpoczyna obsługę przez losowy czas obiektów - zgłoszeń.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class RozpocznijObsluge extends BasicSimEvent<Smo, Zgloszenie>
{
    private Smo smoParent;
    private SimGenerator generator;

    public RozpocznijObsluge(Smo parent, double delay) throws SimControlException
    {
    	super(parent, delay); //
    	generator = new SimGenerator();
        this.smoParent = parent;
    }

    public RozpocznijObsluge(Smo parent) throws SimControlException
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
        if (smoParent.liczbaZgl() > 0)
        {
        	// Zablokuj gniazdo
        	smoParent.setWolne(false);
        	// Pobierz zgłoszenie
        	Zgloszenie zgl = smoParent.usun();
        	// Przerwanie niecierpliwosci
        	zgl.koniecNiecierpliwosci.interrupt();
        	// Wygeneruj czas obsługi
        	double czasObslugi = generator.normal(10, 1.0);
            // Zapamiętaj dane monitorowane
            smoParent.MVczasy_oczekiwania.setValue(simTime() - zgl.getCzasOdniesienia(), simTime());
            zgl.setCzasOdniesienia(simTime());
            System.out.println(simTime()+": Początek obsługi zgl. nr: " + zgl.getTenNr());
        	// Zaplanuj koniec obsługi
        	smoParent.zakonczObsluge = new ZakonczObsluge(smoParent, czasObslugi, zgl);        	
        }
		
	}

	@Override
	public Object getEventParams() {
		// TODO Auto-generated method stub
		return null;
	}
}