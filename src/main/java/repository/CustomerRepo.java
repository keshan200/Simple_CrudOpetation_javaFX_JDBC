package repository;

import modle.CustomerModle;
import org.Test.CrudOperation.DBconnection.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {

    public  static  boolean Add(CustomerModle cusModle) throws SQLException {

        String sql = "insert into customer values(?,?,?,?)";

        Connection connection = DBconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(1,cusModle.getName());
        pstm.setObject(2,cusModle.getNic());
        pstm.setObject(3,cusModle.getAddres());
        pstm.setObject(4,cusModle.getTelNo());

        return pstm.executeUpdate()>0;

    }

    public  static  boolean Delete(String nic ) throws SQLException {

        String sql = "delete from customer where  NIC =?";


        Connection connection = DBconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,nic);

        return pstm.executeUpdate() > 0;

    }



    public static boolean Update(CustomerModle customer) throws SQLException {

        String sql = "UPDATE customer SET name = ?,address = ?,telno =? WHERE NIC = ?";

        Connection connection = DBconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,customer.getName());
        pstm.setObject(2,customer.getAddres());
        pstm.setObject(3,customer.getTelNo());
        pstm.setObject(4,customer.getNic());

        return pstm.executeUpdate() > 0;
    }



    public  static  CustomerModle Search(String nic) throws SQLException {

        String  sql = "select  * from customer where NIC =?";

        Connection connection = DBconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,nic);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {


            String name = resultSet.getString(1);
            String Nic = resultSet.getString(2);
            String addrs = resultSet.getString(3);
            int telNo = Integer.parseInt(resultSet.getString(4));


            CustomerModle customerModle = new CustomerModle(name,Nic,addrs,telNo);

            return  customerModle;

        }

        return  null;
    }



    public static List<CustomerModle> getAll() throws SQLException {

        String sql = "select * from customer";

        Connection connection = DBconnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<CustomerModle> customer = new ArrayList<>();

        while (resultSet.next()){

            String name = resultSet.getString(1);
            String nic = resultSet.getString(2);
            String addrs = resultSet.getString(3);
            int telNo = Integer.parseInt(resultSet.getString(4));


            CustomerModle customerModle = new CustomerModle(name,nic,addrs,telNo);

            customer.add(customerModle);
        }

        return  customer;
    }


}
