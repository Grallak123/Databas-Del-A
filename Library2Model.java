/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/** Library2Model Class
 *
 * @author frith
 */
public class Library2Model implements BooksDbInterface {

    private Connection con;
    
    private String user;
    private String pwd;
    private String database = "library2";
    private String userID;
    private String userRank;
    
    private ArrayList<Book> booklist;
    private Book book;
    private String server = "jdbc:mysql://localhost:3306/" + database +
        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    /**
     * Construct a Library2Model 
     */
    public Library2Model() {
        Connection con = null;
        booklist = new ArrayList<Book>();
       
    }
    
    /**
     * accessor method that returns Library2Model.con
     * @return con Connection
     */
    public Connection getCon(){
        return this.con;
    }
    
    /**
     * accessor method that returns Library2Model.user
     * @return user String
     */
    public String getUsername(){
        return this.user;
    }
    
    /**
     * accessor method that returns Library2Model.pwd
     * @return pwd
     */
    public String getPassword(){
        return this.pwd;
    }
    
    /**
     * accessor method that returns Library2Model.database
     * @return database String
     */
    public String getDatabase(){
        return this.database;
    }
    
    /**
     * accessor method that returns Library2Model.server
     * @return server String
     */
    public String getServer(){
        return this.server;
    }
    
    /**
     * accessor method that returns Library2Model.booklist
     * @return booklist ArrayList Book
     */
    public ArrayList<Book> getBooks(){
        
        return this.booklist;
    }
    
    /**
     * accessor method that returns Library2Model.userID
     * @return userID String
     */
    public String getUserID(){
        return this.userID;
    }
    
    /**
     * accessor method that returns Library2Model.userRank
     * @return userRank String
     */
    public String getUserRank(){
        return this.userRank;
    }
    
    /**
     * mutator method that sets Library2Model.userID
     * @param uID String
     */
    public void setUserID(String uID){
        this.userID = uID;
    }   
    
    /**
     * mutator method that sets Library2Model.userRank
     * @param uRank String
     */
    public void setUserRank(String uRank){
        this.userRank = uRank;
    }   
    
    /**
     * mutator method that sets Library2Model.user
     * @param uName String
     */
    public void setUsername(String uName){
        this.user = uName;
    }
    
    /**
     * mutator method that sets Library2Model.pwd
     * @param pWord String
     */
    public void setPassword(String pWord){
        this.pwd = pWord;
    }    
    
    /**
     * mutator method that sets Library2Model.booklist
     * @param inputBookList String
     */
    public void setBookList(ArrayList<Book> inputBookList){
        this.booklist = inputBookList;
    }
    
    /**
     * a method that checks all books just in case
     */
    public void testBooks(){
        
        
        for(Book book : booklist){
            System.out.println(book);
            
        }
    }
    
     /**
     *Will attempt to log in an existing user into the database and go to
     * the main screen
     * @param username String
     * @param password String
     * 
     * @throws java.lang.Exception
     */
    @Override
    public void tryToConnect(String username, String password) throws Exception{

        this.user = username;
        this.pwd = password;
        System.out.println(user + ", *********");
        this.database = "library2"; // the name of the specific database 
        this.server
                = "jdbc:mysql://localhost:3306/" + database
                + "?UseClientEnc=UTF8";
        
        server = "jdbc:mysql://localhost:3306/" + database +
        "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Connection con = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pwd);
            System.out.println("Connected!");
            // select uid,ranking from usersview where username = 'Stardust';
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select uid,ranking from usersview"
                    + " where username = '" +this.user + "'");
            
             if(rs.next()){
                        this.userID = rs.getString(1);
                        this.userRank = rs.getString(2);
             }
             System.out.println("UserName: " + user + " Password: " + pwd);
             System.out.println("UserID: " + userID + " UserRank: " + userRank);
            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
            }
        }
    }
    
    
    /**
     * makes sure that the connection always closes on window close
     * 
     * 
     */
    @Override
    public void tryToCloseConnection(){
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection Terminanted!");
                }
            } catch (SQLException e) {
            }
    }

    /**
     * Searches and updates the main UI depending on what criteria was entered
     * @param query String
     * @return temporaryBookList ArrayList Book
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public ArrayList<Book> executeSearchAndUpdateQuery(String query) throws SQLException, InterruptedException {
        
         ArrayList<Book> temporaryBookList = new ArrayList<Book>();
         try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            boolean bookFound = false;
            ArrayList<String> tmpSeenISBNList = new ArrayList<String>();
            Statement stmt = null;
         
            try{
                stmt = con.createStatement();
              
                
                // Execute the SQL statement
                
                ResultSet rs = stmt.executeQuery(query);
                

                // Get the attribute names
                ResultSetMetaData metaData = rs.getMetaData();
                int ccount = metaData.getColumnCount();
                
              
                for (int c = 1; c <= ccount; c++) {
                    System.out.print(metaData.getColumnName(c) + "\t");
                    
                }
                System.out.println();
                
                
                this.booklist.clear();
                
                
                int bookCounter = 0;
                boolean secondLife = true;
                ArrayList<Author> tmpAuthorList = new ArrayList<Author>();
               
                // Get the attribute values
                while (rs.next()) {
                    // NB! This is an example, -not- the preferred way to retrieve data.
                    // You should use methods that return a specific data type, like
                    // rs.getInt(), rs.getString() or such.
                    // It's also advisable to store each tuple (row) in an object of
                    // custom type (e.g. Employee).
                    ArrayList<String> tmpBookList = new ArrayList<String>();
                    
                    for (int c = 1; c <= ccount; c++) {
                        tmpBookList.add(rs.getString(c));
                        
                        
                        System.out.print(rs.getObject(c) + "\t");
                       
                    }
                        
                    Book.Genre bookGenre = Book.Genre.valueOf(tmpBookList.get(1));
                    
               
                    
                    try{
                        if(!tmpSeenISBNList.contains(tmpBookList.get(0))){
                            //System.out.println("good");
                            tmpSeenISBNList.add(tmpBookList.get(0));
                            tmpAuthorList.clear();
                            System.out.println("look here ree" + rs.getString(7)+rs.getString(8)+rs.getString(9));
                            tmpAuthorList.add(new Author(rs.getString(7),rs.getString(8),rs.getString(9)));
                            book = new Book(tmpBookList.get(0),bookGenre,tmpBookList.get(2),
                            tmpBookList.get(3),tmpBookList.get(4),tmpAuthorList,tmpBookList.get(5));
                            temporaryBookList.add(book);
                            bookCounter++;
                            bookFound = true;
                            if(secondLife){
                                bookCounter--;
                                secondLife = false;
                            }
                            
                       
                        }
                        else{
                       
                           
                           temporaryBookList.get(bookCounter).getTheAuthors().add
                            (new Author(rs.getString(7),rs.getString(8),rs.getString(9)));
                        
                        }

                        
                        if(book == null){
                            throw new Exception("Book was dead");
                        }
                        
                        book = null;
                        
                    }catch(IllegalArgumentException ile){
                        System.out.println(ile.getMessage());
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }

                    System.out.println();
                }   
                
               
                
            }finally{

                
               
               stmt.close(); 
            } 
            
            //do the all author check call here
            if(bookFound){
                String authorCheckQuery = createAuthorCheckQuery(tmpSeenISBNList);
                temporaryBookList = executeAuthorCheckQuery(authorCheckQuery,temporaryBookList);
            }
            
            if(bookFound){
                temporaryBookList = addAllReview(temporaryBookList);
            }
            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                    
                }
            } catch (SQLException e) {
                throw e;
            }
        }
         return temporaryBookList;
    } 
  
    /**
     * Creates authorCheckQuery that is used in executeAuthorCheckQuery to
     * ensure all authors of a book are included even if none of the criteria
     * in for example name matched with them Since they are Co-authors of a
     * book it only makes sense that they are incldued as well in the final
     * result
     * @param usedISBN ArrayList String
     * @return authorCheckQuery String
     * @throws InterruptedException
     */
    @Override
    public String createAuthorCheckQuery(ArrayList<String> usedISBN) throws InterruptedException{
        
        String authorCheckQuery = "";
        System.out.println(usedISBN);
       
        
        authorCheckQuery += "select authorof.*,authorname,authorcreator from authorof"
                + " join author on authorof.authorID = author.authorID"
                + " where";
   
        for(String checkedISBN : usedISBN){
            authorCheckQuery += " authorof.isbn = '" + checkedISBN + "' or";
        }
        authorCheckQuery += " authorof.isbn = '9999999999999'  order by ISBN DESC";
        
       
        System.out.println(authorCheckQuery);
        
        
        return authorCheckQuery;
    }
    
    /**
     * exexcutes authorCheckQuery and updates the book list that is returned
     * back to SearchAndUpdate
     * @param authorCheckQuery
     * @param temporaryBookList
     * @return temporaryBookList ArrayList Book
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public ArrayList<Book> executeAuthorCheckQuery(String authorCheckQuery,ArrayList<Book> temporaryBookList) throws SQLException,
            InterruptedException{
        
            Statement stmt = null;
            //Statement stmt2 = null; 
            try{
                stmt = con.createStatement();
                //stmt2 = con.createStatement();
                
                // Execute the SQL statement
                
                ResultSet rs = stmt.executeQuery(authorCheckQuery);
                

                // Get the attribute names
                ResultSetMetaData metaData = rs.getMetaData();
                int ccount = metaData.getColumnCount();
                
              
                for (int c = 1; c <= ccount; c++) {
                    System.out.print(metaData.getColumnName(c) + "\t");
                    
                }
                System.out.println();
                
                int safeRow = 0;
     
                String tempISBN = null;
                boolean tryonce = false;
                while (rs.next()) {
                    
                    if(!tryonce){
                        tempISBN = rs.getString(1);
                        System.out.println(tempISBN);
                        tryonce = true;
                    }
                    
                    
                    for (int c = 1; c <= ccount; c++) {
                        
                       System.out.print(rs.getObject(c) + "\t");
                       
                    }
  
                    
                    try{
                       
        
                       System.out.println(rs.getString(1));
                    
                       if(temporaryBookList.get(safeRow).getIsbn().equals(rs.getString(1))){      
                           
                           
                               
                            if(!temporaryBookList.get(safeRow).getTheAuthors().contains(new Author(
                                    rs.getString(3),rs.getString(4),
                            rs.getString(2)))){
                                temporaryBookList.get(safeRow).getTheAuthors().add(
                                new Author(rs.getString(3)
                                        ,rs.getString(4),rs.getString(2)));
                            }

                       }else{
                        
                            safeRow++;
                            if(!temporaryBookList.get(safeRow).getTheAuthors().contains(new Author(
                                    rs.getString(3),rs.getString(4),
                            rs.getString(2)))){
                                temporaryBookList.get(safeRow).getTheAuthors().add(
                                new Author(rs.getString(3),rs.getString(4),rs.getString(2)));
                            }
                            
                       }

                  
                        
                        if(book == null){
                            throw new Exception("Book was reee");
                        }
                        
                        book = null;
                        
                    }catch(IllegalArgumentException ile){
                        System.out.println(ile.getMessage());
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                        
                    }

                    System.out.println();
                }   
                
               
                
            }finally{

               stmt.close(); 
            }        
        
            return temporaryBookList;
        
    }
    
    /**
     * final part of SearchAndUpdate that makes sure all reviews are added
     * @param temporaryBookList ArrayList Book
     * @return temporaryBookList ArrayList Book
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public ArrayList<Book> addAllReview(ArrayList<Book> temporaryBookList) throws SQLException,
            InterruptedException{
     
        //Select * from review;
        
        Statement stmt = null;
        
     
        try{
            
            stmt = con.createStatement();
            ResultSet russia = stmt.executeQuery("select * from review");
            while (russia.next()) {
                for(Book laBook : temporaryBookList){
                    if(laBook.getIsbn().matches(russia.getNString(1))){
                        System.out.println(laBook.getIsbn());
                        for(int i = 0; i<temporaryBookList.size();i++){
                            if(temporaryBookList.get(i).getIsbn().matches(russia.getString(1))){
                                temporaryBookList.get(i).getTheReviews().add(new Review(russia.getString(2),
                       russia.getString(3),russia.getString(4),russia.getString(5)));
                            }
                        }              
                       
                    }
                }
            }
            
            
        }finally{
            
            stmt.close();
            
        }
        
        
        
        return temporaryBookList;
    }

    /**
     * method that inserts a new book based on the input values
     * @param query String
     * @param authorsList ArrayList String
     * @param chosenISBN String
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public void executeAddBook(String query,ArrayList<String> authorsList,
            String chosenISBN) throws SQLException, InterruptedException  {
        
        int listSize = authorsList.size();
         
        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            
            Statement stmt1 = null;
            
            try{
                con.setAutoCommit(false);
                stmt1 = con.createStatement();
               
                
                // Execute the SQL statement
                
                stmt1.executeUpdate(query);
              
                
               System.out.println("hello" + listSize);
               for(String atts : authorsList){
                   System.out.println(atts);
               }
               for(int i = 0; i<listSize;i++){
                   //1. check in the database for any author with that name
                   // and if so use that id when creating an authorof
                    Statement tmpstmt = con.createStatement();
                    ResultSet ras = tmpstmt.executeQuery("select authorid from author where "
                          + "authorname = '" +authorsList.get(i) + "'");
                    int tmpInt = 0;
                    System.out.println("select authorid from author where "
                          + "authorname = '" +authorsList.get(i) + "'");
                    
                    if(ras.next()){
                        tmpInt = ras.getInt(1);
                        System.out.println("what int was " + tmpInt);
                        //2. if not zero then an author already exists
                        // use his id to create an author of and don't create
                        // any new authors
                        
                            Statement authorOfStmt = con.createStatement();
                            String authorOfQuery = "";
                            authorOfQuery += "Insert into Authorof values"
                            + "('"+chosenISBN+"',"+Integer.toString(tmpInt)+")";
                            System.out.println(authorOfQuery);
                            authorOfStmt.executeUpdate(authorOfQuery);
                            authorOfStmt.close();
                        
                        
                    }else{
                            Statement authorstmt2 = con.createStatement();
                            String authorQuery = "";
                            authorQuery += "Insert into Author(authorname,creator) values"
                            + "('"+authorsList.get(i)+"','" + this.user +"')";
                            System.out.println(authorQuery);
                            authorstmt2.executeUpdate(authorQuery);
                            authorstmt2.close();
                            
                            
                            Statement authorOfStmt2 = con.createStatement();
                            String authorOfQuery2 = "";
                            authorOfQuery2 += "Insert into Authorof(isbn) values"
                            + "('"+chosenISBN+"')";
                            System.out.println(authorOfQuery2);
                            authorOfStmt2.executeUpdate(authorOfQuery2);
                            authorOfStmt2.close();
                    }
                    System.out.println("authorID:" + tmpInt);
                    tmpstmt.close();
                   
               }
                

                        
            
            } catch(Exception e){
                System.out.println(e.getMessage());
                if(con != null) con.rollback();
                        throw e;
      

            }finally{

              if(stmt1 != null) stmt1.close();
               con.setAutoCommit(true);
            } 

            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    /**
     * Deletes a book from the database based on isbn
     * @param deleteQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public void executeDeleteBook(String deleteQuery) throws SQLException, InterruptedException{
       
        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            
            Statement stmt1 = null;
          
            try{
                con.setAutoCommit(false);
                stmt1 = con.createStatement();
             
                
                stmt1.executeUpdate(deleteQuery);
   
        
            
            } catch(Exception e){
                System.out.println(e.getMessage());
                if(con != null) con.rollback();
                        throw e;
      

            }finally{

              if(stmt1 != null) stmt1.close();
               con.setAutoCommit(true);
            } 

            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }
        
    }

    /**
     * takes a text and creates a review based on current username&
     * userid and current time
     * @param addReviewQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public void executeAddReview(String addReviewQuery) throws SQLException,InterruptedException {

        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            
            Statement stmt1 = null;
          
            try{
                con.setAutoCommit(false);
                stmt1 = con.createStatement();
            
                
                // Execute the SQL statement
                
                stmt1.executeUpdate(addReviewQuery);

                        
            
            } catch(Exception e){
                System.out.println(e.getMessage());
                if(con != null) con.rollback();
                        throw e;
      

            }finally{

              if(stmt1 != null) stmt1.close();
               con.setAutoCommit(true);
            } 

            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }        

    }

    /**
     * deletes review if the reviewer's id matches the current user's id
     * @param deleteReviewQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public void executeDeleteReview(String deleteReviewQuery) throws SQLException,InterruptedException  {

        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            
            Statement stmt1 = null;

            try{
                con.setAutoCommit(false);
                stmt1 = con.createStatement();
                
                // Execute the SQL statement
                
                stmt1.executeUpdate(deleteReviewQuery);

            
            } catch(Exception e){
                System.out.println(e.getMessage());
                if(con != null) con.rollback();
                        throw e;
               
            
      

            }finally{

              if(stmt1 != null) stmt1.close();
               con.setAutoCommit(true);
            } 

            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }        
        
        
    }

    /**
     * this method creates a new author and and authorof if the author does
     * not exist in the database already
     * @param addNewAuthorQuery String
     * @param addNewAuthorOfQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public void executeAddAuthorAndAuthorOfQuery(String addNewAuthorQuery, String addNewAuthorOfQuery)
    throws SQLException,InterruptedException{
        
        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            
            Statement stmt1 = null;
            Statement stmt2 = null;
            //Statement stmt2 = null; 
            try{
                con.setAutoCommit(false);
                stmt1 = con.createStatement();
                stmt2 = con.createStatement();
                
                // Execute the SQL statement
                
                stmt1.executeUpdate(addNewAuthorQuery);
                stmt2.executeUpdate(addNewAuthorOfQuery);

            } catch(Exception e){
                System.out.println(e.getMessage());
                if(con != null) con.rollback();
                        throw e;
      

            }finally{

              if(stmt1 != null) stmt1.close();
              if(stmt2 != null) stmt2.close();
               con.setAutoCommit(true);
            } 

            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }

        
    }

     /**
     * this method is used if the author already exists and simply creates 
     * a new authorof relation
     * @param addOldAuthorOfQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public void executeAddOnlyAuthorOfQuery(String addOldAuthorOfQuery) throws SQLException,InterruptedException {

        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            
            Statement stmt1 = null;
            //Statement stmt2 = null; 
            try{
                con.setAutoCommit(false);
                stmt1 = con.createStatement();
                //stmt2 = con.createStatement();
                
                // Execute the SQL statement
                
                stmt1.executeUpdate(addOldAuthorOfQuery);

            
            } catch(Exception e){
                System.out.println(e.getMessage());
                if(con != null) con.rollback();
                        throw e;
               
            
      

            }finally{

              if(stmt1 != null) stmt1.close();
               con.setAutoCommit(true);
            } 

            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }          
        

    }

    /**
     * deletes the author if only one authorof or just the authorof to that isbn
     * if there are more examples.
     * @param deleteAuthorQuery String
     * @param chosenBookISBN String
     * @param daID String
     * @throws SQLException
     * @throws InterruptedException
     */
    @Override
    public void executeDeleteAuthor(String deleteAuthorQuery, String chosenBookISBN,String daID) throws SQLException,InterruptedException {

        try {
            
            //Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, this.user, this.pwd);
            System.out.println("Connected!");
            Statement stmt1 = null;
            Statement stmt2 = null;
            int nrOfBooks = 0;
            //select author.*, count(authorof.authorid) AS "books with author"
            //from author
            //inner join authorof on authorof.authorid = author.authorid
            //where author.authorid = 5;
            String hotQuery = "";
            hotQuery += "select author.*, count(authorof.authorid) as bookswritten "
                    + "from author inner join authorof on authorof.authorid ="
                    + " author.authorid where author.authorid = " + daID;
            
            try{
                con.setAutoCommit(false);
                stmt1 = con.createStatement();
                stmt2 = con.createStatement();
               
                ResultSet rese = stmt1.executeQuery(hotQuery);
                
                while(rese.next()){
                    System.out.println(rese.getInt(4));
                    nrOfBooks = rese.getInt(4);
                }
                
                
                if(nrOfBooks == 1){
                    stmt2.executeUpdate(deleteAuthorQuery);
                } else{
                    stmt2.executeUpdate("delete from authorof where authorid"
                            + " = " + daID + " and isbn = '" + chosenBookISBN+ "'");
                }
               

            } catch(Exception e){
                System.out.println(e.getMessage());
                if(con != null) con.rollback();
                        throw e;
               
            
      

            }finally{
                    
              if(stmt1 != null) stmt1.close();
              if(stmt2 != null) stmt2.close();
               con.setAutoCommit(true);
            } 

            
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {
                throw e;
            }
        }   
        

    }
      
    
}
