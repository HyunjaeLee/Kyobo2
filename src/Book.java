public class Book {

    private String title;
    private String orderId;
    private String status;
    private String barcode;
    private String ejkGb; // Example: KOR

    public Book() {
    }

    public Book(String title, String orderId, String status, String barcode, String ejkGb) {
        this.title = title;
        this.orderId = orderId;
        this.status = status;
        this.barcode = barcode;
        this.ejkGb = ejkGb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEjkGb() {
        return ejkGb;
    }

    public void setEjkGb(String ejkGb) {
        this.ejkGb = ejkGb;
    }

    @Override
    public String toString() {
        return title;
    }

}
