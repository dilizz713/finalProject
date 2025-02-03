package lk.ijse.gdse71.finalproject.dao;

import lk.ijse.gdse71.finalproject.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory
                =new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,LOGIN,MAINTENANCERECORD,MILEAGETRACKING,PAYMENT,QUERY,RESERVATION,VEHICLEDAMAGE,VEHICLE
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            case MAINTENANCERECORD:
                return new MaintenanceRecordDAOImpl();
            case MILEAGETRACKING:
                return new MileageTrackingDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case VEHICLEDAMAGE:
                return new VehicleDamageDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            default:
                return null;
        }
    }
}
