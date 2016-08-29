package com.smilehacker.gradle.difflint;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;

/**
 * Created by kleist on 16/8/29.
 */
public class GitHelper {
    public void init() {
        try {
            Repository repo = new FileRepositoryBuilder().readEnvironment().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
