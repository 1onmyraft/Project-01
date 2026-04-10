import Utilities.Code;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

public class Library {
    public static final int LENDING_LIMIT = 5;
    private HashMap<Book, Integer> books;
    private static int libraryCard;
    private String name;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;


    public Library(String s){
        name = s;
        shelves = new HashMap<>();
        books = new HashMap<>();
    }

    public Code addBook(Book b){
        return Code.SUCCESS;
    }

    private Code addBookToShelf(Book b, Shelf s){
        // unnecessary

        return Code.SUCCESS;
    }

    public Code addReader(Reader reader){
        if (readers.contains(reader))
        {
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }

        for (Reader r : readers)
            if (r.getCardNumber() == reader.getCardNumber())
            {
                System.out.println(r.getName() + " and "+reader.getName()+" have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }

        readers.add(reader);
        System.out.println(reader.getName() + " added to the library!");
        if (reader.getCardNumber() > libraryCard)
            libraryCard = reader.getCardNumber();

        return Code.SUCCESS;
    }

    public Code addShelf(Shelf s){
        return Code.SUCCESS;
    }

    public Code addShelf(String s){
        return Code.SUCCESS;
    }

    public Code checkOutBook(Reader reader, Book book) {

        if (!readers.contains(reader))
        {
            System.out.println(reader.getName() + " doesn't have an account here");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if (reader.getBookCount() > LENDING_LIMIT){
            System.out.println(reader.getName() + " has reached the lending limit, ("+LENDING_LIMIT+")");
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }

        if (!books.containsKey(book)){
            System.out.println("ERROR: could not find " + book.getTitle());
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        Shelf shelf = getShelf(book.getSubject());

        if (shelf == null)
        {
            System.out.println("no shelf for " + book.getSubject() + " books!");

        }


        return Code.SUCCESS;
    }

    private static LocalDate convertDate(String s, Code c){
        String[] date = s.split("-");

        if (date.length != 3)
        {
            System.out.println("ERROR: date conversion error, could not parse " + s);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.EPOCH;
        }

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);

        if (year <0 || month < 0 || day < 0)
        {
            System.out.println("Error converting date: Year " + year);
            System.out.println("Error converting date: Month " + month);
            System.out.println("Error converting date: Day " + day);
            System.out.println("Using default date (01-jan-1970)");
            return LocalDate.EPOCH;
        }


        return LocalDate.of(year, month, day);
    }

    private static int convertInt(String s, Code c){
        int parsed = -1;
        try {
             parsed = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Value which caused the error: " + s);
            System.out.println("Error message: " + c.getMessage());

            switch(c){
                case Code.DATE_CONVERSION_ERROR:
                    System.out.println("Error: Could not parse date component");
                    break;
                case Code.PAGE_COUNT_ERROR:
                    System.out.println("Error: could not parse page count");
                    break;
                case Code.BOOK_COUNT_ERROR:
                    System.out.println("Error: Could not read number of books");
                    break;
                default:
                    System.out.println("Error: Unknown conversion error");
            }

            return c.getCode();
        }

        return parsed;
    }

    private Code errorCode(int codeNumber) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeNumber) {
                return code;
            }
        }
        return Code.UNKNOWN_ERROR;
    }


    public Book getBookByISBN(String isbn){
        for (Book b : books.keySet())
            if (b.getISBN().equals(isbn))
                return b;

        System.out.println("ERROR: Could not find a book with isbn: " + isbn);
        return null;
    }

    public int getLibraryCardNumber(){
        return libraryCard+1;
    }

    public Reader getReaderByCard(int cardNumber){
        for (Reader r: readers)
            if (r.getCardNumber() == cardNumber)
                return r;

        System.out.println("Could not find a reader with card #" + cardNumber);
        return null;
    }

    public Shelf getShelf(String subject){
        return null;
    }

    public Shelf getShelf(Integer shelfNumber){
        return null;
    }

    public static Code ret(int count){
        if (count < 0) {
            Code[] iterable = Code.values();

            if (count >= 0 && count < iterable.length)
                return Code.values()[count];
            else
                return Code.UNKNOWN_ERROR;
        }
        return Code.SUCCESS;
    }

    public Code init(String filename) {

        File f = new File(filename);
        Scanner s;
        try {
             s = new Scanner(f);
        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        int book_count = convertInt(s.nextLine(), Code.SUCCESS);


        Code status = ret(book_count);
        if (status != Code.SUCCESS)
            return status;
        initBooks(book_count, s);
        listBooks();
        int shelf_count = convertInt(s.nextLine(), Code.SUCCESS);
        status = ret(shelf_count);
        if (status != Code.SUCCESS)
            return status;
        initShelves(shelf_count, s);
        listShelves();

        int reader_count = convertInt(s.nextLine(), Code.SUCCESS);
        status = ret(reader_count);
        if (status != Code.SUCCESS)
            return status;
        initReader(reader_count, s);
        listReaders();

        return Code.SUCCESS;
    }

    private Code initBooks(int bookCount, Scanner scan){
        if (bookCount < 1)
            return Code.LIBRARY_ERROR;

         String author = "";
         LocalDate dueDate = null;
         String isbn = "";
         int pageCount = 0;
         String subject = "";
         String title = "";

        for (int i =0; i < bookCount; i++){

            for (int j = 0; j <= 5; j++)
                switch(j){
                    case 4:
                        author = scan.next();
                        break;
                    case 5:
                        dueDate = convertDate(scan.next(), Code.SUCCESS);
                        if (dueDate == null)
                            return Code.DATE_CONVERSION_ERROR;
                        break;
                    case 0:
                        isbn = scan.next();
                        break;
                    case 3:
                        int c = convertInt(scan.next(), Code.SUCCESS);
                        if (c < 0)
                            return Code.PAGE_COUNT_ERROR;
                        pageCount = c;
                        break;
                    case 2:
                        subject = scan.next();
                        break;
                    case 1:
                        title = scan.next();
                        break;
                }
            Book book = new Book(isbn,title, subject,pageCount, author, dueDate);
            addBook(book);
        }

        return Code.SUCCESS;
    }

    private Code initReader(int readerCount, Scanner scan){
        if (readerCount < 1)
            return Code.READER_COUNT_ERROR;

        int cardNumber = 0;
        String name = "";
        String phone = "";

        for (int i =0; i < readerCount; i++){

            for (int j = 0; j <= 4; j++)
                switch(j){
                    case 0:
                        name = scan.next();
                        break;
                    case 1:
                        phone = scan.next();
                        break;
                    case 2:
                        scan.next();
                        break;
                    case 3:
                        int c = convertInt(scan.next(), Code.SUCCESS);
                        if (c < 0)
                            return Code.PAGE_COUNT_ERROR;
                        cardNumber = c;
                        break;

                }
            Reader reader = new Reader(cardNumber, name, phone);
            addReader(reader);
        }

        return Code.SUCCESS;
    }

    private Code initShelves(int shelfCount, Scanner scan){
        if (shelfCount < 1)
            return Code.LIBRARY_ERROR;

        String author = "";
        LocalDate dueDate = null;
        String isbn = "";
        int pageCount = 0;
        String subject = "";
        String title = "";

        for (int i =0; i < shelfCount; i++){

            for (int j = 0; j <= 5; j++)
                switch(j){
                    case 0:
                        author = scan.next();
                        break;
                    case 1:
                        dueDate = convertDate(scan.next(), Code.SUCCESS);
                        if (dueDate == null)
                            return Code.DATE_CONVERSION_ERROR;
                        break;
                    case 2:
                        isbn = scan.next();
                        break;
                    case 3:
                        int c = convertInt(scan.next(), Code.SUCCESS);
                        if (c < 0)
                            return Code.PAGE_COUNT_ERROR;
                        pageCount = c;
                        break;
                    case 4:
                        subject = scan.next();
                        break;
                    case 5:
                        title = scan.next();
                }
            Book book = new Book(isbn,title, subject,pageCount, author, dueDate);
            addBook(book);
        }

        return Code.SUCCESS;
    }

    public int listBooks(){
        return 0;
    }

    public int listReaders(){
        for (Reader r : readers)
            System.out.println(r.toString());

        return readers.size();
    }

    public int listReaders(boolean showBooks){
        if (showBooks)
            for (Reader r : readers) {
            System.out.println(r.getName() + "(#"+ r.getCardNumber() +")  has the following books:");
            System.out.println(r.getBooks().toString());
            }
        else
            for (Reader r : readers)
                System.out.println(r.toString());

        return readers.size();
    }

    public int listShelves(boolean b){
        return 0;
    }

    public int listShelves(){
        return 0;
    }

    public Code removeReader(Reader r){
        if (readers.contains(r)){
            if (r.getBookCount() > 0)
            {
                System.out.println(r.getName() + " must return all books!");
                return Code.READER_STILL_HAS_BOOKS_ERROR;
            }else
                readers.remove(r);
        }else{
            System.out.println(r.getName() + " is not part of this Library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }
        return Code.SUCCESS;
    }

    public Code returnBook(Reader r, Book b){
        return Code.SUCCESS;
    }

    public Code returnBook(Book b){
        return Code.SUCCESS;
    }






    public String getName() {
        return name;
    }
}
