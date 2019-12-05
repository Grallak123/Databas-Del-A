/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author frith
 */
import javafx.application.Platform;
import java.io.File;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import model.Library2Model;
import model.Book;
import model.Author;
import model.Review;

/**
 *
 * @author frith
 */
public class Library2View extends StackPane {
    
    private Library2Model model;
    private Stage stage;
    private Library2Controller controller;
    private Canvas canvas;
    
    
    
    

    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    /**
     *
     * @param model
     * @param stage
     */
    public Library2View(Library2Model model,Stage stage) {
        this.model = model;
        this.stage = stage;
        
        controller = new Library2Controller(model, this);
        
        loginScene();
        
      

       
    }
    
    public void showAlert(String message) {
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setContentText(message);
       
        alert.show();
        
        //use these for changing review?
        //alert.setDialogPane(value);
      
        //alert.setOnCloseRequest(value);
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     */
    public void showDialogAddReview(int chosenBookNr, String chosenBookISBN,String chosenTitle){
        //TextField tf = new TextField("");
        TextInputDialog dialog = new TextInputDialog(":)");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter review Text:");

       // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        
        /*if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }*/

        // The Java 8 way to get the response value (with lambda expression).
      //  result.ifPresent(datext -> System.out.println("Written text: " + datext));
      result.ifPresent(datext -> controller.handleAddReview(chosenBookNr,chosenBookISBN,chosenTitle,datext));
        
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     */
    public void showDialogAddAuthor(int chosenBookNr, String chosenBookISBN,String chosenTitle){
        //TextField tf = new TextField("");
        TextInputDialog dialog = new TextInputDialog(":)");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please Author name:");

       // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        
        /*if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }*/

        // The Java 8 way to get the response value (with lambda expression).
      //  result.ifPresent(datext -> System.out.println("Written text: " + datext));
      result.ifPresent(daAuthor -> controller.handleAddAuthor(chosenBookNr,chosenBookISBN,chosenTitle,daAuthor));
        
    }    
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     */
    public void showDialogDeleteAuthor(int chosenBookNr, String chosenBookISBN,String chosenTitle){
        //TextField tf = new TextField("");
        TextInputDialog dialog = new TextInputDialog(":)");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please Author ID:");

       // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        
        /*if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }*/

        // The Java 8 way to get the response value (with lambda expression).
      //  result.ifPresent(datext -> System.out.println("Written text: " + datext));
      result.ifPresent(daID -> controller.handleDeleteAuthor(chosenBookNr,chosenBookISBN,chosenTitle,daID));
        
    }   
  
    /**
     *
     */
    public void loginScene(){
        
        this.getChildren().clear();   
         
         stage.setTitle("Login");
        
        Label loginlabel = new Label("Enter username and password");
        loginlabel.setTranslateX(40);
        loginlabel.setTranslateY(0);
        
        Label unlabel = new Label("Username:");
        unlabel.setTranslateX(-100);
        unlabel.setTranslateY(40);

        
        Label pwlabel = new Label("password:");
        pwlabel.setTranslateX(-100);
        pwlabel.setTranslateY(120);
        
        Button loginButton = new Button("Log in");
        loginButton.setTranslateY(200);
        
        Button lurker = new Button("Browse");
        lurker.setTranslateY(200);
        lurker.setTranslateX(-100);

        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(unlabel,pwlabel,loginlabel,loginButton,lurker);    
        
        
        TextField untxt1 = new TextField();
        untxt1.setText("Username");
        untxt1.setTranslateX(50);
        untxt1.setTranslateY(50);
        untxt1.setMaxSize(200, 50);
        TextField pwtxt2 = new TextField();
        pwtxt2.setText("Password");
        pwtxt2.setTranslateX(50);
        pwtxt2.setTranslateY(120);
        pwtxt2.setMaxSize(200, 50);
        
        
        loginButton.setOnMousePressed(e->{
            controller.handleCheckLogin(untxt1.getText(),pwtxt2.getText());
        });

        
        
        this.getChildren().addAll(untxt1,pwtxt2,stack);
        
        untxt1.clear();
        pwtxt2.clear();
        
        untxt1.setOnMouseClicked(ke-> {
            untxt1.requestFocus();
            untxt1.setMouseTransparent(true);
            untxt1.setEditable(true);
            
        });
        
        pwtxt2.setOnMouseClicked(ke-> {
            pwtxt2.requestFocus();
            pwtxt2.setMouseTransparent(true);
            pwtxt2.setEditable(true);
        });
        
        
        
        lurker.setOnMousePressed(e->{
            //controller.handlePrintQuery("Query1");
            model.setPassword("321");
            model.setUsername("lurker");
            model.setUserID("4");
            model.setUserRank("0");
            System.out.println("UserName: " + model.getUsername() + " Password: " + model.getPassword());
            System.out.println("UserID: " + model.getUserID() + " UserRank: " + model.getUserRank());
            
            bigTableBookScene();
        });
        
        
        
        //use this when changing view
        //this.getChildren().clear();            
            
    }
    
    /**
     *
     */
    public void mainMenuScene(){
        
        this.getChildren().clear();
        
        ComboBox<String> choice1 = new ComboBox<String>();
        
        String Query1 = "Query1";
        String Query2 = "Query2";
        
        choice1.getItems().addAll(Query1,Query2);
        
        choice1.setTranslateX(-200);
        choice1.setTranslateY(-200);
        
        //choice1.setPromptText(Query1);
        
        
        
        Button queryButton = new Button("Query Selection");
        queryButton.setTranslateX(100);
        queryButton.setTranslateY(150);
        
        
        queryButton.setOnAction(e->{
           // controller.handlePrintQuery(choice1.getValue());
        });
        
        Button booksButton = new Button("books");
        booksButton.setTranslateX(0);
        booksButton.setTranslateY(150);

        
        booksButton.setOnAction(e->{
          
            ArrayList<Book> booklist = model.getBooks();
            System.out.println("|   ISBN      " + " |   Genre   " + "|     Title     |" +
                    "     Publisher     |" + "    |   Authors    |");
            for(Book book : booklist){
            System.out.println(book);
            
            }
            System.out.println();
   
        });
        
       
        Button viewSelection = new Button("show tables");
        
  
        viewSelection.setTranslateX(-100);
        viewSelection.setTranslateY(150);
       
        viewSelection.setOnAction(e->{
            bigTableBookScene();
        });
        
        
        this.getChildren().addAll(queryButton,booksButton,choice1,viewSelection);
        
        
    }
    
    /**
     *
     */
    public void bigTableBookScene(){
        
        this.getChildren().clear();
        
        stage.setTitle("Library");
        
        
        
      //  ArrayList<Author>specAuthor = new ArrayList<Author>();
       // specAuthor.add(new Author("Josef"));
        
        TableView<Book> table = new TableView<Book>();
        ObservableList<Book> data =
            FXCollections.observableArrayList(
            //new Book("1234567891234", Book.Genre.Fantasy, "hoodtitle","shitcompany",
            //specAuthor)
           // new Book("1234563791234", Book.Genre.Crime, "sometitle","crashco")            
        );
        //data = model.getBooks();
        data = FXCollections.observableArrayList(model.getBooks());
        //System.out.println("books model ree: " + model.getBooks());
        
        
        
        final Label label = new Label("Book List");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn firstNameCol = new TableColumn("ISBN");
        firstNameCol.setMinWidth(120);
        firstNameCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("isbn"));
        firstNameCol.setEditable(true);
        firstNameCol.setOnEditStart(e->{
            
            
        });
        
        
        TableColumn lastNameCol = new TableColumn("Genre");
        lastNameCol.setMinWidth(60);
        lastNameCol.setCellValueFactory(
        new PropertyValueFactory<Book, Book.Genre>("genre"));
 
        TableColumn emailCol = new TableColumn("Title");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("title"));
        
        TableColumn pubCol = new TableColumn("Publisher");
        pubCol.setMinWidth(100);
        pubCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("publisher"));
        
        TableColumn ratingCol = new TableColumn("Rating");
        ratingCol.setMinWidth(100);
        ratingCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("rating"));
        
        TableColumn bookCreatorCol = new TableColumn("Creator");
        bookCreatorCol.setMinWidth(100);
        bookCreatorCol.setCellValueFactory(
        new PropertyValueFactory<Book, String>("bookCreator"));
        
        TableColumn authorCol = new TableColumn("Authors");
        authorCol.setMinWidth(280);
        authorCol.setCellValueFactory(
        new PropertyValueFactory<Book, ArrayList<Author>>("theAuthors"));

       
        TableColumn allReviewsCol = new TableColumn("Reviews");
        allReviewsCol.setMinWidth(280);
        allReviewsCol.setCellValueFactory(
        new PropertyValueFactory<Book, ArrayList<Review>>("theReviews"));
        
      
 
        table.setItems(data);
        /*table.getColumns().addAll(firstNameCol, lastNameCol, emailCol,pubCol,
                authorCol,bookCreatorCol,reviewCol,deleteBookCol);*/
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol,pubCol,
               ratingCol, authorCol,bookCreatorCol,allReviewsCol);
        
        
        ObservableList<String> hobbies = FXCollections.observableArrayList(
                "Crime", "Mystery", "Romance", "Drama", "Memoir", "Fantasy",
                "Learning","Any");
        ComboBox<String> pickGenre = new ComboBox<>(hobbies);
        
        
        pickGenre.setVisibleRowCount(3);
        pickGenre.setValue("Any");
        
        
        //pickGenre.setTranslateX(0);
        //pickGenre.setTranslateY(30);
        //pickGenre.getvalue,pickPublisher.getValue or just whatever in a txt
        Button searchButton = new Button("search&Update");
        Button killMeButton = new Button("Delete book");
        Button AddABookButton = new Button("Add book");
        Button returnToLoginButton = new Button("Return to login");
        returnToLoginButton.setTranslateX(100);
        returnToLoginButton.setTranslateY(100);
        Button goToAuthors = new Button("Check Authors");
        goToAuthors.setTranslateX(100);
        goToAuthors.setTranslateY(0);
        
        Button goToReviews = new Button("Check Reviews");
        goToReviews.setTranslateX(100);
        goToReviews.setTranslateY(0);                
        
 
        final TextField checkISBN = new TextField();
        checkISBN.setPromptText("ISBN");
        checkISBN.setMaxWidth(120);
        
        
        final TextField checkTitle = new TextField();
        checkTitle.setPromptText("Title");
        checkTitle.setMaxWidth(100);
        
        final TextField checkAuthor = new TextField();
        checkAuthor.setMaxWidth(100);
        checkAuthor.setPromptText("Author");
        
        final TextField checkPublisher = new TextField();
        checkPublisher.setMaxWidth(90);
        checkPublisher.setPromptText("Publisher");
        
        final TextField checkRating = new TextField();
        checkRating.setMaxWidth(60);
        checkRating.setPromptText("rating");
        
        searchButton.setOnAction(e->{
            
            controller.handleSearchAndUpdate(pickGenre.getValue(),
            checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
            checkAuthor.getText(),checkRating.getText());
          
            
           
        });
        
       
        
        AddABookButton.setOnAction(e->{
            
             if(!model.getUsername().matches("lurker")){
        
            //How do I use this? :(
            //buildAddBookDialog();
            try{
               controller.handleAddBook(pickGenre.getValue(),
                checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
                checkAuthor.getText(),checkRating.getText()); 
               //Refresh here
                checkISBN.clear();
                checkTitle.clear();
                checkPublisher.clear();
                checkAuthor.clear();
                pickGenre.setValue("Any");
                controller.handleSearchAndUpdate(pickGenre.getValue(),
                checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
                checkAuthor.getText(),checkRating.getText());
            }catch(Error err){
                showAlert(err.getMessage());
            }
            
            }else{
            //System.out.println("Get out of here Stalker!");
            showAlert("You need to log in to do this command!");
            
            }
            
        });
            
        
        killMeButton.setOnAction(e->{
            
            if(!model.getUsername().matches("lurker")){
            //start bg thread
            
            //new
            controller.handleDestroyBook(checkISBN.getText());
            
            // end bg thread
          
            
            
                checkISBN.clear();
                checkTitle.clear();
                checkPublisher.clear();
                checkAuthor.clear();
                pickGenre.setValue("Any");
                controller.handleSearchAndUpdate(pickGenre.getValue(),
                checkISBN.getText(),checkTitle.getText(),checkPublisher.getText(),
                checkAuthor.getText(),checkRating.getText());
                
            }else{
                //System.out.println("Get out of here Stalker!");
                showAlert("You need to log in to do this command!");
            
            }
            
        });
        
        goToAuthors.setOnAction(es->{
        
            boolean foundISBN = false;
            int bookListSize = 0;
            bookListSize = model.getBooks().size();
            int bookNr = 0;
            String theTitle = "";
            if(bookListSize != 0){
                for(int i = 0; i<bookListSize;i++){
                    if(model.getBooks().get(i).getIsbn().matches(checkISBN.getText())){
                        foundISBN = true;
                        bookNr = i;
                        theTitle = model.getBooks().get(i).getTitle();
                        
                    }
                    
                }
                if(foundISBN){
                    bigTableAuthorScene(bookNr,checkISBN.getText(),theTitle);
                }
                
            }
           
            
        });
        
        goToReviews.setOnAction(ed->{
        
            boolean foundISBN = false;
            int bookListSize = 0;
            bookListSize = model.getBooks().size();
            int bookNr = 0;
            String theTitle = "";
            if(bookListSize != 0){
                for(int i = 0; i<bookListSize;i++){
                    if(model.getBooks().get(i).getIsbn().matches(checkISBN.getText())){
                        foundISBN = true;
                        bookNr = i;
                        theTitle = model.getBooks().get(i).getTitle();
                        
                    }
                    
                }
                if(foundISBN){
                    bigTableReviewScene(bookNr,checkISBN.getText(),theTitle);
                }
                
            }
           
            
        });        

        
        returnToLoginButton.setOnAction(e->{
            
            loginScene();
            
        });
        



        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(checkISBN,checkTitle,checkAuthor,
                checkPublisher,checkRating,searchButton,killMeButton,AddABookButton,goToAuthors,
                goToReviews,returnToLoginButton);
        
        
        vbox.getChildren().addAll(label,table,hbox,pickGenre);
 
        this.getChildren().addAll(vbox);
        
        
        
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     */
    public void bigTableAuthorScene(int chosenBookNr, String chosenBookISBN,String chosenTitle){
        
         /***do an entire new query to get an exclusive author arraylist from the
         specified book maybe?***/
        
        this.getChildren().clear();
        
        stage.setTitle("Authors");
        
     
        
        TableView<Author> aTable = new TableView<Author>();
        ObservableList<Author> data =
            FXCollections.observableArrayList(
   
        );
 
        data = FXCollections.observableArrayList(model.getBooks().get(chosenBookNr).getTheAuthors());
        
        final Label label = new Label("Authors of " + chosenTitle);
        label.setFont(new Font("Arial", 20));
 
        aTable.setEditable(true);
        
        TableColumn authorIDCol = new TableColumn("Author ID");
        authorIDCol.setMinWidth(150);
        authorIDCol.setCellValueFactory(
        new PropertyValueFactory<Author, String>("authorID"));
        
        TableColumn theAuthorCol = new TableColumn("Author");
        theAuthorCol.setMinWidth(150);
        theAuthorCol.setCellValueFactory(
        new PropertyValueFactory<Author, String>("name"));
        
        TableColumn authorCreatorCol = new TableColumn("Author Creator");
        authorCreatorCol.setMinWidth(150);
        authorCreatorCol.setCellValueFactory(
        new PropertyValueFactory<Author, String>("authorCreator"));
        
        aTable.setItems(data);

        aTable.getColumns().addAll(authorIDCol,theAuthorCol,authorCreatorCol);
        
        
        
        Button addAuthorButton = new Button("Add Author");
        Button deleteAuthorButton = new Button("Delete Author");
        Button returnTobBigTableBookSceneButton = new Button("return to Library");
        
        addAuthorButton.setOnAction(e->{
            
            //bigTableBookScene();
            
            if(!model.getUserRank().matches("0")){
                showDialogAddAuthor(chosenBookNr,chosenBookISBN,chosenTitle);
            } else {
                showAlert("Rank is too low");
            }
            
        });
                
        deleteAuthorButton.setOnAction(e->{
            
            if(!model.getUserRank().matches("0")){
                showDialogDeleteAuthor(chosenBookNr,chosenBookISBN,chosenTitle);
            } else {
                showAlert("Rank is too low");
            }

            
        });
        
        
        returnTobBigTableBookSceneButton.setOnAction(e->{
            
            bigTableBookScene();
            
        });
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(addAuthorButton,deleteAuthorButton,
                returnTobBigTableBookSceneButton);
        
        
        vbox.getChildren().addAll(label,aTable,hbox);
 
        this.getChildren().addAll(vbox);

        
        
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     */
    public void bigTableReviewScene(int chosenBookNr, String chosenBookISBN,String chosenTitle){
        
        /***do an entire new query to get an exclusive review arraylist from the
         specified book maybe?***/
        
        //and also just throw in an ArrayList<Review> and take all their values
        // and just add them manually me thinks?
        
        System.out.println(model.getBooks().get(chosenBookNr).toString());
        System.out.println(chosenBookNr);
        
        
        this.getChildren().clear();
        
        stage.setTitle("Reviews");
        

        TableView<Review> rTable = new TableView<Review>();
        ObservableList<Review> data =
            FXCollections.observableArrayList(
            //new Book("1234567891234", Book.Genre.Fantasy, "hoodtitle","shitcompany",
            //specAuthor)
           // new Book("1234563791234", Book.Genre.Crime, "sometitle","crashco")            
        );
  
        if(model.getBooks().get(chosenBookNr).getTheReviews() != null){
            data = FXCollections.observableArrayList(model.getBooks().get(chosenBookNr).getTheReviews());
        } else{
            System.out.println("No reviews for this book");
        
       }
        
        final Label label = new Label("Reviewers of " + chosenTitle);
        label.setFont(new Font("Arial", 20));
 
        rTable.setEditable(true);
       
        TableColumn theReviewerIDCol = new TableColumn("Reviewer ID");
        theReviewerIDCol.setMinWidth(50);
        theReviewerIDCol.setCellValueFactory(
        new PropertyValueFactory<Review, String>("reviewCreatorID"));
        
        TableColumn theReviewerNameCol = new TableColumn("Reviewer name");
        theReviewerNameCol.setMinWidth(100);
        theReviewerNameCol.setCellValueFactory(
        new PropertyValueFactory<Review, String>("reviewCreatorUName"));
        
        TableColumn reviewEntryDateTimeCol = new TableColumn("Review Date&Time");
        reviewEntryDateTimeCol.setMinWidth(300);
        reviewEntryDateTimeCol.setCellValueFactory(
        new PropertyValueFactory<Review, String>("entryDateTime"));
        
        TableColumn reviewTextCol = new TableColumn("Review Text");
        reviewTextCol.setMinWidth(400);
        reviewTextCol.setCellValueFactory(
        new PropertyValueFactory<Review, String>("reviewText"));   
        reviewTextCol.setEditable(true);
        
        
        
        rTable.setItems(data);
 
        rTable.getColumns().addAll(theReviewerIDCol, theReviewerNameCol,
                reviewEntryDateTimeCol,reviewTextCol);
        
        
        Button writeTextButton = new Button("write text");
        Button deleteReviewButton = new Button("Delete Review");
        
        
        writeTextButton.setOnAction(esy->{
            if(!model.getUserRank().matches("0")){
                showDialogAddReview(chosenBookNr,chosenBookISBN,chosenTitle);
            } else {
                showAlert("Rank is too low");
            }
        });  
        
        deleteReviewButton.setOnAction(esal->{
            if(!model.getUserRank().matches("0")){
                controller.handleDeleteReview(chosenBookISBN);
            } else {
                showAlert("Rank is too low");
            }
        });          


        
        
        Button returnTobBigTableBookSceneButton = new Button("return to Library");
        
        returnTobBigTableBookSceneButton.setOnAction(e->{
            
            bigTableBookScene();
            
        });
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        hbox.getChildren().addAll(writeTextButton,
                deleteReviewButton,returnTobBigTableBookSceneButton);
        
        
        vbox.getChildren().addAll(label,rTable,hbox);
 
        this.getChildren().addAll(vbox);

        
        
    }

}