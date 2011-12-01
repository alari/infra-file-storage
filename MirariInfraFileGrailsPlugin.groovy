import grails.util.Environment
import ru.mirari.infra.file.FileStorageHolder
import ru.mirari.infra.file.LocalFileStorage
import ru.mirari.infra.file.S3FileStorage

class MirariInfraFileGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Mirari File Storage Plugin" // Headline display name of the plugin
    def author = "Dmitry Kurinskiy"
    def authorEmail = "name.alari@gmail.com"
    def description = '''\
Provides convenient methods to store files on local path in dev/test mode and on Amazon S3 on production.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/mirari-infra-file"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
    //    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
    //    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [system: "github", url: "https://github.com/alari/mirari-infra-file/issues"]

    // Online location of the plugin's browseable source code.
    def scm = [url: "http://github.com/alari/mirari-infra-file"]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        s3FileStorage(S3FileStorage)
        localFileStorage(LocalFileStorage)
        fileStorage(FileStorageHolder) {
            storage = ref(Environment.isWarDeployed() ? "s3FileStorage" : "localFileStorage")
        }
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}