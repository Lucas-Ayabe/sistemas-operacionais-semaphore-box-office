package models.threads;

import java.util.concurrent.Semaphore;
import models.RandomInt;

public class PersonBuyProccess extends Thread {

    private int id = 0;
    private static int totalTickets = 100;
    private Semaphore semaphore;

    public PersonBuyProccess(int id, Semaphore semaphore) {
        this.semaphore = semaphore;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            var amountToBuy = (new RandomInt(1, 4)).generate();
            login();
            buy();
            validatePurchase(amountToBuy);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void login() throws InterruptedException {
        var time = new RandomInt(50, 2000);
        sleep(time.generate());
    }

    private void buy() throws InterruptedException {
        var time = new RandomInt(1000, 3000);
        sleep(time.generate());
    }

    private void validatePurchase(int amount) {
        try {
            semaphore.acquire();
            if (amount <= totalTickets) {
                totalTickets -= amount;
                System.out.println(
                    this +
                    " comprou " +
                    amount +
                    " ingressos, ingressos disponíveis: " +
                    totalTickets +
                    "\n"
                );
            } else {
                System.out.println(
                    "O número de tickets solicitados excede o número disponível, compra para " +
                    this +
                    " foi cancelada."
                );
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public String toString() {
        return "Pessoa " + id;
    }
}
