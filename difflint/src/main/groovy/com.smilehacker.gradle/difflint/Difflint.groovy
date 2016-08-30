package com.smilehacker.gradle.difflint

import org.gradle.api.Plugin
import org.gradle.api.Project

public class Difflint implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("difflint", DifflintExtension)
        project.task('testTask') << {
            println "Hello gradle plugin"
            GitHelper helper = new GitHelper()
            helper.run(project.properties.get("branch", null) as String)
        }
    }
}