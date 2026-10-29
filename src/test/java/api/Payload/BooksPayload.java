package api.Payload;

public class BooksPayload {
     
	public static String userAccessToken;
	String ClientName;
	String ClientEmail;
	int bookId;
	String customerName;
	 
	  public String getClientName() {
		return ClientName;
	}
	public void setClientName(String ClientName) {
		this.ClientName = ClientName;
	}
	
	
	public String getClientEmail() {
		return ClientEmail;
	}
	public void setClientEmail(String ClientEmail) {
		this.ClientEmail = ClientEmail;
	}
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
