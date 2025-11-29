package aoc.Day7;

import java.util.ArrayList;
import java.util.List;

public class Directory {
    private String name;
    private List<MyFile> files;
    private List<Directory> subdirectories;
    private final Directory parent;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.files = new ArrayList<>();
        this.subdirectories = new ArrayList<>();
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Directory find(String name) {
        for (Directory d : subdirectories) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    public void addDirectory(Directory directory) {
        subdirectories.add(directory);
    }

    public void addFile(MyFile file) {
        files.add(file);
    }

    public Directory getParent() {
        return parent;
    }

    public List<Directory> getSubdirectories() {
        return subdirectories;
    }

    public int getSize() {
        int size = 0;
        for (MyFile f : files) {
            size += f.size();
        }
        for (Directory d : subdirectories) {
            size += d.getSize();
        }
        return size;
    }

    public List<Integer> getALlSizes() {
        List<Integer> result = new ArrayList<>();
        result.add(getSize());
        for (Directory d : subdirectories) {
            List<Integer> sizes = d.getALlSizes();
            result.addAll(sizes);
        }
        return result;
    }
}
