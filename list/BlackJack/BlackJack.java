package list.BlackJack;

import db.Database;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJack {

    long startTime;
    long endTime;

    private String username;
    private class Card {
        String value;
        String type;

        Card(String value, String type) {
            this.value = value;
            this.type = type;
        }

        public String toString() {
            return value + "-" + type;
        }

        public int getValue() {
            if ("AJQK".contains(value)) { //A J Q K
                if (value.equals("A")) {
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value); //2-10
        }

        public boolean isAce() {
            return value.equals("A");
        }

        public String getImagePath() {
            return "img/Cards/" + toString() + ".png";
        }
    }

    ArrayList<Card> deck;
    Random random = new Random();

    //dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    //window
    int boardWidth = 600;
    int boardHeight = boardWidth;

    int cardWidth = 110;
    int cardHeight = 154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                //draw hidden card
                ClassLoader classLoader = getClass().getClassLoader();
                Image hiddenCardImg = new ImageIcon(classLoader.getResource("img/Cards/BACK.png")).getImage();
                if (!stayButton.isEnabled()) {
                    hiddenCardImg = new ImageIcon(classLoader.getResource("img/Cards/BACK.png")).getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                //draw dealer's hand
                if(dealerHand != null){
                    for (int i = 0; i < dealerHand.size(); i++) {
                        Card card = dealerHand.get(i);
                        Image cardImg = new ImageIcon(classLoader.getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, cardWidth + 25 + (cardWidth + 5)*i, 20, cardWidth, cardHeight, null);
                    }
                }

                //draw player's hand
                if(playerHand != null){
                    for (int i = 0; i < playerHand.size(); i++) {
                        Card card = playerHand.get(i);
                        Image cardImg = new ImageIcon(classLoader.getResource(card.getImagePath())).getImage();
                        g.drawImage(cardImg, 20 + (cardWidth + 5)*i, 320, cardWidth, cardHeight, null);
                    }
                }

                if (!stayButton.isEnabled()) {
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();

                    String message = "";
                    if (playerSum > 21) {
                        message = "You Lose!";
                        endTime = System.currentTimeMillis();
                        int totalTimeInMillis = (int) (endTime - startTime);
                        int totalTimeInSeconds = totalTimeInMillis / 1000;
                        playerBalance -= betAmount;
                        Database.addScore(username, playerBalance, totalTimeInSeconds, "blackjack");
                    }
                    else if (dealerSum > 21) {
                        message = "You Win!";
                        endTime = System.currentTimeMillis();
                        int totalTimeInMillis = (int) (endTime - startTime);
                        int totalTimeInSeconds = totalTimeInMillis / 1000;
                        playerBalance += 2 * betAmount;
                        Database.addScore(username, playerBalance, totalTimeInSeconds, "blackjack");
                    }
                    //both you and dealer <= 21
                    else if (playerSum == dealerSum && playerHand != null) {
                        message = "Tie!";
                        endTime = System.currentTimeMillis();
                        int totalTimeInMillis = (int) (endTime - startTime);
                        int totalTimeInSeconds = totalTimeInMillis / 1000;
                        playerBalance -= betAmount;
                        Database.addScore(username, playerBalance, totalTimeInSeconds, "blackjack");
                    }
                    else if (playerSum > dealerSum) {
                        message = "You Win!";
                        endTime = System.currentTimeMillis();
                        int totalTimeInMillis = (int) (endTime - startTime);
                        int totalTimeInSeconds = totalTimeInMillis / 1000;
                        playerBalance += 2 * betAmount;
                        Database.addScore(username, playerBalance, totalTimeInSeconds, "blackjack");
                    }
                    else if (playerSum < dealerSum) {
                        message = "You Lose!";
                        endTime = System.currentTimeMillis();
                        int totalTimeInMillis = (int) (endTime - startTime);
                        int totalTimeInSeconds = totalTimeInMillis / 1000;
                        playerBalance -= betAmount;
                        Database.addScore(username, playerBalance, totalTimeInSeconds, "blackjack");
                    }

                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    g.setColor(Color.white);
                    g.drawString(message, 220, 250);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");
    JButton betButton = new JButton("Bet");
    JButton replayButton = new JButton("Rejouer");
    JLabel balanceLabel = new JLabel("Balance: $1000");
    JLabel betLabel = new JLabel("Bet: $0");

    int playerBalance = 1000;
    int betAmount = 0;
    boolean betPlaced = false;

    public BlackJack(String usernamed) {
        startTime = System.currentTimeMillis();
        username = usernamed;
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(53, 101, 77));
        frame.add(gamePanel);

        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        buttonPanel.add(betButton);
        buttonPanel.add(replayButton);
        buttonPanel.add(balanceLabel);
        buttonPanel.add(betLabel);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.setFocusable(false);
        stayButton.setFocusable(false);
        betButton.setFocusable(false);
        replayButton.setFocusable(false);

        hitButton.setEnabled(false);
        stayButton.setEnabled(false);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card card = deck.remove(deck.size()-1);
                playerSum += card.getValue();
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if (reducePlayerAce() > 21) { //A + 2 + J --> 1 + 2 + J
                    hitButton.setEnabled(false);
                    stayButton.setEnabled(false);
                    endGame();
                }
                gamePanel.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);

                while (dealerSum < 17) {
                    Card card = deck.remove(deck.size()-1);
                    dealerSum += card.getValue();
                    dealerAceCount += card.isAce() ? 1 : 0;
                    dealerHand.add(card);
                }
                gamePanel.repaint();
                endGame();
            }
        });

        betButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!betPlaced) {
                    String betString = JOptionPane.showInputDialog(frame, "Enter bet amount:");
                    try {
                        betAmount = Integer.parseInt(betString);
                        if (betAmount <= playerBalance && betAmount > 0) {
                            betPlaced = true;
                            startGame();
                            playerBalance -= betAmount;
                            betLabel.setText("Bet: $" + betAmount);
                            balanceLabel.setText("Balance: $" + playerBalance);
                            hitButton.setEnabled(true);
                            stayButton.setEnabled(true);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid bet amount!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid bet amount!");
                    }
                }
            }
        });

        replayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                betPlaced = false;
                betAmount = 0;
                betLabel.setText("Bet: $" + betAmount);
                balanceLabel.setText("Balance: $" + playerBalance);
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                startTime = System.currentTimeMillis();
                startGame();
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();
    }

    public void startGame() {
        if (!betPlaced) return;

        if (deck == null || deck.isEmpty()) {
            buildDeck();
            shuffleDeck();
        }

        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size()-1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size()-1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        gamePanel.repaint();
    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }

    public void endGame() {
    }
}
