package linksharing

class User {
    Long id
    String firstName
    String lastName
    String password
    String email
    String photo
    Date lastUpdated
    Date dateCreated
    Boolean active
    Boolean admin
    String fullName
    String confirmPassword
    List<Topic> topics
    List<Subscription>subscriptions
    List<ReadingItem>readingItems
    List<Resource> resources

    static transients = ['fullName','confirmPassword']

    String getFullName() {
        return "$firstName $lastName"
    }

    static hasMany = [topics: Topic, subscriptions: Subscription, readingItems: ReadingItem, resources: Resource]
    static mapping = {
        version false
        photo(type: 'blob')
        sort id:"desc"
        topics lazy: false
    }
    static constraints = {
        password blank: false, nullable: false, minSize: 5
        photo nullable: true
        active nullable: true
        admin nullable: true
        email blank: false, nullable: false, email: true, unique: true
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        confirmPassword bindable:true
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + getFullName() + '\'' +
                '}';
    }
}
