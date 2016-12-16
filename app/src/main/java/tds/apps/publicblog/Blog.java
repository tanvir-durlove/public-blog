package tds.apps.publicblog;

/**
 * Created by td on 12/16/16.
 */

public class Blog {

    private String title;
    private String desc;
    private String image;
    private String username;


    public Blog(){

    }

    public Blog(String desc, String title, String image) {
        this.desc = desc;
        this.title = title;
        this.image = image;
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

