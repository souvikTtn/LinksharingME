package linksharing

import enums.Visibility
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.ObjectNotFoundException

@Transactional
class TopicService {
    SubscriptionService subscriptionService
    ResourceService resourceService

    def serviceMethod() {

    }

    void createTopics(User user) {
        5.times {
            Visibility visibility
            if (it % 2) {
                visibility = Visibility.PRIVATE
            } else visibility = Visibility.PUBLIC
            subscriptionService.subscribeTopicsByUser(user,visibility,it)
        }
    }
    Topic getTopicById(Long id){
        return Topic.read(id)
    }

    List<Topic> getAllTopics(){
        Topic.findAll()
    }

    Boolean deleteTopic(Long id){
        try{
            Topic.load(id).delete()
            return true
        }
        catch (ObjectNotFoundException obj){
            log.error("Topic Doesn't exists "+obj.message)
            return false
        }
    }

    Boolean saveTopic(GrailsParameterMap params, User user){
        if(!user.isAttached()){
            user.attach()
        }
        Topic topic=new Topic(user: user,name: params.name)
        try{
            topic.save(flush:true,failOnError:true)
            return true
        }
        catch (Exception e){
            return false
        }
    }
}
