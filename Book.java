class Book{
    public const static final int ISBN_ = 0;
    public const static final int TITLE_ = 1;
    public const static final int SUBJECT_ = 2;
    public const static final int PAGE_COUNT_ = 3;
    public const static final int AUTHOR_ = 4;
    public const static final int DUE_DATE_ = 5;

    private String author;
    private LocalDate dueDate;
    private String isbn;
    private int pageCount;
    private String subject;
    private String title;

    public Book(String author, String title, String subject, int pageCount, String isbn, LocalDate dueDate){
        this.author = author;
        this.title = title;
        this.subject = subject;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.dueDate = dueDate;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Book book = (Book) object;
        return ISBN_ == book.ISBN_ && TITLE_ == book.TITLE_ && SUBJECT_ == book.SUBJECT_ && PAGE_COUNT_ == book.PAGE_COUNT_ && AUTHOR_ == book.AUTHOR_ && DUE_DATE_ == book.DUE_DATE_;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), ISBN_, TITLE_, SUBJECT_, PAGE_COUNT_, AUTHOR_, DUE_DATE_);
    }

    public String toString(){
        return title + " by " + author + " ISBN: " + isbn;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}