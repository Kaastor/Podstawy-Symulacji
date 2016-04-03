/**
 * @author Dariusz Pierzchala
 * 
 * Description: Zdarzenie końcowe aktywności gniazda obsługi. Kończy obsługę przez losowy czas obiektów - zgłoszeń.
 */

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;

public class ZakonczObsluge extends BasicSimEvent<Smo, Zgloszenie>
{
    private Smo smoParent;

    public ZakonczObsluge(Smo parent, double delay, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, delay, zgl);
        this.smoParent = parent;
    }

    public ZakonczObsluge(Smo parent, SimEventSemaphore semafor, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, semafor, zgl);
        this.smoParent = parent;
    }
    
	@Override
	protected void onInterruption() throws SimControlException {
		// TODO
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
        if (smoParent.getSmo2().dodaj(transitionParams)) {
    		smoParent.setWolne(true);
            System.out.println(simTime()+": Koniec obsługi zgl. nr: " + transitionParams.getTenNr());
            smoParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasOdniesienia(), simTime());
        	
            if (smoParent.getSmo2().liczbaZgl()==1 && smoParent.getSmo2().isWolne()) {
            	smoParent.getSmo2().rozpocznijObsluge = new RozpocznijObslugeBis(smoParent.getSmo2());
            }
        	// Zaplanuj dalsza obsługe w tym gnieździe
        	if (smoParent.liczbaZgl() > 0)
        	{
        		smoParent.rozpocznijObsluge = new RozpocznijObsluge(smoParent);        	
        	}	
        } else {
            System.out.println(simTime()+": Oczekiwanie na semaforze - zgl. nr: " + transitionParams.getTenNr());
        	smoParent.zakonczObsluge = new ZakonczObsluge(smoParent, smoParent.getSemafor(), transitionParams);        	
        }
	}

	@Override
	public Object getEventParams() {
		// TODO Auto-generated method stub
		return null;
	}
}