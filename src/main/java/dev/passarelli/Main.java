package dev.passarelli;

import dev.passarelli.controller.CompetitionController;
import dev.passarelli.controller.MatchController;
import dev.passarelli.model.Competition;
import dev.passarelli.model.Match;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main extends JFrame {
    private JComboBox<String> competitionComboBox;
    private DefaultListModel<String> matchListModel;
    private JList<String> matchList;
    private JTextArea matchDetailsArea;
    private MatchController matchController;
    private CompetitionController competitionController;
    private JPanel matchDetailsPanel;

    public Main() {
        setTitle("Football Match App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        matchController = new MatchController();
        competitionController = new CompetitionController();

        matchDetailsPanel = new JPanel(new BorderLayout());
        add(matchDetailsPanel, BorderLayout.EAST);
        // Carica le competizioni di default
        List<Competition> competitions;
        try {
            competitions = competitionController.getCompetitions();
            populateCompetitionComboBox(competitions);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle competizioni!");
            return;
        }

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(competitionComboBox);

        JButton fetchMatchesButton = new JButton("Ottieni Partite");
        fetchMatchesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Match> matches = matchController.getMatchesByCompetition(String.valueOf(competitionComboBox.getSelectedItem()));
                    updateMatchList(matches);
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        controlPanel.add(fetchMatchesButton);

        add(controlPanel, BorderLayout.NORTH);

        matchListModel = new DefaultListModel<>();
        matchList = new JList<>(matchListModel);
        matchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        matchList.addListSelectionListener(e -> {
            if (matchList.getSelectedIndex() != -1) {
                String selectedMatchString = matchListModel.getElementAt(matchList.getSelectedIndex());
                // Trova l'ID tra le parentesi quadre
                String selectedMatchId = selectedMatchString.replaceAll("\\[([^\\]]+)\\].*", "$1");
                Match selectedMatch = null;
                try {
                    selectedMatch = matchController.getMatchDetails(selectedMatchId);
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                displayMatchDetails(selectedMatch);
            }
        });
        JScrollPane matchListScrollPane = new JScrollPane(matchList);
        add(matchListScrollPane, BorderLayout.WEST);

        matchDetailsArea = new JTextArea();
        matchDetailsArea.setEditable(false);
        JScrollPane matchDetailsScrollPane = new JScrollPane(matchDetailsArea);
        add(matchDetailsScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void populateCompetitionComboBox(List<Competition> competitions) {
        competitionComboBox = new JComboBox<String>();
        for (Competition competition : competitions) {
            competitionComboBox.addItem(competition.getCode());
        }

        competitionComboBox.setPreferredSize(new Dimension(150, competitionComboBox.getPreferredSize().height));
    }


    private void updateMatchList(List<Match> matches) {
        matchListModel.clear();
        for (Match match : matches) {
            String matchString = "[" + match.getId() + "] ";
            matchString += match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName();
            matchListModel.addElement(matchString);
        }
    }

    private void displayMatchDetails(Match match) {
        String matchDetails = "Partita: " + match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName() + "\n";
        matchDetails += "Campionato: " + match.getCompetition().getName() + "\n";

        String statusMessage = "";
        if (match.getStatus().equalsIgnoreCase("IN_PLAY")) {
            statusMessage = "Stato: IN CORSO";
        } else if (match.getStatus().equalsIgnoreCase("SCHEDULED")) {
            statusMessage = "Stato: Inizia più tardi";
        } else if (match.getStatus().equalsIgnoreCase("FINISHED")) {
            statusMessage = "Stato: FINITA";
        }
        matchDetails += statusMessage;

        matchDetails += "\nRisultato: " + match.getScore().getFullTime().getHome() + " - " + match.getScore().getFullTime().getAway() + "\n";
        matchDetails += "Data: " + match.getUtcDate() + "\n";

        matchDetailsArea.setText(matchDetails);
        matchDetailsPanel.removeAll(); // Rimuovi i componenti precedenti dal pannello dei dettagli della partita

        try {
            // Carica e aggiungi l'immagine della squadra di casa al pannello dei dettagli della partita
            if (match.getHomeTeam().getCrest() != null && !match.getHomeTeam().getCrest().isEmpty()) {
                URL homeTeamLogoUrl = new URL(match.getHomeTeam().getCrest());
                Image homeTeamLogo = new ImageIcon(homeTeamLogoUrl).getImage();
                if (homeTeamLogo != null) {
                    // Ridimensiona l'immagine della squadra di casa e aggiungila al pannello dei dettagli della partita
                    Image scaledHomeTeamLogo = homeTeamLogo.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    JLabel homeTeamLogoLabel = new JLabel(new ImageIcon(scaledHomeTeamLogo));
                    matchDetailsPanel.add(homeTeamLogoLabel, BorderLayout.WEST);
                }
            }
            // Carica e aggiungi l'immagine della squadra ospite al pannello dei dettagli della partita
            if (match.getAwayTeam().getCrest() != null && !match.getAwayTeam().getCrest().isEmpty()) {
                URL awayTeamLogoUrl = new URL(match.getAwayTeam().getCrest());
                Image awayTeamLogo = new ImageIcon(awayTeamLogoUrl).getImage();
                if (awayTeamLogo != null) {
                    // Ridimensiona l'immagine della squadra ospite e aggiungila al pannello dei dettagli della partita
                    Image scaledAwayTeamLogo = awayTeamLogo.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    JLabel awayTeamLogoLabel = new JLabel(new ImageIcon(scaledAwayTeamLogo));
                    matchDetailsPanel.add(awayTeamLogoLabel, BorderLayout.EAST);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        matchDetailsPanel.revalidate(); // Aggiorna il pannello dei dettagli della partita
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
