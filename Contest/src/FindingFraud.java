/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Trung Pham
 */
import java.util.*;

public class FindingFraud {

    static String[] getSuspiciousList(String[] transactions) {
        HashMap<String, Transaction> lastTransactionMap
                = new HashMap<>();
        HashSet<String> fraud = new HashSet();
        boolean[] fraudTransaction = new boolean[transactions.length];
        String[] nameList = new String[transactions.length];
        for (int i = 0; i < transactions.length; i++) {
            Transaction trans = parseTransaction(i, transactions[i]);
            Transaction last = null;
            if (lastTransactionMap.containsKey(trans.name)) {
                last = lastTransactionMap.get(trans.name);
            }
            int index = detectInvalidTransactionID(last, trans);
            if (index != -1) {
                if (!fraud.contains(trans.name)) {
                    fraud.add(trans.name);
                    fraudTransaction[index] = true;
                }
            }
            nameList[i] = trans.name;
            lastTransactionMap.put(trans.name, trans);
        }
        String[] result = new String[fraud.size()];
        int index = 0;
        for (int i = 0; i < nameList.length; i++) {
            if (fraudTransaction[i]) {
                result[index++] = nameList[i];
            }
        }
        return result;

    }

    static int detectInvalidTransactionID(Transaction lastTransaction, Transaction currentTransaction) {
        if (lastTransaction != null) {
            if (!lastTransaction.location.equals(currentTransaction.location)) {
                if (currentTransaction.time - lastTransaction.time < 60) {
                    return lastTransaction.index;
                }
            }
        }
        if (currentTransaction.amount > 3000) {
            return currentTransaction.index;
        }
        return -1;
    }

    static Transaction parseTransaction(int index, String trans) {
        String[] tmp = trans.split("\\|");
        System.out.println(Arrays.toString(tmp));
        Transaction result = new Transaction(tmp[0], tmp[2].trim(), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[3]), index);
        return result;
    }

    static class Transaction {

        String name, location;
        int amount, time, index;

        public Transaction(String name, String location, int amount, int time, int index) {
            this.name = name;
            this.location = location;
            this.amount = amount;
            this.time = time;
            this.index = index;
        }
    }

    public static void main(String[] args) {
        String[] data = {"A|5000|X X|0", "B|200||1", "B|300|  |2"};
        System.out.println(Arrays.toString(getSuspiciousList(data)));
    }

}
