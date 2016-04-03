import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

/**
 * 
 * @author Dariusz Pierzchala

 */
public class KoniecNiecierpliwienia extends BasicSimEvent<Zgloszenie, Object>
{
    private SimGenerator generator;
    private Zgloszenie parent;

    public KoniecNiecierpliwienia(Zgloszenie parent, double delay) throws SimControlException
    {
    	super(parent, delay);
    	generator = new SimGenerator();
        this.parent = parent;
    }

    public KoniecNiecierpliwienia(Zgloszenie parent) throws SimControlException
    {
    	super(parent);
    	generator = new SimGenerator();
        this.parent = parent;
    }
    
	@Override
	protected void onInterruption() throws SimControlException {
        System.out.println(simTime()+": Przerwanie niecierpliwości zgl. nr: " + parent.getTenNr());
	}

	@Override
	protected void onTermination() throws SimControlException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stateChange() throws SimControlException {
        System.out.println(simTime()+": Koniec niecierpliwości zgl. nr: " + parent.getTenNr());
        if (parent.smo.usunWskazany(parent)){
            System.out.println(simTime()+": Usunięto z kolejki zgl. nr: " + parent.getTenNr());
            double lutrac = parent.smo.MVutraconeZgl.getValue();
            parent.smo.MVutraconeZgl.setValue(lutrac++);
        }
        else
            System.out.println(simTime()+": Problem z usunięciem z kolejki zgl. nr: " + parent.getTenNr());       	
	}

	@Override
	public Object getEventParams() {
		// TODO Auto-generated method stub
		return null;
	}
}