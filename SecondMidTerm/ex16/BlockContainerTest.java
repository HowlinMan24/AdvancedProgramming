package mk.kolokvium2.ex16;

import java.util.*;


class BlockContainer<T extends Comparable<T>> {

    public List<TreeSet<T>> blocks;
    public int maxElements;

    public BlockContainer(int maxElements) {
        this.blocks = new ArrayList<>();
        this.maxElements = maxElements;
        addBlock();
    }

    public void add(T a) {
        if (blocks.isEmpty() || blocks.get(blocks.size() - 1).size() == maxElements) {
            blocks.add(new TreeSet<>());
        }
        blocks.get(blocks.size() - 1).add(a);
        //        TreeSet<T> lastBLock = blocks.get(blocks.size() - 1);
//        if (lastBLock.size() == maxElements) {
//            addBlock();
//            lastBLock = blocks.get(blocks.size() - 1);
//        }
//        lastBLock.add(a);
    }

    public boolean remove(T a) {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            TreeSet<T> currentBlock = blocks.get(i);
            if (currentBlock.contains(a)) {
                currentBlock.remove(a);
                if (currentBlock.isEmpty()) {
                    blocks.remove(i);
                }
                return true;
            }
        }
        return false;
    }

    public void sort() {
//        for (TreeSet<T> block : blocks) {
//            TreeSet<T> sortedBlock = new TreeSet<>(block);
//            block.clear();
//            block.addAll(sortedBlock);
//        }
    }

    public void addBlock() {
        this.blocks.add(new TreeSet<>());
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (TreeSet<T> block : blocks) {
            st.append(block.toString()).append(",");
        }
        return st.toString();
    }
}

public class BlockContainerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = scanner.nextInt();
        BlockContainer<Integer> integerBC = new BlockContainer<Integer>(size);
        scanner.nextLine();
        Integer lastInteger = null;
        for (int i = 0; i < n; ++i) {
            int element = scanner.nextInt();
            lastInteger = element;
            integerBC.add(element);
        }
        System.out.println("+++++ Integer Block Container +++++");
        System.out.println(integerBC);
        System.out.println("+++++ Removing element +++++");
        integerBC.remove(lastInteger);
        System.out.println("+++++ Sorting container +++++");
        integerBC.sort();
        System.out.println(integerBC);
        BlockContainer<String> stringBC = new BlockContainer<String>(size);
        String lastString = null;
        for (int i = 0; i < n; ++i) {
            String element = scanner.next();
            lastString = element;
            stringBC.add(element);
        }
        System.out.println("+++++ String Block Container +++++");
        System.out.println(stringBC);
        System.out.println("+++++ Removing element +++++");
        stringBC.remove(lastString);
        System.out.println("+++++ Sorting container +++++");
        stringBC.sort();
        System.out.println(stringBC);
    }
}

// Вашиот код овде



