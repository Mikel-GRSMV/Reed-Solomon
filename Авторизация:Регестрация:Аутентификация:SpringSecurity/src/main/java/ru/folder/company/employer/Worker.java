package ru.folder.company.employer;


public interface Worker {
    default void work(){
        throw new RuntimeException("Not implemented");
    }
}
