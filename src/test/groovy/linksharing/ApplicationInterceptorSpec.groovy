package linksharing

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class ApplicationInterceptorSpec extends Specification implements InterceptorUnitTest<ApplicationInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test custom interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"custom")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
