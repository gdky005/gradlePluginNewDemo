import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import org.gradle.util.Clock

class TimingsListener implements TaskExecutionListener, BuildListener {

    private Clock clock
    private timings = []

    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    /**
     *
     * 类似这样：
     BUILD SUCCESSFUL in 22s

     57 actionable tasks: 57 executed
     Task timings:
     146ms  :app:generateDebugBuildConfig
     86ms  :gradlescriptdemo:compileGroovy
     273ms  :app:mergeDebugResources
     169ms  :app:processDebugManifest
     248ms  :app:processDebugResources
     1181ms  :app:compileDebugJavaWithJavac
     439ms  :app:transformClassesWith-->WQTransform<--ForDebug
     415ms  :app:transformClassesWithDexForDebug
     451ms  :app:packageDebug
     180ms  :app:mergeReleaseResources
     69ms  :app:processReleaseResources
     130ms  :app:compileReleaseJavaWithJavac
     172ms  :buildsrc:compileGroovy
     1197ms  :app:lintVitalRelease
     86ms  :app:packageRelease
     4246ms  :app:lint
     6528ms  :app:mockableAndroidJar

     *
      * @param buildResult
     */
    @Override
    void buildFinished(BuildResult buildResult) {
        println "Task timings:"
        for (timing in timings) {
            if (timing[0] >= 50) {
                printf "%7sms  %s\n", timing
            }
        }
    }

    @Override
    void beforeExecute(Task task) {
        clock = new org.gradle.util.Clock()
    }

    /**
     * 类似这样：
     *

     > Task :app:compileDebugJavaWithJavac
     :app:compileDebugJavaWithJavac took 1181ms
     taskName:compileDebugJavaWithJavac
     outputs.files.files: ------------start-----------------
     /Users/WangQing/Android_Pro/PrivatePro/Timer/app/build/intermediates/classes/debug
     outputs.files.files: ---------------end------------------

     > Task :app:compileDebugNdk
     :app:compileDebugNdk took 1ms
     taskName:compileDebugNdk
     outputs.files.files: ------------start-----------------
     /Users/WangQing/Android_Pro/PrivatePro/Timer/app/build/intermediates/ndk/debug/Android.mk
     /Users/WangQing/Android_Pro/PrivatePro/Timer/app/build/intermediates/ndk/debug/obj
     /Users/WangQing/Android_Pro/PrivatePro/Timer/app/build/intermediates/ndk/debug/lib
     outputs.files.files: ---------------end------------------



     *
     * @param task
     * @param taskState
     */
    @Override
    void afterExecute(Task task, TaskState taskState) {
        def ms = clock.timeInMs
        timings.add([ms, task.path])
        task.project.logger.warn "${task.path} took ${ms}ms"
        if(task.outputs.files.files) {
            task.project.logger.warn "taskName:${task.name}"
            task.project.logger.warn "outputs.files.files: ------------start----------------- "
            task.outputs.files.files.each {
                task.project.logger.warn "${it.absolutePath} "
            }
            task.project.logger.warn "outputs.files.files: ---------------end------------------ "
        }
    }
}