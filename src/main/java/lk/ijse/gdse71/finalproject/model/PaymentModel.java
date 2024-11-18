package lk.ijse.gdse71.finalproject.model;

import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.PaymentDTO;
import lk.ijse.gdse71.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select id from Payment order by id desc limit 1");

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newId = i+1;
            return String.format("P%03d",newId);
        }
        return "P001";
    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Payment");

        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    rst.getString(1),                    //id
                    rst.getDouble(2),                   //amount
                    rst.getDate(3).toLocalDate(),        //date
                    rst.getString(4),                   //type
                    rst.getString(5),                    //status
                    rst.getString(6)                     //reservationId

            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Payment values (?,?,?,?,?,?)",
                paymentDTO.getId(),
                paymentDTO.getAmount(),
                paymentDTO.getDate(),
                paymentDTO.getType(),
                paymentDTO.getStatus(),
                paymentDTO.getReservationId()

        );

    }


    public void updateAdvancePaymentStatus(String reservationId) throws SQLException {
        String query = "update Payment set status = 'Done' where reservationId = ? and type = 'Advance Payment'";
        CrudUtil.execute(query, reservationId);
    }
}
