package lk.ijse.gdse71.finalproject.bo.custom.impl;

import lk.ijse.gdse71.finalproject.bo.custom.LoginBO;
import lk.ijse.gdse71.finalproject.dao.DAOFactory;
import lk.ijse.gdse71.finalproject.dao.custom.LoginDAO;
import lk.ijse.gdse71.finalproject.dao.custom.impl.LoginDAOImpl;
import lk.ijse.gdse71.finalproject.dto.CustomerDTO;
import lk.ijse.gdse71.finalproject.dto.LoginDTO;
import lk.ijse.gdse71.finalproject.entity.Customer;
import lk.ijse.gdse71.finalproject.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public boolean saveLogin(LoginDTO loginDTO) throws SQLException {
        return loginDAO.save(new Login(
                loginDTO.getUserName(),
                loginDTO.getPassword(),
                loginDTO.getEmail()
        ));
    }

    public LoginDTO findByUserName(String userName) throws SQLException {
       Login login = loginDAO.findByUserName(userName);
       if(login != null){
           return new LoginDTO(login.getUserName(), login.getPassword(), login.getEmail());
       }else{
           return null;
       }
    }

    public boolean updateLogin(LoginDTO loginDTO) throws SQLException {
       return false;
    }

    @Override
    public boolean deleteLogin(String dto) throws SQLException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException {
        return " ";
    }

    @Override
    public ArrayList<LoginDTO> getAll() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<LoginDTO> searchLoginDetails(String keyword) throws SQLException {
        return null;
    }

    public LoginDTO findByEmail(String email) throws SQLException {
        Login login =  loginDAO.findByEmail(email);
        if(login != null){
            return new LoginDTO(login.getUserName(), login.getPassword(), login.getEmail());
        }else{
            return null;
        }
    }

    @Override
    public boolean updatePasswordByEmail(String emailText, String newPassword) throws SQLException {
        return loginDAO.updatePasswordByEmail(emailText,newPassword);
    }
}
