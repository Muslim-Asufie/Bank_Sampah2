import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class KategoriRowRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Object valKategori = table.getValueAt(row, 1);
        
        if (valKategori != null) {
            String kategori = valKategori.toString();
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else {
                switch (kategori) {
                    case "Organik":
                        c.setBackground(new Color(225, 245, 225));
                        c.setForeground(Color.BLACK);
                        break;
                    case "Anorganik":
                        c.setBackground(new Color(225, 238, 255));
                        c.setForeground(Color.BLACK);
                        break;
                    case "B3":
                        c.setBackground(new Color(255, 225, 225));
                        c.setForeground(Color.BLACK);
                        break;
                    default:
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                        break;
                }
            }
        }
        return c;
    }
}