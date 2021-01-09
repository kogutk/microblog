package pl.wwsis.MicroBlog.model;

public enum UserStatus {

	INVISIBLE("I"), ACTIVE("A"), OCCUPIED("O"), BACKSOON("B");

	private String code;

	private UserStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
