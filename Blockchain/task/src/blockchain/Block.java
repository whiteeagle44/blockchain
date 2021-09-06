package blockchain;

import java.security.MessageDigest;
import java.util.Date;

public class Block {
    private final String miner;
    private final long id;
    private final long timestamp;
    private long magicNumber = 0;
    private final String prevHash;
    private final String hash;
    private final long generationTime;

    public Block(long id, String prevHash, StringBuilder prefix) {
        this.miner = Thread.currentThread().getName();
        this.id = id;
        this.prevHash = prevHash;
        this.timestamp = new Date().getTime();

        String hashFound;
        do {
            magicNumber = (long) (Math.random() * Long.MAX_VALUE);
            hashFound = generateHash("" + id + timestamp + magicNumber + prevHash);
//            System.out.print(hashFound.substring(0, 8) + ", ");
        }
        while(!hashFound.startsWith(String.valueOf(prefix)));
        this.hash = hashFound;
//        System.out.print(this);
        generationTime = new Date().getTime() - timestamp;
//        System.out.println("Temp: " + generationTime);
    }

    public long getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getHash() {
        return hash;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Block: \n" +
                "Created by: " + miner + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block: \n" + prevHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block was generating for " + generationTime + " milliseconds\n";
    }
}
