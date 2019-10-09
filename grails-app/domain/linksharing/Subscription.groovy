package linksharing

import enums.Seriousness

class Subscription {
    //user bydefault
    //topic bydefault
    Seriousness seriousness=Seriousness.SERIOUS
    Date lastUpdated
    Date dateCreated

    static belongsTo = [topic:Topic,user:User]
    static mapping = {
    }

    static constraints = {
        topic(unique: 'user')
    }

}
