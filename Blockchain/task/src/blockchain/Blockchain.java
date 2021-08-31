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

    public void add() {
        String prevHash;
        if (chain.isEmpty()) {
            prevHash = "0";
        } else {
            prevHash = chain.get(chain.size() - 1).getHash();
        }

        chain.add(new Block(counter++, prevHash, prefix));
    }

    public boolean verify() {
        for (int i = 1; i < chain.size(); i++) {
            if (!chain.get(i).getPrevHash().equals(chain.get(i - 1).getHash())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return chain.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}
