package dev.jamiecraane.ktf.configuration

import dev.jamiecraane.ktf.security.ADMIN_AUTH
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.FileTemplateResolver

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(
            (if (developmentMode) {
                FileTemplateResolver().apply {
                    cacheManager = null
                    prefix = "src/main/resources/templates/"
                }
            } else {
                ClassLoaderTemplateResolver().apply {
                    prefix = "templates/"
                    templateMode = TemplateMode.HTML
                }
            }).apply {
                suffix = ".html"
                characterEncoding = "utf-8"
            })

        routing {
            get("/admin/login") {
                call.respond(ThymeleafContent("admin/login", emptyMap()))
            }

            authenticate(ADMIN_AUTH) {
                get("/admin/dashboard") {
                    call.respond(ThymeleafContent("admin/dashboard", emptyMap()))
                }
            }
        }
    }
}
