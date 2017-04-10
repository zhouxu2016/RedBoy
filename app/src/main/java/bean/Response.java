package bean;


public class Response {

	public String response;

	public boolean isSuccess() {
		return !"error".equals(response);
	}
}
