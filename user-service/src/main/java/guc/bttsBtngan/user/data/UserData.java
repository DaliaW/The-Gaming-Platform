package guc.bttsBtngan.user.data;

public class UserData {
    private String userName;
    private String Password;
    private String email;
    private String photo_ref;
    private boolean isModerator;
    private boolean isBanned;

    public UserData(String userName, String password, String email, String photo_ref, boolean isModerator, boolean isBanned) {
        this.userName = userName;
        this.Password = password;
        this.email = email;
        this.photo_ref = photo_ref;
        this.isModerator = isModerator;
        this.isBanned = isBanned;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto_ref() {
        return photo_ref;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoto_ref(String photo_ref) {
        this.photo_ref = photo_ref;
    }

    public void setModerator(boolean isModerator) {
        this.isModerator = isModerator;
    }

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "userName='" + userName + '\'' +
                ", Password='" + Password + '\'' +
                ", email='" + email + '\'' +
                ", photo_ref='" + photo_ref + '\'' +
                ", isModerator=" + isModerator +
                ", isBanned=" + isBanned +
                '}';
    }
}
