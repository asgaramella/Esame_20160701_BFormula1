package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Circuit;
import it.polito.tdp.formulaone.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Circuit> boxCircuiti;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Circuit c=this.boxCircuiti.getValue();
    	if(c==null){
    		txtResult.appendText("ERRORE: Selezionare un circuito");
    		return;
    	}
    	
    	txtResult.appendText("Il costruttore con il miglior punteggio è "+model.getBest(c).toString()+"\n");
    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    	String ks=this.textInputK.getText();
    	if(ks.equals(" ")){
    		txtResult.appendText("ERRORE: inserire paramentro del dream team");
    		return;
    	}
    	
    	int k;
    	try{
    	k=Integer.parseInt(ks);
    	}catch(NumberFormatException e){
    		txtResult.appendText("Inserire un numero!");
    		return;
    	}
    	
    	txtResult.appendText(model.dreamTeam(k).toString());
    }

    @FXML
    void initialize() {
        assert boxCircuiti != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	this.boxCircuiti.getItems().addAll(model.getAllCircuits());
    	
    }
}
