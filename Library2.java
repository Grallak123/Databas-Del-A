/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library2;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Library2Model;
import view.Library2View;
import view.Library2Controller;



public class Library2 extends Application {

    @Override
    public void start(Stage stage) {

        // create model, view and controller, and tie them together
        Library2Model model = new Library2Model();
        Library2View view = new Library2View(model,stage); // also creates the controller
        
        // create the window, add the view, and show it
        
        Scene scene = new Scene(view,1200,600);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();
        stage.show();
        
        stage.setOnCloseRequest(e ->{
            //make sure to close connection
            model.tryToCloseConnection();
            
            stage.close();
        });
        
              
    }

    public static void main(String[] args) {
        launch(args);
    }
}