package linksharing

class ReadingItem {
    //resource by default
    //user by default
    Boolean isRead=Boolean.FALSE
    Date lastUpdated
    Date dateCreated

    static constraints = {
        resource(unique: 'user')
    }
    static belongsTo = [user:User,resource:Resource]
}
