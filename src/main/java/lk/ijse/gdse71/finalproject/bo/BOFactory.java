package lk.ijse.gdse71.finalproject.bo;

import lk.ijse.gdse71.finalproject.bo.custom.impl.*;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.SuperDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        return (boFactory == null) ? boFactory
                = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMER, LOGIN, MAINTENANCERECORD, MILEAGETRACKING, PAYMENT, QUERY, RESERVATION, VEHICLEDAMAGE, VEHICLE, MAKERESERVATION
    }

    public SuperBO getBO(BOFactory.BOTypes boTypes) {
        switch (boTypes) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case MAINTENANCERECORD:
                return new MaintenanceRecordBOImpl();
            case MILEAGETRACKING:
                return new MileageTrackingBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case QUERY:
                return new QueryBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            case VEHICLEDAMAGE:
                return new VehicleDamageBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case MAKERESERVATION:
                return new MakeReservationBOImpl();
            default:
                return null;
        }
    }
}
