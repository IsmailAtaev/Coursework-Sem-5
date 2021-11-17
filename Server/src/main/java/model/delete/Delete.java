package model.delete;

import com.example.model.tour.Tour;
import model.bd.dbhtour.DBHTour;
import model.bd.idbhandler.IDBHandler;
import java.util.ArrayList;

public class Delete {

    private IDBHandler idbHandlerTour = new DBHTour();

    public boolean deleteTour(int id, ArrayList<Object> objects) {
        ArrayList<Tour> tours = (ArrayList<Tour>) objects.clone();
        for (Tour t : tours) {
            if (id == t.getId()) {
                return idbHandlerTour.deleteObj(t);
            }
        }
        return false;
    }
}
