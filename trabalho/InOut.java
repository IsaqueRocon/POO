import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class InOut {
    private static final ImageIcon INFO_ICON = buildIcon(new Color(33, 150, 243), "i");
    private static final ImageIcon INPUT_ICON = buildIcon(new Color(0, 150, 136), "?");
    private static final ImageIcon WARN_ICON = buildIcon(new Color(255, 152, 0), "!");
    private static final ImageIcon SUCCESS_ICON = buildIcon(new Color(76, 175, 80), "V");
    private static final ImageIcon ERROR_ICON = buildIcon(new Color(244, 67, 54), "X");

    private InOut() {
    }

    public static void exibirMensagem(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE, INFO_ICON);
    }

    public static void exibirAviso(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE, WARN_ICON);
    }

    public static void exibirErro(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE, ERROR_ICON);
    }

    public static void exibirSucesso(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE, SUCCESS_ICON);
    }

    public static String lerTexto(String titulo, String mensagem) {
        return JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE);
    }

    public static int lerInteiro(String titulo, String mensagem) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE, INPUT_ICON, null, "")
                .toString();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException ex) {
                exibirErro("Valor inválido", "Digite um número inteiro válido.");
            } catch (NullPointerException ex) {
                exibirAviso("Entrada cancelada", "A operação foi cancelada. Tente novamente.");
            }
        }
    }

    public static boolean confirmar(String titulo, String mensagem) {
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, titulo, JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE, INPUT_ICON);
        return opcao == JOptionPane.YES_OPTION;
    }

    private static ImageIcon buildIcon(Color color, String text) {
        BufferedImage image = new BufferedImage(72, 72, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 0));
        g2.fillRect(0, 0, 72, 72);
        g2.setColor(color);
        g2.fillOval(6, 6, 60, 60);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(6, 6, 60, 60);
        g2.setFont(new Font("SansSerif", Font.BOLD, 30));
        g2.drawString(text, 28, 43);
        g2.dispose();
        return new ImageIcon(image);
    }
}
