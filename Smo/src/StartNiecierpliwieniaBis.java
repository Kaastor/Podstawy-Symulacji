import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

/**
 *
 * @author Dariusz Pierzchala
 * Description: Zdarzenie początkowe niecierpliwości zgłoszenia. Rozpoczyna niecierpliwość przez losowy czas
 */
public class StartNiecierpliwieniaBis extends BasicSimEvent<Zgloszenie, Object>
{
    private SimGenerator generator;
    private Zgloszenie parent;

    public StartNiecierpliwieniaBis(Zgloszenie parent, double delay) throws SimControlException
    {
        super(parent, delay);
        generator = new SimGenerator();
        this.parent = parent;
    }

    public StartNiecierpliwieniaBis(Zgloszenie parent) throws SimControlException
    {
        super(parent);
        generator = new SimGenerator();
        this.parent = parent;
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
        System.out.println(simTime()+":SMO2- Początek niecierpliwości zgl. nr: " + parent.getTenNr());
        double odstep = generator.erlang(20, 0.5);
        parent.koniecNiecierpliwieniaBis = new KoniecNiecierpliwieniaBis(parent, odstep);
    }

    @Override
    public Object getEventParams() {
        // TODO Auto-generated method stub
        return null;
    }
}