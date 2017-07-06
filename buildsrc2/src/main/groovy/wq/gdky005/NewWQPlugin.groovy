package wq.gdky005

import org.gradle.api.Plugin
import org.gradle.api.Project

class NewWQPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("========================")
        System.out.println("这是第二个插件!")
        System.out.println("========================")
    }
}