package linksharing

class LinkResource extends Resource {
    String url
    static constraints = {
        url (url: true)
    }

    @Override
    public String toString() {
        return "LinkResource{" +
                "url='" + url + '\'' +
                '}';
    }
}
