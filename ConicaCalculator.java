import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConicaCalculator extends JFrame {
    private JTextField a11Field, a12Field, a22Field, a10Field, a20Field, a00Field;
    private JButton calculateButton;
    private JPanel graphPanel;
    private JTextArea resultArea;

    public ConicaCalculator() {
        setTitle("Calculator Conice");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel pentru input
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("a11:"));
        a11Field = new JTextField();
        inputPanel.add(a11Field);

        inputPanel.add(new JLabel("a12:"));
        a12Field = new JTextField();
        inputPanel.add(a12Field);

        inputPanel.add(new JLabel("a22:"));
        a22Field = new JTextField();
        inputPanel.add(a22Field);

        inputPanel.add(new JLabel("a10:"));
        a10Field = new JTextField();
        inputPanel.add(a10Field);

        inputPanel.add(new JLabel("a20:"));
        a20Field = new JTextField();
        inputPanel.add(a20Field);

        inputPanel.add(new JLabel("a00:"));
        a00Field = new JTextField();
        inputPanel.add(a00Field);

        calculateButton = new JButton("Calculează și Desenează");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.WEST);

        // Panel pentru grafic
        graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawAxes(g);
                drawConic(g);
            }
        };
        graphPanel.setBackground(Color.WHITE);
        add(graphPanel, BorderLayout.CENTER);

        // Text area pentru rezultate
        resultArea = new JTextArea(20, 30);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.EAST);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.repaint();
                resultArea.setText(""); // Resetăm rezultatele anterioare
                calculateConic();
            }
        });
    }

    private void calculateConic() {
        try {
            double a11 = Double.parseDouble(a11Field.getText());
            double a12 = Double.parseDouble(a12Field.getText());
            double a22 = Double.parseDouble(a22Field.getText());
            double a10 = Double.parseDouble(a10Field.getText());
            double a20 = Double.parseDouble(a20Field.getText());
            double a00 = Double.parseDouble(a00Field.getText());

            // Calcularea invarianților
            double traceA = a11 + a22;
            double detA = a11 * a22 - a12 * a12;
            double invariantCentroOrthogonal = a11 * a22 * a00 + a10 * a20 * 1 + a12 * a12;

            // Clasificarea conicei
            String conicType = classifyConic(traceA, detA, invariantCentroOrthogonal);

            // Afișarea rezultatelor
            resultArea.append("Invarianți:\n");
            resultArea.append("Trace(A): " + traceA + "\n");
            resultArea.append("Det(A): " + detA + "\n");
            resultArea.append("Invariant centro-ortogonal: " + invariantCentroOrthogonal + "\n");
            resultArea.append("Tipul conicei: " + conicType + "\n");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Introdu valori valide!");
        }
    }

    private String classifyConic(double traceA, double detA, double invariantCentroOrthogonal) {
        if (detA > 0) {
            if (invariantCentroOrthogonal < 0) {
                return "Elipsă nedegenerată";
            } else if (invariantCentroOrthogonal == 0) {
                return "Elipsă degenerată (punct dublu)";
            } else {
                return "Hiperbolă nedegenerată";
            }
        } else if (detA == 0) {
            if (invariantCentroOrthogonal > 0) {
                return "Parabolă nedegenerată";
            } else {
                return "Parabolă degenerată (drepte paralele)";
            }
        } else {
            return "Conica degenerată (drepte concurente)";
        }
    }

    private void drawAxes(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(400, 0, 400, 600); // Axă Y
        g.drawLine(0, 300, 800, 300); // Axă X

        // Marcaje pentru axa X
        for (int i = -400; i <= 400; i += 50) {
            g.drawLine(400 + i, 295, 400 + i, 305);
            if (i != 0) {
                g.drawString(Integer.toString(i), 400 + i, 320);
            }
        }

        // Marcaje pentru axa Y
        for (int i = -300; i <= 300; i += 50) {
            g.drawLine(395, 300 - i, 405, 300 - i);
            if (i != 0) {
                g.drawString(Integer.toString(i), 420, 300 - i);
            }
        }
    }

    private void drawConic(Graphics g) {
        try {
            double a11 = Double.parseDouble(a11Field.getText());
            double a12 = Double.parseDouble(a12Field.getText());
            double a22 = Double.parseDouble(a22Field.getText());
            double a10 = Double.parseDouble(a10Field.getText());
            double a20 = Double.parseDouble(a20Field.getText());
            double a00 = Double.parseDouble(a00Field.getText());

            g.setColor(Color.RED); // Culoare pentru conică

            // Desenăm conica pe baza coeficientilor
            for (int x = -400; x < 400; x++) {
                for (int y = -300; y < 300; y++) {
                    double result = a11 * (x / 100.0) * (x / 100.0) +
                            2 * a12 * (x / 100.0) * (y / 100.0) +
                            a22 * (y / 100.0) * (y / 100.0) +
                            2 * a10 * (x / 100.0) +
                            2 * a20 * (y / 100.0) +
                            a00;

                    if (Math.abs(result) < 0.1) { // Puncte pe conică
                        g.drawLine(x + 400, -y + 300, x + 400, -y + 300); // Trasăm un punct
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Introdu valori valide!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConicaCalculator frame = new ConicaCalculator();
            frame.setVisible(true);
        });
    }
}
