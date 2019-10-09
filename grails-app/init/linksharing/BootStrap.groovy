package linksharing

import org.apache.commons.collections.CollectionUtils

class BootStrap {
    UserService userService
    TopicService topicService
    SubscriptionService subscriptionService
    ResourceService resourceService
    ReadingItemService readingItemService
    static boolean flag=false
    def init = { servletContext ->
        if(CollectionUtils.isEmpty(userService.getAllUsers()))
            flag=true
        if(flag){
            userService.createUsers()
            if(CollectionUtils.isNotEmpty(userService.getAllUsers())){
                userService.getAllUsers().each {
                    topicService.createTopics(it)
                }
                resourceService.createResources()
            }
            subscriptionService.subscribeTopicNotCreatedByUser()
            readingItemService.createReadingItems()
            resourceService.addResourceRating()
            flag=false
        }
    }
    def destroy = {
    }
}
