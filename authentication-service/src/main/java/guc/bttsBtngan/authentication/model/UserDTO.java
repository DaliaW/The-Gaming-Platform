package guc.bttsBtngan.authentication.model;

public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String photo_ref;
    private boolean isModerator;
    private boolean isBanned;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_ref() {
        return photo_ref;
    }

    public void setPhoto_ref(String photo_ref) {
        this.photo_ref = photo_ref;
    }

    public boolean getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(boolean moderator) {
        isModerator = moderator;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean banned) {
        isBanned = banned;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
