package infra.file.storage

import groovy.transform.CompileStatic

/**
 * @author alari
 * @since 12/18/12 6:34 PM
 */
@CompileStatic
class AnnotatedFilesManager extends AbstractFilesManager {
    private Object domain
    private final FilesHolder holder
    private final String propertyName
    private final FileStorage storage

    Collection<String> fileNames


    AnnotatedFilesManager(def domain, FileStorageService fileStorageService, FilesHolder holder) {
        this.domain = domain
        this.holder = holder instanceof FilesHolder ? holder : (FilesHolder)domain.class.getAnnotation(FilesHolder)

        propertyName = this.holder.filesProperty()

        final String storageName = ((Closure<String>) this.holder.storage().newInstance(domain, domain)).call()
        storage = fileStorageService.getFileStorage(storageName)

        try {
            String getter = "get"
            getter += propertyName[0].toUpperCase()
            getter += propertyName.substring(1)
            domain.class.getMethod(getter)

            if (!domain."${propertyName}") {
                domain."${propertyName}" = []
            }

            fileNames = domain."${propertyName}"
        } catch(NoSuchMethodException e) {
            fileNames = []
        }
    }

    @Override
    FileStorage getStorage() {
        storage
    }

    @Override
    String getPath() {
        ((Closure<String>) holder.path().newInstance(domain, domain)).call()
    }

    @Override
    String getBucket() {
        ((Closure<String>) holder.bucket().newInstance(domain, domain)).call()
    }

    @Override
    void setFileNames(Collection<String> fileNames) {
        this.fileNames.clear()
        this.fileNames.addAll(fileNames)
    }

    @Override
    String[] getAllowedExtensions() {
        holder.allowedExtensions()
    }
}
