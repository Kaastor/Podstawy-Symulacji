/**
 * Created by Przemek on 2016-01-20.
 */

import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimEventSemaphore;

public class SprzezenieZwrotneTer extends BasicSimEvent<SmoTer, Zgloszenie> {

    private SmoTer smoParent;

    public SprzezenieZwrotneTer(SmoTer parent, SimEventSemaphore semafor, Zgloszenie zgl) throws SimControlException
    {
        super(parent, semafor, zgl);
        this.smoParent = parent;
    }

    public SprzezenieZwrotneTer(SmoTer parent, double delay, Zgloszenie zgl) throws SimControlException
    {
        super(parent, delay, zgl);
        this.smoParent = parent;
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
        AppSMO.smo.dodaj(transitionParams); //dodaj zgłoszenie do smo
        smoParent.setWolne(true); // ustawia w smoter wolne gniazdo
        //
        new StartNiecierpliwienia(transitionParams);
        System.out.println(simTime() + ":SMO3-zgl. nr: " + transitionParams.getTenNr() + " Wraca do kolejki Smo");
        smoParent.MVczasy_obslugi.setValue(simTime() - transitionParams.getCzasOdniesienia(), simTime());
        if (smoParent.liczbaZgl() > 0) {
            smoParent.rozpocznijObsluge = new RozpocznijObslugeTer(smoParent); //rozpoczyna obsługę kolejnego zgłoszenia w smoTer
        }
    }

    @Override
    public Object getEventParams() {
        // TODO Auto-generated method stub
        return (Zgloszenie) transitionParams;
    }
}
