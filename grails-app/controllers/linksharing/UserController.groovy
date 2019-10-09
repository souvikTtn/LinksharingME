package linksharing

class UserController {
    UserService userService
    def index() {
        User user=session.user
        render("$user.fullName User DashBoard")
    }

    def allUsers(){
       render(userService.getAllUsers())
    }
}
