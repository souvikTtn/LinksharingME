package linksharing

class ResourceController {

    ResourceService resourceService
    def index() { }

    def delete(Long id){
        if(resourceService.deleteResource(id)){
            log.info("Resource Successfully deleted")
            render("Resource Successfully deleted")
        }
         render("Resource does not exists ")
    }
}
