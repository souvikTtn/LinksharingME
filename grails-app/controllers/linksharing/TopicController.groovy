package linksharing
import enums.Visibility

class TopicController {
    SubscriptionService subscriptionService
    TopicService topicService

    def index() {}

    def show(Long id) {
        User user = session.user
        Topic topic = topicService.getTopicById(id)
        if (topic != null) {
            if (topic.visibility.equals(Visibility.PUBLIC)) {
                render("Success")
            } else {
                Subscription subscription = subscriptionService.findByUserAndTopic(user, topic)
                if (subscription != null) {
                    render("Success")
                } else {
                    flash.error = "No Subscription for the user"
                    redirect(controller: 'user', action: 'index')
                }
            }
        } else {
            flash.error = "Topic Not Found"
            redirect(controller: 'user', action: 'index')
        }
    }

    def allTopics() {
        render(topicService.getAllTopics())
    }

    def delete(Long id) {
        if (topicService.deleteTopic(id)) {
            log.info("Topic Successfully deleted")
            render("Topic Successfully deleted")
        }
        render("Topic does not exists ")
    }

    def saveTopic() {
        User user = session.user
        if(topicService.saveTopic(params, user))
            render("Topic Successfully added")
        render("Topic Addition failed")
    }
}
