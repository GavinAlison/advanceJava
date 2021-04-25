package com.alison.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class FolderProcessor extends RecursiveTask<List<String>> {
    private final String path;
    private final String extension;

    public FolderProcessor(String path,String extension) {
        this.extension = extension;
        this.path = path;
    }

    @Override
    protected List<String> compute() {
        List<String> list = new ArrayList<String>();
        List<FolderProcessor> tasks = new ArrayList<FolderProcessor>();
        File file = new File(path);
        File[] content= file.listFiles();
        if(content != null){
            for (File aContent : content) {
                if (aContent.isDirectory()) {
                    FolderProcessor task =
                            new FolderProcessor(aContent.getAbsolutePath(), extension);
                    task.fork();
                    tasks.add(task);
                } else {
                    if (checkFile(aContent.getName())) {
                        list.add(aContent.getAbsolutePath());
                    }
                }
            }
        }

        if (tasks.size() > 50)
        {
            System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), tasks.size());
        }
        addResultsFromTasks(list, tasks);
        return list;
    }

    private void addResultsFromTasks(List<String> list, List<FolderProcessor> tasks) {
        for (FolderProcessor item : tasks)
        {
            list.addAll(item.join());
        }
    }

    private boolean checkFile(String name) {
        return name.endsWith(extension);
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FolderProcessor system = new FolderProcessor("C://", "log");
        FolderProcessor library = new FolderProcessor("C://", "log");
        FolderProcessor users = new FolderProcessor("D://", "log");
        pool.execute(system);
        pool.execute(library);
        pool.execute(users);
        do
        {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
            System.out.printf("******************************************\n");
            try
            {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        } while ((!system.isDone()) || (!library.isDone()) || (!users.isDone()));
        pool.shutdown();
        List<String> results;
        results = system.join();
        System.out.printf("System: %d files found.\n", results.size());
        results = library.join();
        System.out.printf("Library: %d files found.\n", results.size());
        results = users.join();
        System.out.printf("Users: %d files found.\n", results.size());
    }
}
