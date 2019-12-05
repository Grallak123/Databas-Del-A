package model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface declares methods for querying a Books database.
 * Different implementations of this interface handles the connection and
 * queries to a specific DBMS and database, for example a MySQL or a MongoDB
 * database.
 * 
 * @author frith
 */
public interface BooksDbInterface {
    
    /**
     *Will attempt to log in an existing user into the database and go to
     * the main screen
     * @param username String
     * @param password String
     * 
     * @throws java.lang.Exception
     */

    
    public void tryToConnect(String username, String password) throws Exception;
    
    /**
     * makes sure that the connection always closes on window close
     * 
     * 
     */
    public void tryToCloseConnection();
    
    /**
     * Searches and updates the main UI depending on what criteria was entered
     * @param query String
     * @return temporaryBookList ArrayList Book
     * @throws SQLException
     * @throws InterruptedException
     */
    public ArrayList<Book> executeSearchAndUpdateQuery(String query) throws SQLException, InterruptedException;
    
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
    public String createAuthorCheckQuery(ArrayList<String> usedISBN) throws InterruptedException;
    
    /**
     * exexcutes authorCheckQuery and updates the book list that is returned
     * back to SearchAndUpdate
     * @param authorCheckQuery
     * @param temporaryBookList
     * @return temporaryBookList ArrayList Book
     * @throws SQLException
     * @throws InterruptedException
     */
    public ArrayList<Book> executeAuthorCheckQuery(String authorCheckQuery,ArrayList<Book> temporaryBookList) throws SQLException,
            InterruptedException;
    
    /**
     * final part of SearchAndUpdate that makes sure all reviews are added
     * @param temporaryBookList ArrayList Book
     * @return temporaryBookList ArrayList Book
     * @throws SQLException
     * @throws InterruptedException
     */
    public ArrayList<Book> addAllReview(ArrayList<Book> temporaryBookList) throws SQLException,
            InterruptedException;
    
    /**
     * method that inserts a new book based on the input values
     * @param query String
     * @param authorsList ArrayList String
     * @param chosenISBN String
     * @throws SQLException
     * @throws InterruptedException
     */
    public void executeAddBook(String query,ArrayList<String> authorsList,
            String chosenISBN) throws SQLException, InterruptedException;
    
    /**
     * Deletes a book from the database based on isbn
     * @param deleteQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    public void executeDeleteBook(String deleteQuery) throws SQLException, InterruptedException;
    
    /**
     * takes a text and creates a review based on current username&
     * userid and current time
     * @param addReviewQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    public void executeAddReview(String addReviewQuery) throws SQLException, InterruptedException;
    
    /**
     * deletes review if the reviewer's id matches the current user's id
     * @param deleteReviewQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    public void executeDeleteReview(String deleteReviewQuery) throws SQLException, InterruptedException;
    
    /**
     * this method creates a new author and and authorof if the author does
     * not exist in the database already
     * @param addNewAuthorQuery String
     * @param addNewAuthorOfQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    public void executeAddAuthorAndAuthorOfQuery(String addNewAuthorQuery, String addNewAuthorOfQuery)
    throws SQLException,InterruptedException;
    
    /**
     * this method is used if the author already exists and simply creates 
     * a new authorof relation
     * @param addOldAuthorOfQuery String
     * @throws SQLException
     * @throws InterruptedException
     */
    public void executeAddOnlyAuthorOfQuery(String addOldAuthorOfQuery) throws SQLException,
            InterruptedException;
    
    /**
     * deletes the author if only one authorof or just the authorof to that isbn
     * if there are more examples.
     * @param deleteAuthorQuery String
     * @param chosenBookISBN String
     * @param daID String
     * @throws SQLException
     * @throws InterruptedException
     */
    public void executeDeleteAuthor(String deleteAuthorQuery, String chosenBookISBN,
            String daID) throws SQLException,
            InterruptedException;
    
    
    // TODO: Add abstract methods for all inserts, deletes and queries 
    // mentioned in the instructions for the assignement.
}