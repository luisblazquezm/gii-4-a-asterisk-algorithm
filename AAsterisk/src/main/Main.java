package main;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        Box[] productionList = {
                new Box(9,1,17),
                new Box(10,1,17),
                new Box(11,1,17),
                new Box(12,1,17),
                new Box(13,1,17),
                new Box(14,1,17),
                new Box(15,1,17),
                new Box(16,1,17),
                new Box(17,1,17),
                new Box(18,1,17),
                new Box(19,1,17),
                new Box(20,1,17),
                new Box(3,1,18),
                new Box(4,1,18),
                new Box(5,1,18),
                new Box(6,1,18),
                new Box(7,1,18),
                new Box(8,1,18),
                new Box(1,1,19),
                new Box(2,1,19),
        };

        StorageHouse storageHouse = new StorageHouse();
        List<StorageHouse> open = new ArrayList<StorageHouse>();
        List<StorageHouse> closed = new ArrayList<StorageHouse>();

        for (Box b : productionList)
        {
            storageHouse.addBox(b);
            System.out.println(storageHouse.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
