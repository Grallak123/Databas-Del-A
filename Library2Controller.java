/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;




import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;

import model.Author;
import model.Book;
import model.Library2Model;
import model.Review;

/**
 *
 * @author frith
 */
public class Library2Controller {
    
    private final Library2Model model;
    private final Library2View view;

    /**
     *
     * @param model
     * @param view
     */
    public Library2Controller(Library2Model model, Library2View view) {
        this.model = model;
        this.view = view;
    }
    
    /**
     *
     * @param username
     * @param password
     */
    public void handleCheckLogin(String username,String password){
        System.out.println(username + " " + password);
        System.out.println("ree");
        try{
            model.tryToConnect(username,password);
            //view.mainMenuScene();
            model.setPassword(password);
            model.setUsername(username);
            view.bigTableBookScene();
        }catch(SQLException sqle){
            System.out.println(sqle.getMessage());
        } catch(Exception er){
            System.out.println(er.getMessage());
        }

        
    }
    
    /**
     *
     * @param chosenGenre
     * @param chosenISBN
     * @param chosenTitle
     * @param chosenPublisher
     * @param chosenAuthor
     * @param chosenRating
     */
    public void runTask1(String chosenGenre,String chosenISBN,
    String chosenTitle,String chosenPublisher,String chosenAuthor,String chosenRating) 
    {
  
       
        String customQuery = "";
        
        customQuery += "Select book.*,authorname,authorcreator,author.authorid from book";

        if("Any".matches(chosenGenre.toString())){  
            customQuery += " join authorof on authorof.isbn = book.isbn";
            customQuery += " join author on author.authorid = authorof.authorid";
            customQuery += " where book.isbn like '%" + chosenISBN + "%'";
            customQuery += " and book.title like '%" + chosenTitle + "%'";
            customQuery += " and book.publisher like '%" + chosenPublisher + "%'";
            customQuery += " and book.rating like '%" + chosenRating + "%'";
            customQuery += " and author.authorname like '%" + chosenAuthor + "%'";
           // customQuery += " order by author.authorID ASC";
            customQuery += " order by isbn DESC";
            
        }else{ 
            customQuery += " join authorof on authorof.isbn = book.isbn";
            customQuery += " join author on author.authorid = authorof.authorid";
            customQuery += " where book.genre = '" + chosenGenre+"'"; 
            customQuery += " and book.isbn like '%" + chosenISBN + "%'";
            customQuery += " and book.title like '%" + chosenTitle + "%'";
            customQuery += " and book.publisher like '%" + chosenPublisher + "%'";
            customQuery += " and book.rating like '%" + chosenRating + "%'";
            customQuery += " and author.authorname like '%" + chosenAuthor + "%'";
            //customQuery += " order by author.authorID ASC";
            customQuery += " order by isbn DESC";
        }

        //System.out.println(customQuery);
        
        try{
          
            final ArrayList<Book> aBookList  =  new ArrayList<Book>(model.executeSearchAndUpdateQuery(customQuery));
        
            Platform.runLater(new Runnable() 
            {
                    @Override
                    public void run() 
                    {
                      
                        model.setBookList(aBookList);
        
                        view.bigTableBookScene();
                        
                    }
            });
          
        }catch(SQLException sqla){
            System.out.println(sqla.getMessage());
        } catch (InterruptedException edo) 
        {
                edo.printStackTrace();
        }
            
                
         
          
    }  

    /**
     *
     * @param chosenGenre
     * @param chosenISBN
     * @param chosenTitle
     * @param chosenPublisher
     * @param chosenAuthor
     * @param chosenRating
     */
    public void handleSearchAndUpdate(String chosenGenre,String chosenISBN,
           String chosenTitle,String chosenPublisher,String chosenAuthor,String chosenRating){
                
               // Create a Runnable
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                runTask1(chosenGenre,chosenISBN,chosenTitle,chosenPublisher,
                        chosenAuthor,chosenRating);
            }
        };
 
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();

      
   }

    /**
     *
     * @param anISBN
     */
    public void runTask2(String anISBN){
       
    
        if(anISBN.length() == 13){
            String deleteBookQuery = "";
            deleteBookQuery += "Delete from book where isbn = '"
            + anISBN + "'";
            
        
            
            try{
          
                model.executeDeleteBook(deleteBookQuery);
        
                Platform.runLater(new Runnable() 
                {
                        @Override
                        public void run() 
                        {
                      

                        view.bigTableBookScene();
                        
                     }
                });
          
            }catch(SQLException sqla){
                System.out.println(sqla.getMessage());
            } catch (InterruptedException edo) 
            {
                edo.printStackTrace();
            }            
        
        }       
       
       
   }
   
    /**
     *
     * @param anISBN
     */
    public void handleDestroyBook(String anISBN){
        // just make a call and kill any isbn with that number ree
        
        // create bg
        
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                runTask2(anISBN);
            }
        };
 
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();       
        
       
    }
   
    /**
     *
     * @param chosenGenre
     * @param chosenISBN
     * @param chosenTitle
     * @param chosenPublisher
     * @param chosenAuthor
     * @param chosenRating
     */
    public void runTask3(String chosenGenre,String chosenISBN,    
    String chosenTitle,String chosenPublisher,String chosenAuthor,String chosenRating){
        
         //Start bg thread here
 
        
        
        boolean goodGenre = false;
        boolean goodRating = false;
        

        if(chosenGenre.matches("Crime")){
           
            goodGenre = true;
        } else if(chosenGenre.matches("Mystery")) {
            
            goodGenre = true;
        } else if(chosenGenre.matches("Romance")) {
            
            goodGenre = true;
        } else if(chosenGenre.matches("Drama")) {
            
            goodGenre = true;
        } else if(chosenGenre.matches("Memoir")) {
           
            goodGenre = true;
        } else if(chosenGenre.matches("Fantasy")) {
           
            goodGenre = true;
        } else if(chosenGenre.matches("Learning")) {
           
            goodGenre = true;           
        } else {
            throw new Error("You didn't enter a viable genre");
            
        }
        if(Book.isValidIsbn(chosenISBN)){
            
        } else{
            throw new Error("bad isbn");
        }
        if(chosenRating.matches("1") || chosenRating.matches("2")
                || chosenRating.matches("3")
                || chosenRating.matches("4")
                || chosenRating.matches("5")){
           goodRating = true;
        }  else{
            throw new Error("bad rating. must be 1-5");
        }
        
        String[] elements = chosenAuthor.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);
        ArrayList<String> authorsList = new ArrayList<String>(fixedLenghtList);
     
        
 
        String addBookQuery = "";

        addBookQuery += "Insert into Book values('" + chosenISBN +"','" +
        chosenGenre+"'" + ",'"+chosenTitle+"',"+"'"+chosenPublisher+"','" + chosenRating +"','" +
                model.getUsername() +"')";
        System.out.println(addBookQuery);
       
       if(!authorsList.isEmpty()){
            try{
  
                model.executeAddBook(addBookQuery,authorsList,chosenISBN);
                
                Platform.runLater(new Runnable() 
                {
                        @Override
                        public void run() 
                        {
                      

                        view.bigTableBookScene();
                        
                     }
                });
                
            }catch(SQLException sqla){
                System.out.println(sqla.getMessage());
            } catch(Error erra){
                System.out.println(erra.getMessage());
            } catch (InterruptedException edo){
                edo.printStackTrace();
            } 
        
       }         
        
        
    }
   
    /**
     *
     * @param chosenGenre
     * @param chosenISBN
     * @param chosenTitle
     * @param chosenPublisher
     * @param chosenAuthor
     * @param chosenRating
     */
    public void handleAddBook(String chosenGenre,String chosenISBN,  
        String chosenTitle,String chosenPublisher,String chosenAuthor,String chosenRating){
      
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                runTask3(chosenGenre,chosenISBN,chosenTitle,chosenPublisher,chosenAuthor,chosenRating);
            }
        };
 
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();   


     
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     * @param datext
     */
    public void runTask4(int chosenBookNr,String chosenBookISBN,
            String chosenTitle,String datext){
        
        
 
        boolean alreadyReviewed = false;
        for(int i = 0; i<model.getBooks().size();i++){
            if(model.getBooks().get(i).getIsbn().matches(chosenBookISBN)){
                System.out.println(model.getBooks().get(i).getTheReviews());
          
              
                for(Review aReview : model.getBooks().get(i).getTheReviews()){
                    if(aReview.getReviewCreatorID().matches(model.getUserID())){
                       alreadyReviewed = true;
                    }
                } 
            }   
        }

        if(alreadyReviewed == false){
            
            System.out.println(model.getUsername() + " can review "
            + chosenBookISBN);
            
            String addReviewQuery = "";
            
          
            addReviewQuery += "insert into review(isbn,uid,review_text,reviewcreator) values";
            addReviewQuery += "('" + chosenBookISBN + "'," + model.getUserID() +
            ",'" + datext + "','" + model.getUsername() + "')";
            
            
            try{
                model.executeAddReview(addReviewQuery);
                
                Platform.runLater(new Runnable() 
                {
                    @Override
                    public void run() 
                    {
                      
                        view.bigTableReviewScene(chosenBookNr,chosenBookISBN,chosenTitle);  
                        
                    }
                });
                
                
            }catch(SQLException sqla){
                System.out.println(sqla.getMessage());
            } catch(Error erra){
                System.out.println(erra.getMessage());
            } catch (InterruptedException edo){
                edo.printStackTrace();
            }
            
            
        } else{
            System.out.println(model.getUsername() + " has already reviewed "
            + chosenBookISBN);
        }
           
  
        
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     * @param datext
     */
    public void handleAddReview(int chosenBookNr,String chosenBookISBN,
            String chosenTitle,String datext) {
        

        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                runTask4(chosenBookNr,chosenBookISBN,chosenTitle,datext);
            }
        };
 
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();   

        
  
    }
    
    /**
     *
     * @param chosenBookISBN
     */
    public void runTask5(String chosenBookISBN){
        
        String deleteReviewQuery = "";
        deleteReviewQuery += "delete from review where isbn = '" + chosenBookISBN
        + "' and uid = " + model.getUserID();
        
        try{
            model.executeDeleteReview(deleteReviewQuery);
            
          /*  Platform.runLater(new Runnable() 
            {
                @Override
                public void run() 
                { 
                        
                }
            });*/
            
        }catch(SQLException sqlad){
            System.out.println(sqlad.getMessage());
        } catch(Error erra){
            System.out.println(erra.getMessage());
        } catch (InterruptedException edo){
                edo.printStackTrace();
        }
        
    }
    
    /**
     *
     * @param chosenBookISBN
     */
    public void handleDeleteReview(String chosenBookISBN) {

        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                runTask5(chosenBookISBN);
            }
        };
 
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();   
        
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     * @param daAuthor
     */
    public void runTask6(int chosenBookNr, String chosenBookISBN, String chosenTitle, String daAuthor){
        
        String authorID = null;
        boolean authorAlreadyExists = false;
        boolean authorAlreadyExistsInLibrary = false;
        for(int i = 0; i<model.getBooks().size();i++){
            for(Author anAuthor : model.getBooks().get(i).getTheAuthors()){
                  if(anAuthor.getName().matches(daAuthor)){
                      
                      authorAlreadyExistsInLibrary = true;
                      for(int j = 0; j < model.getBooks().get(i).getTheAuthors().size();j++){
                          if(model.getBooks().get(i).getTheAuthors().get(j).getName().matches(daAuthor)){
                             authorID = model.getBooks().get(i).getTheAuthors().get(j).getAuthorID();
                          }
                      }
                      
                  }
              }
            
            if(model.getBooks().get(i).getIsbn().matches(chosenBookISBN)){
                System.out.println(model.getBooks().get(i).getTheAuthors());
            
              
              for(Author anAuthor : model.getBooks().get(i).getTheAuthors()){
                  if(anAuthor.getName().matches(daAuthor)){
                      authorAlreadyExists = true;
                      authorAlreadyExistsInLibrary = true;
                  }
              }
              
                
            }   
        }

        //System.out.println(addReviewQuery);
        if(authorAlreadyExists == false){
            

            System.out.println(daAuthor + " is a new author in "
            + chosenBookISBN);
            
            //need to also check all books incase you need to make a new author
            // or just make an authorof query without making a new author
            
            if(authorAlreadyExistsInLibrary == false){
                //Make a new author and assign an authorof
                //INSERT INTO Author(authorname,authorcreator) VALUES('PeterOhman','jeffrey');

                //INSERT INTO Authorof(isbn) VALUES('9789144131757'); 
                
                String addNewAuthorQuery = "";
                
                //INSERT INTO Author(authorname,authorcreator) VALUES('ChristerAllgulander','fegelein');
                addNewAuthorQuery += "insert into author(authorname,authorcreator) values('" +
                daAuthor +"','" + model.getUsername() +"')";
                
                String addNewAuthorOfQuery = "";
                
                //INSERT INTO Authorof(isbn) VALUES('9789144131757')
                addNewAuthorOfQuery += "insert into authorof(isbn) values('" +
                chosenBookISBN + "')";      
                
                // now execute
                
                try{
                    model.executeAddAuthorAndAuthorOfQuery(addNewAuthorQuery,addNewAuthorOfQuery);

                  /*  Platform.runLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        { 
                        
                        }
                    });   */                 
                    
                    
                }catch(SQLException sqla){
                    System.out.println(sqla.getMessage());
                } catch(Error erra){
                    System.out.println(erra.getMessage());
                }  catch (InterruptedException edo){
                    edo.printStackTrace();
                } 
                
                
                
            }else{
                //just make an authorof
                //INSERT INTO Authorof VALUES('9780261102736',5);
                String addOldAuthorOfQuery = "";
                
                addOldAuthorOfQuery += "insert into authorof values('" +
                chosenBookISBN + "'," + authorID + ")";
                
                //now execute
                try{
                    model.executeAddOnlyAuthorOfQuery(addOldAuthorOfQuery);
                    
                  /*  Platform.runLater(new Runnable() 
                    {
                        @Override
                        public void run() 
                        { 
                        
                        }
                    });  */                     
                    
                }catch(SQLException sqla){
                    System.out.println(sqla.getMessage());
                } catch(Error erra){
                    System.out.println(erra.getMessage());
                } catch (InterruptedException edo){
                    edo.printStackTrace();
                }
            }
            

            
        } else{
            System.out.println(daAuthor + " already exists in "
            + chosenBookISBN);
        }        
        
        
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     * @param daAuthor
     */
    public void handleAddAuthor(int chosenBookNr, String chosenBookISBN, String chosenTitle, String daAuthor) {
       
        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                runTask6(chosenBookNr,chosenBookISBN,chosenTitle,daAuthor);
            }
        };
 
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();           

        
    }
    
    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     * @param daID
     */
    public void runTask7(int chosenBookNr, String chosenBookISBN, String chosenTitle, String daID){
        
        String deleteAuthorQuery = "";
        deleteAuthorQuery += "delete from author where authorid = " + daID;
        
        try{
            model.executeDeleteAuthor(deleteAuthorQuery,chosenBookISBN,daID);
            
           /* Platform.runLater(new Runnable() 
            {
                @Override
                public void run() 
                { 
                        
                }
            });*/
            
        }catch(SQLException sqlad){
            System.out.println(sqlad.getMessage());
        } catch(Error erra){
            System.out.println(erra.getMessage());
        } catch (InterruptedException edo){
                    edo.printStackTrace();
        }         
        
    }

    /**
     *
     * @param chosenBookNr
     * @param chosenBookISBN
     * @param chosenTitle
     * @param daID
     */
    public void handleDeleteAuthor(int chosenBookNr, String chosenBookISBN, String chosenTitle, String daID) {

        Runnable task = new Runnable()
        {
            @Override
            public void run()
            {
                runTask7(chosenBookNr,chosenBookISBN,chosenTitle,daID);
            }
        };
 
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();  

    }
    

}