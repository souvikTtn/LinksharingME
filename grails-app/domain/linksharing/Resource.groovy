package linksharing

class Resource {
    //user by default created by
    //topic by default
    String description
    Date lastUpdated
    Date dateCreated
    static constraints = {

    }
    static mapping = {
        description(type: 'text')
    }
    static belongsTo = [user:User,topic:Topic]
    static hasMany = [ratings:Rating,readingItems:ReadingItem]
}
