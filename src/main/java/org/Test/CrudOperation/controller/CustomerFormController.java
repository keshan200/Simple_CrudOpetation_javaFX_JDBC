package org.Test.CrudOperation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modle.CustomerModle;
import modle.TM.CustomerTM;
import repository.CustomerRepo;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddres;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTelNo;


    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTelNo;




    public  void  initialize(){
        setcellValues();
        loadAllCustomers();
    }




    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String nic = txtNIC.getText();
        if (!nic.isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want Delete ? ").showAndWait();
        }
        try {
            if (CustomerRepo.Delete(nic)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted ").show();
                clearFields();
                loadAllCustomers();
            }else {
                new Alert(Alert.AlertType.ERROR, "Can't Delete Customer!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }



    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String name = txtName.getText();
        String nic = txtNIC.getText();
        String addrs = txtAddress.getText();
        int telNo = Integer.parseInt(txtTelNo.getText());


        CustomerModle customerModle = new CustomerModle(name,nic,addrs,telNo);

        try {
            boolean isSaved = CustomerRepo.Add(customerModle);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!!").show();
                 clearFields();
                 loadAllCustomers();
            }else {
                new Alert(Alert.AlertType.ERROR, "Can't Save Customer!!").show();
                clearFields();
            }

        } catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {

        String nic = txtNIC.getText();

        CustomerModle cusmodel = CustomerRepo.Search(nic);

        if (cusmodel != null) {

            txtName.setText(cusmodel.getName());
            txtNIC.setText(cusmodel.getNic());
            txtAddress.setText(cusmodel.getAddres());
            txtTelNo.setText(String.valueOf(cusmodel.getTelNo()));

        }else{
            new Alert(Alert.AlertType.INFORMATION, "Customer not found!").show();
            clearFields();

        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String name = txtName.getText();
        String nic = txtNIC.getText();
        String addrs = txtAddress.getText();
        int telNo = Integer.parseInt(txtTelNo.getText());

        CustomerModle customerModle = new CustomerModle(name,nic,addrs,telNo);

        boolean isUpdate = CustomerRepo.Update(customerModle);

        if (isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated Sucsesssfully!").show();
             clearFields();
             loadAllCustomers();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't Update").show();
            clearFields();
        }

    }



    private  void loadAllCustomers(){
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        try {
            List<CustomerModle> cusList = CustomerRepo.getAll();
            for (CustomerModle cusModle : cusList){

                CustomerTM TM = new CustomerTM(cusModle.getName(),cusModle.getNic(),cusModle.getAddres(),cusModle.getTelNo());

                obList.add(TM);
                tblCustomer.setItems(obList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private  void setcellValues(){    //use cell name for customerTM cls variable name those are must be same to show values
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
       colAddres.setCellValueFactory(new PropertyValueFactory<>("addres"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
    }

    private  void clearFields(){
        txtName.setText("");
        txtNIC.setText("");
        txtAddress.setText("");
        txtTelNo.setText("");
    }

}
