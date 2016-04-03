import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

/**
 *
 * @author Dariusz Pierzchala

 */
public class KoniecNiecierpliwieniaBis extends BasicSimEvent<Zgloszenie, Object>
{
    private SimGenerator generator;
    private Zgloszenie parent;

    public KoniecNiecierpliwieniaBis(Zgloszenie parent, double delay) throws SimControlException
    {
        super(parent, delay);
        generator = new SimGenerator();
        this.parent = parent;
    }

    public KoniecNiecierpliwieniaBis(Zgloszenie parent) throws SimControlException
    {
        super(parent);
        generator = new SimGenerator();
        this.parent = parent;
    }

    @Override
    protected void onInterruption() throws SimControlException {
        System.out.println(simTime()+":SMO2- Przerwanie niecierpliwości zgl. nr: " + parent.getTenNr());
    }

    @Override
    protected void onTermination() throws SimControlException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void stateChange() throws SimControlException {
        System.out.println(simTime()+":SMO2- Koniec niecierpliwości zgl. nr: " + parent.getTenNr());
        if (parent.smoBis.usunWskazany(parent)){
            System.out.println(simTime()+":SMO2- Usunięto z kolejki zgl. nr: " + parent.getTenNr());
            double lutrac = parent.smoBis.MVutraconeZgl.getValue();
            parent.smoBis.MVutraconeZgl.setValue(lutrac++);
        }
        else
            System.out.println(simTime()+":SMO2- Problem z usunięciem z kolejki zgl. nr: " + parent.getTenNr());
    }

    @Override
    public Object getEventParams() {
        // TODO Auto-generated method stub
        return null;
    }
}