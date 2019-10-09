package linksharing

import enums.Seriousness
import enums.Visibility
import grails.gorm.transactions.Transactional
import org.apache.commons.collections.CollectionUtils

@Transactional
class SubscriptionService {
    UserService userService

    def serviceMethod() {

    }

    void subscribeTopicsByUser(User user, Visibility visibility, int itr) {
        log.info("****************Saving Topic*****************")
        Topic topic =new Topic(name: "$user.firstName topic$itr", visibility: visibility, user: user)
       if(!Topic.findByUserAndName(user,topic.name)){
           try {
               topic.save(flush:true,failOnError:true)
               log.info("**************Topic Succesfully Saved*******************")
           }
           catch (Exception e){
               log.error("unable to save topic because of $e.message")
           }
           Subscription.findOrSaveWhere(user: user, topic: topic).errors.each {
               if (it.hasErrors()) {
                   log.error(it.allErrors.toString())
               } else log.info("Subscription Succesfully Saved and subscribed by user ")
           }
       }
        else log.info("Topic Already Exists")
    }

    void subscribeTopicNotCreatedByUser() {
        Map<Long, List<Topic>> map = getTopicsNotCreatedByUser()
        map.entrySet().stream().each {
            User user1 = User.findById(it.key)
            List<Topic> topics = it.value
            topics.each {
                try {
                    log.info("**************adding topics that are not created by users**********")
                    Subscription.findOrSaveWhere(user: user1, topic: it).errors.each {
                        if (it.hasErrors()) {
                            log.error(it.allErrors.toString())
                        } else log.info("**************topic subscribed successfully")
                    }
                }
                catch (Exception e) {
                    log.error("*************failed to save topics not created by user ***********")
                    log.error(e.getMessage())
                }
            }
        }
    }

    Map<Long, List<Topic>> getTopicsNotCreatedByUser() {
        Map<Long, List<Topic>> map = [:]
        List<User> users = userService.getAllUsers()
        if (CollectionUtils.isNotEmpty(users)) {
            users.each {
                List<Topic> topics = Topic.findAllByUserNotInList(Arrays.asList(it))
                if (CollectionUtils.isNotEmpty(topics)) {
                    map.put(it.id, topics)
                }
            }
        }
        map
    }

    Subscription findByUserAndTopic(User user,Topic topic){
        return Subscription.findByUserAndTopic(user,topic)
    }
    Boolean saveSubscription(Long id,String seriousnessStr,User user){
        if(!user.isAttached()){
            user.attach()
        }
        Topic topic=Topic.get(id)
        Seriousness seriousness=Seriousness.valueOf(seriousnessStr.toUpperCase())
        if(topic!=null){
            Subscription subscription=new Subscription(user:user,topic: topic,seriousness:seriousness)
            try {
                subscription.save(flush:true,failOnError:true)
                return true
            }
            catch (Exception e){
                return false
            }
        }
        else return false
    }

    Boolean deleteSubscription(Long id){
        Subscription subscription=Subscription.get(id)
        if(subscription){
            subscription.delete()
            return true
        }
        else return false
    }

    Subscription findById(Long id){
        Subscription.findById(id)
    }

    Boolean updateSubscription(Subscription subscription){
        try {
            subscription.save(flush:true,failOnError:true)
            return true
        }
        catch (Exception e){
            return  false
        }

    }
}
