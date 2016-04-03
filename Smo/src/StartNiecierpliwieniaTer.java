import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

/**
 *
 * @author Dariusz Pierzchala
 * Description: Zdarzenie początkowe niecierpliwości zgłoszenia. Rozpoczyna niecierpliwość przez losowy czas
 */
public class StartNiecierpliwieniaTer extends BasicSimEvent<Zgloszenie, Object>
{
    private SimGenerator generator;
    private Zgloszenie parent;

    public StartNiecierpliwieniaTer(Zgloszenie parent, double delay) throws SimControlException
    {
        super(parent, delay);
        generator = new SimGenerator();
        this.parent = parent;
    }

    public StartNiecierpliwieniaTer(Zgloszenie parent) throws SimControlException
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
        System.out.println(simTime()+":SMO3- Początek niecierpliwości zgl. nr: " + parent.getTenNr());
        double odstep = generator.normal(20, 2);
        parent.koniecNiecierpliwieniaTer = new KoniecNiecierpliwieniaTer(parent, odstep);
    }

    @Override
    public Object getEventParams() {
        // TODO Auto-generated method stub
        return null;
    }
}