package linksharing

import grails.gorm.transactions.Transactional

@Transactional
class UserService {
    def messageSource
    def serviceMethod() {
    }
    List<User> createUsers() {
        String pass=messageSource.getMessage('default.password',null,Locale.default)
        User normalUser = new User(firstName: "subham", lastName: "chakraborty", password:pass ,
                email: "user@gmail" +
                ".com", lastUpdated: new Date(), dateCreated: new Date(), active: true, admin: false)
        User admin = new User(firstName: "souvik", lastName: "chakraborty", password:pass , email:
    "admin@gmail" +
                ".com", lastUpdated: new Date(), dateCreated: new Date(), active: true, admin: true)

        List<User> users=[normalUser,admin]
        users.each {
            if(!User.findByEmail(it.email)){
                it.save(failOnError:true,flush:true)
                log.info("User Succesfully Saved")
                return users
            }
            else {
                log.error("error while saving user"+it.errors.allErrors)
                return null
            }
        }
    }

    List<User> getAllUsers(){
        return User.findAll()
    }

    User getUserByEmail(String email){
        return User.findByEmail(email)
    }
    User saveAndValidateUser(User user){
        if(user.validate()){
            return user.save(flush:true)
        }
        else return null
    }
}
