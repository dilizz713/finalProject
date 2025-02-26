package lk.ijse.gdse71.finalproject.bo.custom.impl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse71.finalproject.bo.custom.MakeReservationBO;
import lk.ijse.gdse71.finalproject.controller.GenerateBillController;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.SQLUtil;
import lk.ijse.gdse71.finalproject.dao.custom.MileageTrackingDAO;
import lk.ijse.gdse71.finalproject.dao.custom.PaymentDAO;
import lk.ijse.gdse71.finalproject.dao.custom.ReservationDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.MileageTrackingDAOImpl;
import lk.ijse.gdse71.finalproject.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.gdse71.finalproject.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.gdse71.finalproject.db.DBConnection;
import lk.ijse.gdse71.finalproject.dto.MileageTrackingDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.dto.ReservationDTO;
import lk.ijse.gdse71.finalproject.entity.MileageTracking;
import lk.ijse.gdse71.finalproject.entity.Payment;
import lk.ijse.gdse71.finalproject.entity.Reservation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MakeReservationBOImpl implements MakeReservationBO {
    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    MileageTrackingDAO mileageTrackingDAO = (MileageTrackingDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MILEAGETRACKING);

    @Override
    public void saveReservationAndPayment(ReservationDTO reservationDTO, PaymentDTO paymentDTO, Button updatePAymentButton) throws SQLException {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isReservationSaved = reservationDAO.save(new Reservation(
                    reservationDTO.getId(),
                    reservationDTO.getCustomerId(),
                    reservationDTO.getVehicleId(),
                    reservationDTO.getStatus(),
                    reservationDTO.getReservationDate()
            ));
            if (!isReservationSaved) {
                throw new SQLException("Failed to save reservation.");
            }


            boolean isPaymentSaved = paymentDAO.save(new Payment(
                    paymentDTO.getId(),
                    paymentDTO.getDate(),
                    paymentDTO.getStatus(),
                    paymentDTO.getReservationId(),
                    paymentDTO.getAdvancePayment(),
                    paymentDTO.getFullPayment()
            ));

            if (!isPaymentSaved) {
                throw new SQLException("Failed to save payment.");
            }
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Reservation and payment saved successfully!").show();
        } catch (Exception e) {


            if (connection != null) {
                connection.rollback();
            }
            new Alert(Alert.AlertType.ERROR, "Failed to save reservation and payment:").show();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
        updatePAymentButton.setDisable(false);


    }

    @Override
    public void updatePayment(Payment entity, String status, String reservationId, Button updatePAymentButton) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isPaymentUpdated = paymentDAO.update(entity);

            if (!isPaymentUpdated) {
                throw new SQLException("Failed to update payment");
            }
            if (status.equalsIgnoreCase("Done")) {
                /*boolean isReservationUpdated = SQLUtil.execute(connection,
                        "update Reservation set status=? where id=?", "Done", reservationId
                );*/
                boolean isReservationUpdated = reservationDAO.updateReservationStatus( reservationId, "Done");
                if (!isReservationUpdated) {
                    throw new SQLException("Failed to update reservation status.");
                }
            }

            connection.commit();

            new Alert(Alert.AlertType.INFORMATION, "Payment and reservation status updated successfully!").show();


        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update payment and reservation status.").show();
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                showBillUI(reservationId, entity.getId(), updatePAymentButton);
            }
        }
    }

    @Override
    public void showBillUI(String reservationId, String paymentId, Button updatePAymentButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bill-view.fxml"));
            Parent root = loader.load();

            GenerateBillController billController = loader.getController();

            MileageTracking mileage = mileageTrackingDAO.getMileageDetails(reservationId);
            Payment payment = paymentDAO.getPaymentDetails(paymentId);
            String vehicleId = reservationDAO.getVehicleIdByReservationId(reservationId);

            billController.setBillDetails(reservationId, paymentId, payment, mileage, vehicleId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            Window currentWindow = updatePAymentButton.getScene().getWindow();
            stage.initOwner(currentWindow);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load the bill UI!").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
