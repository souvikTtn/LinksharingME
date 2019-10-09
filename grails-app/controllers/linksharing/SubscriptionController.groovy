package linksharing

import enums.Seriousness

class SubscriptionController {

    SubscriptionService subscriptionService

    def index() {}

    def save(Long id, String seriousnessStr) {
        User loggeduser = session.user
        if (subscriptionService.saveSubscription(id, seriousnessStr, loggeduser))
            render("Subscription saved Successfully ")
        else render("Subscription failed")
    }

    def delete(Long id) {
        if (subscriptionService.deleteSubscription(id))
            render("Subscription successfully deleted")
        else render("Subscription does not exists")
    }

    def update(Long id,String SeriousnessStr){
        Seriousness seriousness=Seriousness.stringToEnum(SeriousnessStr)
        Subscription subscription=subscriptionService.findById(id)
        if(subscription!=null){
            subscription.seriousness=seriousness
            if(subscriptionService.updateSubscription(subscription))
                render("Subscription updated successfully")
            else render("Subscription updation failure")
        }
    }
}
