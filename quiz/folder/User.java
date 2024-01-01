package folder;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hab
 */
public class User {
    private String name;
    private String username;
    private int id;
    private String gender;
    private String email;
    private String password;
    private String imagePath;
    private String courseid;

    public User(String name, String username, int id, String gender, String email, String password, String imagePath) {
        this.name = name;
        this.username = username;
        this.id = id;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.imagePath = imagePath;
    }
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImagePath() {
        return imagePath;
    }
   public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }
}

