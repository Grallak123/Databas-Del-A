/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/** Review Class that stores review data 
 *
 * @author frith
 */
public class Review {
    
    
    String reviewCreatorID;
    String entryDateTime;
    String reviewText;
    String reviewCreatorUName;
    
    /** Review class's Constructor
     *
     * @param reviewCreatorID String
     * @param inputDateTime String
     * @param reviewText String
     * @param reviewCreatorUName String
     */
    public Review(String reviewCreatorID,String inputDateTime,String reviewText,String reviewCreatorUName){
        this.reviewCreatorID = reviewCreatorID;
 
        this.entryDateTime = inputDateTime;
        this.reviewText = reviewText;
        this.reviewCreatorUName = reviewCreatorUName;
    }
    
    /**
     *accessor method that returns Review.reviewCreatorID
     * @return reviewCreatorID
     */
    public String getReviewCreatorID(){
        return this.reviewCreatorID;
    }
    
    /**
     *accessor method that returns Review.entryDateTime
     * @return entryDateTime String
     */
    public String getEntryDateTime(){
        return this.entryDateTime;
    }
    
    /**
     * accessor method that returns Review.reviewText
     * @return reviewText String
     */
    public String getReviewText(){
        return this.reviewText;
    }
    
    /**
     * accessor method that returns Review.reviewCreatorUName
     * @return reviewCreatorUName String
     */
    public String getReviewCreatorUName(){
        return this.reviewCreatorUName;
    }
    
    /**
     * mutator method that sets Review.reviewCreatorID
     * @param newReviewCreatorID String
     */
    public void setReviewCreatoriD(String newReviewCreatorID){
       this.reviewCreatorID = newReviewCreatorID;
    }
   
    /**
     * mutator method that sets Review.entryDateTime by taking the current time
     * and formating it.
     * 
     */
    public void setEntryDateTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.entryDateTime = myDateObj.format(myFormatObj);
    }
   
    /**
     * mutator method that sets Review.reviewText
     * @param newReviewText String
     */
    public void setReviewText(String newReviewText){
       this.reviewText = newReviewText;
    }
   
    /**
     * mutator method that sets Review.reviewCreatorUName
     * @param newReviewCreatorUName String
     */
    public void setReviewCreatorUName(String newReviewCreatorUName){
       this.reviewCreatorUName = newReviewCreatorUName;
    }
    
     /**the Review Class's equals method
     * @param o Object
     * @return returns true if reviewCreatorID matches the other object
     **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review other = (Review) o;
        return reviewCreatorID.equals(other.reviewCreatorID);
    }

    
     /**the Review Class's equals method has code
     * 
     * @return returns the int of the hash code
     */    
    @Override
    public int hashCode() {
        return Objects.hash(reviewCreatorUName);
    }
    
     /**the Review Class's toString method  
     * 
     * @return info String 
     */            
    @Override
    public String toString(){
                 
        String info = "Reviewer Name: " + this.reviewCreatorUName + "\n";
        
    
        return info;
    }
    

    
    
}
