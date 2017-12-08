package fys.luggagem;

import fys.luggagem.models.Data;
import fys.luggagem.models.Luggage;
import fys.luggagem.models.Matching;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MatchingController implements Initializable {

    private Data data = MainApp.getData();
    private MyJDBC db = MainApp.myJDBC;
    private ObservableList<Luggage> luggageList = FXCollections.observableArrayList();
    private Matching matching = new Matching();

    @FXML
    private Label label;
    @FXML
    private Label type;
    @FXML
    private Label brand1;
    @FXML
    private Label primary;
    @FXML
    private Label secondary;
    @FXML
    private Button confirmButton;
    @FXML
    private Label confirmLabel;

    private Luggage luggage = new Luggage();

    @FXML
    private TableView matchingTableview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getLuggageDetails();

        matchingTableview.setItems(this.luggageList);

        luggage.getMatchingLuggage(db, this.luggageList);

        for (int cnr = 0; cnr < matchingTableview.getColumns().size(); cnr++) {
            TableColumn tc = (TableColumn) matchingTableview.getColumns().get(cnr);
            String propertyName = tc.getId();
            if (propertyName != null && !propertyName.isEmpty()) {
                tc.setCellValueFactory(new PropertyValueFactory<>(propertyName));
                System.out.println("Attached column '" + propertyName + "' in tableview to matching attribute");
            }
        }

        matchingTableview.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    handleSelectionAction();
                }
            });
            return row;
        });
    }
    
    private Luggage getSelectedMatch(){
        return (Luggage) matchingTableview.getSelectionModel().getSelectedItem();
    }
    
    private void handleSelectionAction(){
        Luggage selectedMatch = getSelectedMatch();
        matching.setLabelNr(selectedMatch.getLabelNr());
        matching.setLuggageType(selectedMatch.getLuggageType());
        matching.setPrimaryColor(selectedMatch.getPrimaryColor());
        matching.setSecondaryColor(selectedMatch.getSecondaryColor());
        
        confirmButton.setDisable(false);
        
        System.out.print(selectedMatch.getBrand()+"\n");
        System.out.print(luggage.getBrand());
        
        System.out.print(selectedMatch.getLabelNr()+"\n");
        System.out.print(luggage.getLabelNr());
        
    }
    
    @FXML
    private void confirmUpload(ActionEvent event){
        confirmLabel.setText("Match has been confirmed");
        uploadMatch();
    }
    
    private void uploadMatch(){
        Connection conn = db.getConnection();
        String updateCaseStatus = "UPDATE luggage SET case_status = 0 WHERE registrationnr = ?";
        String uploadMatch = "INSERT INTO matches (registrationnr) VALUES (?)";
        
        
        PreparedStatement ps = null;
        try{
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(updateCaseStatus);
            ps.setInt(1, db.getLuggageRegistrationNr());
            ps.executeUpdate();
            conn.commit();
        } catch(SQLException e){
            System.err.println("SQL ex in uploadMatch->updateCaseStatus: " + e);
        }
        
        try{
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(uploadMatch);
            ps.setInt(1, db.getLuggageRegistrationNr());
            ps.executeUpdate();
            conn.commit();
        } catch(SQLException e){
            System.err.println("SQL ex in uploadMatch: " + e);
        }
    }

    private void getLuggageDetails() {
        Connection conn = db.getConnection();
        String luggageDetails = "SELECT labelnr, luggage_type, brand, primary_color, secondary_color FROM luggage WHERE registrationnr = ?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(luggageDetails);
//            To DO
            ps.setInt(1, db.getLuggageRegistrationNr());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

//                labelnr = rs.getString(1);
                luggage.setLabelNr(rs.getString("labelnr"));
                luggage.setLuggageType(rs.getString("luggage_type"));
                luggage.setBrand(rs.getString("brand"));
                luggage.setPrimaryColor(rs.getString("primary_color"));
                luggage.setSecondaryColor(rs.getString("secondary_color"));

                label.setText(luggage.getLabelNr());
                type.setText(luggage.getLuggageType());
                brand1.setText(luggage.getBrand());
                primary.setText(luggage.getPrimaryColor());
                secondary.setText(luggage.getSecondaryColor());
            }

        } catch (SQLException e) {
            System.err.println("SQL ex in getLuggageDetails: " + e);
        }
    }

}
