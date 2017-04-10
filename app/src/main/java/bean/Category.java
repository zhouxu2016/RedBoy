package bean;

/**
 * Created by yuki on 11/12 0012.
 */

public class Category {
    public String name;
    public String tag;

    public Category(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Category{" + "name='" + name + '\'' + ", tag='" + tag + '\'' + '}';
    }
}
