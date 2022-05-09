package guc.bttsBtngan.user.data;

import javax.persistence.*;

// For postgresql
@Entity
@Table
public class UserUserInteraction {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")

    private String userId;
    private String userName;
    private String Password;
    private String email;
    private String photoRef;
    private boolean isModerator;
    private boolean isBanned;

    public UserUserInteraction() {
    }

    public UserUserInteraction(String userId, String userName, String password, String email, String photoRef, boolean isModerator, boolean isBanned) {
        super();
        this.userId = userId;
        this.userName = userName;
        Password = password;
        this.email = email;
        this.photoRef = photoRef;
        this.isModerator = isModerator;
        this.isBanned = isBanned;
    }

    public UserUserInteraction(String userName, String password, String email, String photoRef, boolean isModerator, boolean isBanned) {
        super();
        this.userName = userName;
        Password = password;
        this.email = email;
        this.photoRef = photoRef;
        this.isModerator = isModerator;
        this.isBanned = isBanned;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public boolean isModerator() {
        return isModerator;
    }

    public void setModerator(boolean moderator) {
        isModerator = moderator;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    @Override
    public String toString() {
        return "UserUserInteraction{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", Password='" + Password + '\'' +
                ", email='" + email + '\'' +
                ", photoRef='" + photoRef + '\'' +
                ", isModerator=" + isModerator +
                ", isBanned=" + isBanned +
                '}';
    }
}
