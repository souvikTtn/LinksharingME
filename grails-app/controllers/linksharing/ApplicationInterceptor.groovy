package linksharing

import enums.Visibility


class ApplicationInterceptor {
    ApplicationInterceptor() {
        matchAll().excludes(controller: "login")
    }

    boolean before() {
        log.info("$controllerName will be called with params $params")
        if (!session.user) {
            redirect(controller: "login", action: "index")
            false
        }
        else true
    }

    boolean after() {
        log.info("$controllerName is called with params $params")
        true
    }

    void afterView() {
        // no-op
    }
}
