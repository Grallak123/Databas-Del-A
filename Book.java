/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author frith
 */

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;



/**
* Book class that is used to store all book related data
* @author frith
*
*/
public class Book {
    
    /**
    * enums for genres.
    * @author frith
    */
    public enum Genre {

       
        Crime,
        Mystery,
        Romance,
        Drama,
        Memoir,
        Fantasy,
        Learning}

    private String title;
    private String isbn; // check format
    private Genre genre;
    private String publisher;
    private String rating;
    private ArrayList<Author> theAuthors;

    private String bookCreator;
    private ArrayList<Review> theReviews;
  
    
    

    // A simplified regexp for isbn, 10 digit number, 
    // last digit may also be 'X' 
    /**
     * static final method for checking pattern
     */
    private static final Pattern ISBN_PATTERN = 
            Pattern.compile("^\\d{12}[\\d|X]$"); 
    
    /**
     * checks that the isbn is 13 digits with the last digit being able to take
     * char x
     * @param isbn String
     * @return boolean
     */
    public static boolean isValidIsbn(String isbn) {
        return ISBN_PATTERN.matcher(isbn).matches();
        
    }
    
    /**Book Constructor method
     *
     * @param isbn String
     * @param genre String
     * @param title String
     * @param publisher String
     * @param rating String
     * @param authorsInput ArrayList Author
     * @param inputBookCreator String
     */
    public Book(String isbn, Genre genre, String title, String publisher,String rating,
            ArrayList<Author> authorsInput,String inputBookCreator) {
        if(!isValidIsbn(isbn)) 
            throw new IllegalArgumentException("not a valid isbn");
        this.isbn = isbn;
        this.title = title;
        this.genre = genre;
        this.publisher = publisher;
        this.rating = rating;
        this.theAuthors = new ArrayList<Author>();
        
        for (Author authors : authorsInput) {
                this.theAuthors.add(authors);
                  
        } 
        
 
        
        this.bookCreator = inputBookCreator;
        this.theReviews = new ArrayList<Review>();
        
    }

    /**
     * mutator method that sets book.isbn
     * @param newISBN String
     */
    public void setIsbn(String newISBN){ 
        this.isbn = newISBN;
    }
    
    /**
     *mutator method that sets book.genre
     * @param newGenre Genre
     */
    public void setGenre(Genre newGenre){ 
        this.genre = newGenre;
    }
    
    /**
     *mutator method that sets book.title
     * @param newTitle String
     */
    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    
    /**
     *mutator method that sets book.publisher
     * @param newPublisher String
     */
    public void setPublisher(String newPublisher){ 
        this.publisher = newPublisher;
    }
    
    /**
     * mutator method that sets book.rating
     * @param newRating String
     */
    public void setRating(String newRating){ 
        this.rating = newRating;
    }
    
    /**
     *mutator method that sets book.bookCreator
     * @param newBookCreator String
     */
    public void setBookCreator(String newBookCreator){
        this.bookCreator = newBookCreator;
    }
    
    /**
     * accessor method that return book.isbn
     * @return isbn String
     */
    public String getIsbn()     { 
        return isbn; 
    }
    
    /**
     *accessor method that return book.title
     * @return title String
     */
    public String getTitle()    { 
        return title; 
    }
    
    /**
     * accessor method that return book.genre
     * @return genre Genre
     */
    public Genre getGenre()     { 
        return genre; 
    }
    
    /**
     *accessor method that return book.publisher
     * @return publisher String
     */
    public String getPublisher() { 
        return publisher; 
    
    }
    
    /**
     * accessor method that return book.rating
     * @return rating String
     */
    public String getRating() {
        return rating; 
    }
    
    /**
     *accessor method that return book.theAuthors
     * @return theAuthors Arraylist Author
     */
    public ArrayList<Author> getTheAuthors() { 
        return theAuthors;
    }
    
    /**
     *accessor method that return book.bookCreator
     * @return bookCreator String
     */
    public String getBookCreator(){ 
        return this.bookCreator; 
    }
    
    /**
     * accessor method that return book.theReviews
     * @return theReviews ArrayList Review
     */
    public ArrayList<Review> getTheReviews() {
        return theReviews;
    }

     /**the Book Class's equals method
     * @param o Object
     * @return returns true if isbn matches the other object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book other = (Book) o;
        return isbn.equals(other.isbn) ;
    }

     /**the Book Class's equals method has code
     * 
     * @return returns the int of the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }    
    
    
     /**the Book Class's toString method  
     * 
     * @return info String 
     */
    @Override
    public String toString() {
        return "| " + isbn + "  | " + genre +"  | " + title + "   |  " 
                + publisher + "   |" + "   |   \n Author/s: " + theAuthors +
                "   |   reviews   |" + theReviews + "   |  Rating: " + rating ;
    }
    
}