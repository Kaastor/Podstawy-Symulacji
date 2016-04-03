/**
 * @author Dariusz Pierzchala
 * 
 * Description: Zdarzenie końcowe aktywności gniazda obsługi. Kończy obsługę przez losowy czas obiektów - zgłoszeń.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;
import dissimlab.simcore.SimParameters;

public class ZakonczObsluge extends BasicSimEvent<Smo, Zgloszenie>
{
    private Smo smoParent;
	private SimGenerator generator;
	private double p1;
	private boolean semafor;

    public ZakonczObsluge(Smo parent, double delay, Zgloszenie zgl) throws SimControlException
    {
    	super(parent, delay, zgl);
        this.smoParent = parent;
		generator = new SimGenerator();
    }

    public ZakonczObsluge(Smo parent, SimEventSemaphore semafor, Zgloszenie zgl, boolean semaf, double p1) throws SimControlException
    {
    	super(parent, semafor, zgl);
        this.smoParent = parent;
		this.p1 = p1;
		this.semafor = semaf;
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
		if(this.semafor == false)
			this.p1 = generator.nextDouble();

		if(p1 > smoParent.getPrawdopodobienstwoP1()) {
			///SmoBis
			if (smoParent.getSmo2().dodaj(transitionParams)) {
				//
				new StartNiecierpliwieniaBis(transitionParams);
				smoParent.setWolne(true); // ustawia w smo1 wolne gniazdo
				System.out.println(simTime() + ": Koniec obsługi zgl. nr: " + transitionParams.getTenNr() + " Dodano do kolejki smoBis");
				smoParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasOdniesienia(), simTime());

				if (smoParent.getSmo2().liczbaZgl() == 1 && smoParent.getSmo2().isWolne()) { //jeśli gniazdo 'spało', aktywuje je
					smoParent.getSmo2().rozpocznijObsluge = new RozpocznijObslugeBis(smoParent.getSmo2());
				}
				// Zaplanuj dalsza obsługe w tym gnieździe
				if (smoParent.liczbaZgl() > 0) {
					smoParent.rozpocznijObsluge = new RozpocznijObsluge(smoParent); //rozpoczyna obsługę kolejnego zgłoszenia w smo1
				}
			} else { //kolejka pełna, czekaj na semaforze
				System.out.println(simTime() + ": Oczekiwanie na semaforze - zgl. nr: " + transitionParams.getTenNr());
				smoParent.zakonczObsluge = new ZakonczObsluge(smoParent, smoParent.getSemafor(), transitionParams, true, p1);
			}
		}
		else{
			///SmoTer
			smoParent.getSmo3().dodaj(transitionParams); //dodaj zgłoszenie do smoTer
			//
			new StartNiecierpliwieniaTer(transitionParams);
			smoParent.setWolne(true); // ustawia w smo1 wolne gniazdo
			System.out.println(simTime() + ": Koniec obsługi zgl. nr: " + transitionParams.getTenNr() + " Dodano do kolejki smoTer");
			smoParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasOdniesienia(), simTime());
			// Aktywuj obsługę, jeżeli kolejka była pusta (gniazdo "spało")
			if (smoParent.getSmo3().liczbaZgl()==1 && smoParent.getSmo3().isWolne()) {
				smoParent.getSmo3().rozpocznijObsluge = new RozpocznijObslugeTer(smoParent.getSmo3());
			}
			if (smoParent.liczbaZgl() > 0) {
				smoParent.rozpocznijObsluge = new RozpocznijObsluge(smoParent); //rozpoczyna obsługę kolejnego zgłoszenia w smo1
			}
		}
	}

	@Override
	public Object getEventParams() {
		// TODO Auto-generated method stub
		return null;
	}
}