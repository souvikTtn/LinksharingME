package linksharing

class Rating {
    //resource by default
    User createdBy
    Integer score
    Date lastUpdated
    Date dateCreated


    static constraints = {
        score(max: 5, min: 1)
        resource(unique: 'createdBy')
    }
    static belongsTo = [resource: Resource]
}
