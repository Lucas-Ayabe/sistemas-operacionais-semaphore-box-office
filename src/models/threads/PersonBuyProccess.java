package models.threads;

import java.util.concurrent.Semaphore;
import models.RandomInt;

public class PersonBuyProccess extends Thread {

    private int id = 0;
    private static int totalTickets = 100;
    private Semaphore semaphore;
    private boolean purchaseFailed = false;

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

            if (!purchaseFailed) {
                validatePurchase(amountToBuy);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void login() throws InterruptedException {
        var time = (new RandomInt(50, 3000)).generate();
        sleep(time);

        if (time >= 3000) {
            purchaseFailed = true;
            printPurchaseCanceling("Timeout, tempo de login excedeu o limite");
        }
    }

    private void buy() throws InterruptedException {
        var time = (new RandomInt(1000, 5500)).generate();
        sleep(time);

        if (time >= 5500) {
            purchaseFailed = true;
            printPurchaseCanceling("Tempo de sessão excedido");
        }
    }

    private void validatePurchase(int amount) {
        try {
            semaphore.acquire();
            if (amount <= totalTickets) {
                totalTickets -= amount;
                String amountComplement = amount == 1
                    ? "ingresso"
                    : "ingressos";
                System.out.println(
                    this +
                    " comprou " +
                    amount +
                    " " +
                    amountComplement +
                    ", ingressos disponíveis: " +
                    totalTickets +
                    "\n"
                );
            } else {
                printPurchaseCanceling(
                    "O número de tickets solicitados excede o número disponível"
                );
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private void printPurchaseCanceling(String message) {
        System.out.println(
            message + ". Compra para " + this + " foi cancelada."
        );
    }

    @Override
    public String toString() {
        return "Pessoa " + id;
    }
}
