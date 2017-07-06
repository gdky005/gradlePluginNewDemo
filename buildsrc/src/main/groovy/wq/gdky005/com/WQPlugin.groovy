package wq.gdky005.com

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class WQPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new MyTransform(project))
    }
}