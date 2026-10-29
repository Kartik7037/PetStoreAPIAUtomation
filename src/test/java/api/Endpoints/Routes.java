package api.Endpoints;

public class Routes {

	
	public static String base_url = "https://petstore.swagger.io/v2";
	
	//User Module
	public static String post_url = base_url + "/user";
	public static String get_url = base_url + "/user/{username}";
	public static String update_url = base_url + "/user/{username}";
    public static String delete_url = base_url + "/user/{username}";
    
    
    public static String base_url_book_API = "https://simple-books-api.click";
	//Book API
    public static String Authentication_url = base_url_book_API + "/api-clients/";
    public static String Status_url = base_url_book_API + "/status";
    public static String Listofbooks_url = base_url_book_API +"/books/";
    public static String Signlebook_url = base_url_book_API + "/books/6";
    public static String Submitorder_url = base_url_book_API +"/orders";
    public static String  Getorder_url = base_url_book_API + "/orders/{orderId}";
    public static String Updateorder_url =  base_url_book_API + "/orders/{orderId}";
    public static String Deleteorder_url = base_url_book_API + "/orders/{orderId}";
    public static String Gettallorders_url = base_url_book_API +"/orders";
}
