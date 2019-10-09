package linksharing

import grails.gorm.transactions.Transactional
import org.apache.commons.collections.CollectionUtils
import org.hibernate.ObjectNotFoundException

@Transactional
class ResourceService {
    TopicService topicService

    def serviceMethod() {
    }

    void createResources() {
        List<Topic> topics = Topic.findAll()
        if (CollectionUtils.isNotEmpty(topics)) {
            topics.each {
                String topicName = it.getName().replaceAll(/\s/, '')
                Topic topic = it
                User user = it.getUser()
                2.times {
                    log.info("**************saving Resources****************")
                    LinkResource.findOrSaveWhere(url: "https://www.$topicName" + "resource$it" + ".com",
                            description:
                                    "linkresource$it $topicName", user: user, topic: topic).errors.each {
                        if (it.hasErrors()) {
                            log.error(it.allErrors.toString())
                        }
                    }
                    log.info("**************Resource successfully saved****************")
                }
                2.times {
                    log.info("**************saving Resources****************")
                    DocumentResource.findOrSaveWhere(filePath: "/../myPath/$topicName", description:
                            "documentResource$it $topicName", user: user, topic: topic).errors.each {
                        if (it.hasErrors()) {
                            log.error(it.allErrors.toString())
                        }
                    }
                    log.info("**************Resource successfully saved****************")

                }
            }
        }
    }
    // to add dummy ratings for user
    void addResourceRating() {
        List<ReadingItem> unreadItems = ReadingItem.findAllByIsRead(Boolean.FALSE)
        if (CollectionUtils.isNotEmpty(unreadItems)) {
            unreadItems.each {
                log.info("**********saving DummyRatings of ReadingItems***************")
                Rating.findOrSaveWhere(createdBy: it.user, resource: it.resource, score: ((it.id.intValue()) % 5) == 0
                        ? 1 : (it
                        .id.intValue()) % 5)
                        .errors
                        .each {
                    if (it.hasErrors()) {
                        log.error(it.allErrors.toString())
                    }
                }
                log.info("***********ratings Successfully Saved********************")
            }
        }
    }
    Boolean deleteResource(Long id){
        try{
            Resource.load(id).delete()
            return true
        }
        catch (ObjectNotFoundException obj){
            log.error("Resource Doesn't exists "+obj.message)
            return false
        }
    }
}