package views;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import models.threads.PersonBuyProccess;

public class BoxOffice {

    public static void main(String[] args) {
        var semaphore = new Semaphore(1);
        IntStream
            .range(1, 301)
            .forEach(id -> (new PersonBuyProccess(id, semaphore)).start());
    }
}
