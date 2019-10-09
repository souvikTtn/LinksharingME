package linksharing

class LoginController {
    UserService userService
    static allowedMethods = [register:'POST']
    def index() {
        /*List<User> users=User.findAll()
        session.user=users.get(0)*/
        if(flash.error)
        render(flash.error)
        else render("Hello Login Page")
    }

    //accepts two parameters userName and password
    def loginHandler(){
        String userName=params.userName
        User user=User.findByEmail(userName)
        if(user!=null){
            if(user.active){
                session.user=user
            }
            else {
                flash.error="your account is not active"
            }
        }
        else flash.error="User Not Found"
        redirect(controller: 'user',action: 'index')
        /*if(session.user){
            redirect(controller:'user',action:'index')
        }
        else render("failure")*/
    }
    def logOut(){
        session.invalidate()
        redirect(controller: 'login',action: 'index')
    }

    def register(){
        User user=new User(params)
        //user registration
        if(userService.getUserByEmail(user.email)==null){
            if(user.password.equals(user.confirmPassword)){
                if(userService.saveAndValidateUser(user))
                render("User saved Successfully")
                else render(user.errors.allErrors)
            }
            else render("password doesn't matches")
        }
        else {
            User user1=userService.getUserByEmail(user.email)
            user1.password=user.password
            userService.saveAndValidateUser(user1)
        }
    }
}
