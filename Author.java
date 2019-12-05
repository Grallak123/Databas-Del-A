/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author frith
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  The Class of Author that handles all author related data
 * 
 * @author frith
 *
*  */
public class Author{
    
    private String name;
    private String authorCreator;
    private String authorID;
    
    /**The Constructor of Author which takes name of the author, authorCreator and authorID
     * as inputs
     * @param name String 
     * @param authorCreator String
     * @param authorID String
     */
    public Author(String name,String authorCreator,String authorID){
        this.name = name;
        this.authorCreator = authorCreator;
        this.authorID = authorID;
    }
    
    /**
     *mutator method that sets author.name
     * @param newName String
     */
    public void setName(String newName){
        this.name = newName;
    }
    
    /**
     *mutator method that sets author.authorCreator
     * @param newAuthorCreator String
     */
    public void setAuthorCreator(String newAuthorCreator){
        this.authorCreator = newAuthorCreator;
    }  
    
    /**
     * mutator method that sets author.authorID
     * @param newAuthorID String
     */
    public void setAuthorID(String newAuthorID){
        this.authorID = newAuthorID;
    }
    
    
    /**Accessor method that returns the name of the author
     * @return name Name the name of this author object.
     */
    public String getName(){
         
        return this.name;
    }

    /**
     *Accessor method that returns the authorCreator of the author
     * @return authorCreator String
     */
    public String getAuthorCreator(){ return this.authorCreator; }
    
    /**
     * Accessor method that returns the authorID of the author
     * @return authorID String
     */
    public String getAuthorID(){
         
        return this.authorID;
    }
    
     /**the Author Class's equals method
     * @param o Object
     * @return returns true if name and authorID matches the other object
     */    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author other = (Author) o;
        return name.equals(other.name) && authorID.equals(other.authorID);
    }

     /**the Author Class's equals method has code
     * 
     * @return returns the int of the hash code
     */    
    @Override
    public int hashCode() {
        return Objects.hash(name,authorID);
    }    
    
    
    /** toString method of the Author with name
     * @return info String
     */
    @Override
    public String toString(){
                 
        String info = "Author name: "+this.name +"\n  ";
        
    
        return info;
    }

 
    
}