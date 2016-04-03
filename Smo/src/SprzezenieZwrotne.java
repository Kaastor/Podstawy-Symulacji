/**
 * Created by Przemek on 2016-01-20.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;

public class SprzezenieZwrotne extends BasicSimEvent<SmoBis, Zgloszenie> {

    private SmoBis smoParent;
    private boolean pierwszyRaz;

    public SprzezenieZwrotne(SmoBis parent, SimEventSemaphore semafor, Zgloszenie zgl, boolean pierwszyRaz) throws SimControlException
    {
        super(parent, semafor, zgl);
        this.smoParent = parent;
        this.pierwszyRaz = pierwszyRaz;
    }

    public SprzezenieZwrotne(SmoBis parent, double delay, Zgloszenie zgl, boolean pierwszyRaz) throws SimControlException
    {
        super(parent, delay, zgl);
        this.smoParent = parent;
        this.pierwszyRaz = pierwszyRaz;
    }

    @Override
    protected void onInterruption() throws SimControlException {
        System.out.println(simTime()+": !Przerwanie obsługi przy zgl. nr: " + transitionParams.getTenNr());
    }

    @Override
    protected void onTermination() throws SimControlException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void stateChange() throws SimControlException {
        smoParent.MVczasy_oczekiwania.setValue(simTime() - transitionParams.getCzasOdniesienia());
        if (smoParent.dodaj(transitionParams)) { //proba dodania do kolejki
            if(pierwszyRaz == true && smoParent.isWolne()!=true) {
                System.out.println(simTime() + ": SMO2-Wypuszczone Zgl. nr: " + transitionParams.getTenNr() + ", dodano do kolejki smoBis");
                //
                new StartNiecierpliwieniaBis(transitionParams);
                smoParent.setWolne(true);
                if (smoParent.liczbaZgl() == 1 && smoParent.isWolne()) { //jeśli gniazdo 'spało', aktywuje je
                    smoParent.rozpocznijObsluge = new RozpocznijObslugeBis(smoParent);
                }
                // Zaplanuj dalsza obsługe w tym gnieździe
                if (smoParent.liczbaZgl() > 0) {
                    smoParent.rozpocznijObsluge = new RozpocznijObslugeBis(smoParent); //rozpoczyna obsługę kolejnego zgłoszenia w smo1
                }
            }
            else{
                System.out.println(simTime() + ": SMO2-Zgl. oczekujace nr: " + transitionParams.getTenNr() + ", dodano do kolejki smoBis");
            }
        } else { // nie dodalo do kolejki, oczekiwanie na semaforze
            smoParent.sprzezenieZwrotne = new SprzezenieZwrotne(smoParent, smoParent.getSemafor(), transitionParams, false);
            if(pierwszyRaz == true && smoParent.isWolne()!=true) {
                System.out.println(simTime() + ": SMO2-Wypuszczone Zgl. nr: " + transitionParams.getTenNr() + " oczekuje na semaforze przed kolejka SmoBis ");
                smoParent.setWolne(true);
                if (smoParent.liczbaZgl() == 1 && smoParent.isWolne()) { //jeśli gniazdo 'spało', aktywuje je
                    smoParent.rozpocznijObsluge = new RozpocznijObslugeBis(smoParent);
                }
                // Zaplanuj dalsza obsługe w tym gnieździe
                if (smoParent.liczbaZgl() > 0) {
                    smoParent.rozpocznijObsluge = new RozpocznijObslugeBis(smoParent); //rozpoczyna obsługę kolejnego zgłoszenia w smoBis
                }
            }else
                System.out.println(simTime() + ": SMO2- Zgl. nr: " + transitionParams.getTenNr() + " ciągle oczekuje na semaforze przed kolejka SmoBis");
        }
    }

    @Override
    public Object getEventParams() {
        // TODO Auto-generated method stub
        return (Zgloszenie) transitionParams;
    }
}
