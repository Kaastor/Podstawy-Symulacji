/**
 * @author Dariusz Pierzchala
 *
 * Description: Obiekt przejmujący zgłoszenia do utylizacji. Działa w wyniku powiadomień z brokera (na sybskrybowane zdarzenia)
 */

import dissimlab.broker.Dispatcher;
import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;



public class UtylizatorKoncowyTer extends BasicSimObj
{
    //
    public Dispatcher infoDystr;
    //

    public UtylizatorKoncowyTer() throws SimControlException
    {
        super();
        infoDystr = getDispatcher();
        infoDystr.subscribe(this, ZakonczObslugeTer.class);
    }



    @Override
    public void reflect(IPublisher publisher, INotificationEvent event) {
        System.out.println("-Utylizator końcowyTer: reflect 1 - zgloszenie nr: " + ((Zgloszenie)((ZakonczObslugeTer)event).getEventParams()).getTenNr());
    }


    @Override
    public boolean filter(IPublisher publisher, INotificationEvent event) {
        // Simply true but should be more complex
        if (((Zgloszenie)((ZakonczObslugeTer)event).getEventParams()).getTenNr()<5)
        {
            System.out.println("-Utylizator końcowyTer: Filtr OK");
            return true;
        }
        else
        {
            //infoDystr.unsubscribe(this, ZakonczObslugeBis.class); //Error due to concurrent modification on Map
            System.out.println("-Utylizator końcowyTer: Filtr false -> unsubscribe");
            return false;
        }
    }
}