package com.smilehacker.gradle.difflint;


import com.android.build.gradle.tasks.Lint;
import com.android.tools.lint.LintCliClient;
import com.android.tools.lint.client.api.IssueRegistry;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kleist on 16/8/29.
 */
public class GitHelper {

    private Git mGit;

    public GitHelper() {
        Repository  repo = null;
        try {
            repo = new FileRepositoryBuilder()
                    .readEnvironment()
                    .findGitDir()
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("can't make project");
            return;
        }

        mGit = new Git(repo);
    }

    public void run(String branch) {
        System.out.println("start task branch = " + branch);
        if (branch == null || "".equals(branch)) {
            System.out.println("do status");
            doStatus();
        } else if (branch.contains("...")) {
            String[] branches = branch.split("...");
            compareBranch(branches[0], branches[1]);
        } else {
            compareBranch(branch);
        }
    }

    private void doStatus() {
        List<String> fileNames = getDiffFileWithCMDStatus();
        System.out.println("1");
        LintCliClient cliClient = new LintCliClient();
        System.out.println("2");
        IssueRegistry issueRegistry = new Lint.LintGradleIssueRegistry();
        System.out.println("3");

        List<File> files = new LinkedList<>();
        for (String name : fileNames) {
            System.out.println("open file " + name);
            File file = new File(name);
            files.add(file);
        }
        System.out.println("open files, get files num=" + files.size());
        try {
            cliClient.run(issueRegistry, files);
        } catch (IOException e) {
            System.out.println("lint error");
            e.printStackTrace();
        }
    }

    private void compareBranch(String branch) {

    }

    private void compareBranch(String branch1, String branch2) {

    }

    private List<String> getDiffFileWithCMDStatus() {
        List<String> files = new LinkedList<>();
        Status status = null;
        try {
            status = mGit.status().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.out.println("can't do status");
            return Collections.emptyList();
        }
        files.addAll(status.getAdded());
        files.addAll(status.getChanged());
        files.addAll(status.getModified());
        files.addAll(status.getUntracked());
        files.addAll(status.getUntrackedFolders());
        System.out.println("do status success, get files =" + files);
        return files;
    }

    public void init() {
        Repository  repo = null;
        try {
            repo = new FileRepositoryBuilder()
                    //.setGitDir(new File("./"))
                    .readEnvironment()
                    .findGitDir()
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Git git = new Git(repo);
        try {
            Status status = git.status().call();
            System.out.println("Added: " + status.getAdded());
            System.out.println("Changed: " + status.getChanged());
            System.out.println("Conflicting: " + status.getConflicting());
            System.out.println("ConflictingStageState: " + status.getConflictingStageState());
            System.out.println("IgnoredNotInIndex: " + status.getIgnoredNotInIndex());
            System.out.println("Missing: " + status.getMissing());
            System.out.println("Modified: " + status.getModified());
            System.out.println("Removed: " + status.getRemoved());
            System.out.println("Untracked: " + status.getUntracked());
            System.out.println("UntrackedFolders: " + status.getUntrackedFolders());

        } catch (GitAPIException e) {
            e.printStackTrace();
        }

    }
}
