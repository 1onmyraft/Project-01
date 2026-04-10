import Utilities.Code;

import java.util.HashMap;
import java.util.Objects;

public class Shelf{
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 0;

    private HashMap<Book, Integer> books;
    private int shelfNumber;
    private String subject;

    public Shelf(){

    }

    public Shelf(int i, String s) {
        shelfNumber = i;
        subject = s;
    }

    public Code addBook(Book b){
        if (books.containsKey(b))
            books.put(b, books.get(b)+1);
        else if (b.getSubject().equals(this.subject))
            books.put(b, 1);
        else
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;

        System.out.println(b.toString() + " added to shelf " + this.subject);
        return Code.SUCCESS;
    }

    public int getBookCount(Book b){
        return books.get(b);
    }

    public HashMap<Book, Integer> getBooks(){
        return books;
    }

    public int getShelfNumber(){
        return shelfNumber;
    }

    public String getSubject(){
        return subject;
    }

    public String listBooks(){
        StringBuilder sb = new StringBuilder();
        sb.append(books.size()).append(" books on shelf: ").append(this.toString()).append("\n");
        for (Book b : books.keySet())
            sb.append(b.getTitle()).append(" by ").append(b.getAuthor()).append(" ISBN:").append(b.getISBN()).append(" ").append(books.get(b));

        return sb.toString();
    }
    public Code removeBook(Book b){
        if (books.containsKey(b)){
            if (books.get(b) == 0) {
                System.out.println("No copies of " + b.toString() + " remain on shelf " + subject);
                return Code.BOOK_NOT_IN_INVENTORY_ERROR;

            }else{
                System.out.println( b.toString() + " successfully removed from shelf " + subject);
                return Code.BOOK_NOT_IN_INVENTORY_ERROR;
            }
            }
        System.out.println(b.toString() + " is not on shelf " + subject);
        return Code.BOOK_NOT_IN_INVENTORY_ERROR;
    }

    public void setBooks(HashMap<Book, Integer> h){
        books = h;
    }

    public void setShelfNumber(int i ){
        shelfNumber = i;
    }

    public void setSubject(String s){
        subject = s;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelfNumber == shelf.shelfNumber && Objects.equals(books, shelf.books) && Objects.equals(subject, shelf.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books, shelfNumber, subject);
    }

    @Override
    public String toString() {
        return shelfNumber +
                " : " + subject;
    }
}