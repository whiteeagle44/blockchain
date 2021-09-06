package blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Blockchain {
    private final List<Block> chain = new ArrayList<>();
    private long counter = 1;
    private final StringBuilder prefix;

    public Blockchain(int numberOfZeros) {
        prefix = new StringBuilder();
        prefix.append("0".repeat(numberOfZeros));
    }

    public Block add() {
        Block block;
        String prevHash;
        do {
            if (chain.isEmpty()) {
                prevHash = "0";
            } else {
                prevHash = chain.get(chain.size() - 1).getHash();
            }
            block = new Block(counter, prevHash, prefix);
        } while (!verify(block));
        synchronized (this) {
            chain.add(block);
            counter++;

            System.out.print(block);
            if (block.getGenerationTime() < 1000) {
                prefix.append("0");
                System.out.println("N was increased to " + prefix.length());
            } else if(block.getGenerationTime() >= 1000 && block.getGenerationTime() < 2000) {
                System.out.println("N stays the same");
            } else {
                prefix.deleteCharAt(prefix.length() - 1);
                System.out.println("N was decreased by 1");
            }
            System.out.println();
            return block;
        }
    }

    public boolean verify() {
        for (int i = 1; i < chain.size(); i++) {
            if (!chain.get(i).getPrevHash().equals(chain.get(i - 1).getHash())) {
                return false;
            }
        }
        return true;
    }

    public synchronized boolean verify(Block block) {
        if (chain.isEmpty()) {
            return block.getPrevHash().equals("0");
        } else return block.getPrevHash().equals(chain.get(chain.size() - 1).getHash());
    }

    @Override
    public String toString() {
        return chain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}
