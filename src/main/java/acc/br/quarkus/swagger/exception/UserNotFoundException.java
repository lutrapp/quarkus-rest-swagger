package acc.br.quarkus.swagger.exception;

public class UserNotFoundException extends Exception {

    /**
	 *
	 */
	private static final long serialVersionUID = -5869314468683783817L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
