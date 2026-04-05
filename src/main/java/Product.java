public class Product {
    private String id;
    private String name;
    private String imagePath;
    private double startingPrice;
    public Product(String id, String name,String imagePath, double startingPrice) {
        this.id = id;
        this.name = name;
        this.imagePath=imagePath;
        this.startingPrice = startingPrice;
    }
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }
    public String getName() {
        return name;
    }
}
