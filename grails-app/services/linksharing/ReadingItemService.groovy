package linksharing

import grails.gorm.transactions.Transactional
import org.apache.commons.collections.CollectionUtils

@Transactional
class ReadingItemService {
    SubscriptionService subscriptionService

    def serviceMethod() {
    }

    void createReadingItems() {
        List<User> users = User.findAll()
        if (CollectionUtils.isNotEmpty(users)) {
            users.each {
                List<Resource> resources
                resources = Resource.findAll("from Resource res,Subscription subs where res.topic = subs" +
                        ".topic " +
                        "and res" +
                        ".user != ${it.id} and subs.user = ${it.id}")

                resources.each {
                    try {
                        log.info("**********saving reading item*************")
                        ReadingItem.findOrSaveWhere(resource: it[0], user: it[0].user).errors.each {
                            if(it.hasErrors()){
                                throw new RuntimeException(it.allErrors.toString())
                            }
                        }
                       /* readingItem.save(flush:true,failOnError:true)*/
                        log.info("**********reading item saved successfully")
                    }
                    catch (Exception e) {
                        log.error("*********reading item can't be saved $e.message***********")
                    }
                }
            }
        }
    }
}
