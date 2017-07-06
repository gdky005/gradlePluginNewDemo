package wq.gdky005

import org.gradle.api.Plugin
import org.gradle.api.Project

class WQPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        println("========================")
        println("hello gradle plugin!")
        println("========================")
    }
}