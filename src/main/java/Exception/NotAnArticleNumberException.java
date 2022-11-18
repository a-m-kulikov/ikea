package Exception;

public class NotAnArticleNumberException extends Exception {
    public NotAnArticleNumberException(){
        super("Содержимое сообщения не является артикулом товара IKEA");
    }
}
