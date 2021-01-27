package de.elvirakraft.docmanagement;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Der Nutzer " + id + " wurde nicht gefunden");
    }
}
