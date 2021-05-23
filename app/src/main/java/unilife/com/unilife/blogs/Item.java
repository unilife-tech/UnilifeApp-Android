package unilife.com.unilife.blogs;

public class Item {
    private String name;
    private int imageSource;

    public Item (int imageSource, String name) {
        this.name = name;
        this.imageSource = imageSource;
    }

    public String getName() {
        return name;
    }

    public int getImageSource() {
        return imageSource;
    }
}
