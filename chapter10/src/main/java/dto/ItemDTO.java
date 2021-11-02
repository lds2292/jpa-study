package dto;

public class ItemDTO {

    private String name;
    private String username;
    private int price;

    public ItemDTO() {
    }

    public ItemDTO(String name, String username, int price) {
        this.name = name;
        this.username = username;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
            "name='" + name + '\'' +
            ", username='" + username + '\'' +
            ", price=" + price +
            '}';
    }
}
