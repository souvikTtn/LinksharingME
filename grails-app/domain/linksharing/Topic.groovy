package linksharing

import enums.Visibility

class Topic {
    //user by default created by
    String name
    Visibility visibility=Visibility.PUBLIC
    Date lastUpdated
    Date dateCreated

    static belongsTo = [user:User]
    static hasMany = [subscription:Subscription,resources:Resource]
   //name is unique per user
    static constraints = {
        name(blank: false, unique: 'user')
        lastUpdated(nullable: true)
    }

    static mapping = {
         sort name: "asc"
    }

  /*  def afterInsert(){
        log.info("***********after inserting topic **************")
        Subscription.withNewSession {
            Subscription subscription=new Subscription(user:this.user,topic: this)
            user.addToSubscriptions(subscription)
            user.save(flush:true)
           *//* subscription.save(flush:true,failOnError:true)*//*
            log.info("********Subscription saved*************")
        }
    }*/

    @Override
     String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                '}';
    }
}