import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.config.cConfig;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppGui {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Restaurant App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Menu", createMenuPanel());
        tabbedPane.addTab("Transactions", createTransactionsPanel());
        tabbedPane.addTab("Total Belanja", createTotalBelanjaPanel());
        tabbedPane.addTab("Cetak Struk", createCetakStrukPanel());
        tabbedPane.addTab("Pesanan Baru", createPesananBaruPanel());

        frame.getContentPane().add(tabbedPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
        
        JButton getMenuButton = new JButton("Get Menu");
        getMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(cConfig.getMenu());
            }
        });
        panel.add(getMenuButton);

        return panel;
    }



    private static JPanel createTransactionsPanel() {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        JButton getTransactionsButton = new JButton("Get All Transactions");
        getTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(cConfig.getAllNotaResto());
            }
        });
        panel.add(getTransactionsButton);

        return panel;
    }




    private static JPanel createTotalBelanjaPanel() {
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        JButton getTotalBelanjaButton = new JButton("Get Total Belanja");
        getTotalBelanjaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(cConfig.getTotalBelanja());
            }
        });
        panel.add(getTotalBelanjaButton);

        return panel;
    }

    private static JPanel createCetakStrukPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Nama Pelanggan:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        JButton getCetakStrukButton = new JButton("Cetak Struk");
        getCetakStrukButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = nameField.getText();
                resultArea.setText(cConfig.getCetakStruk(nama));
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(getCetakStrukButton, gbc);

        return panel;
    }

    private static JPanel createPesananBaruPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
    
        JLabel nameLabel = new JLabel("Nama Pelanggan:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
    
        JTextField nameField = new JTextField(30);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);
    
        JLabel phoneLabel = new JLabel("No Handphone:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(phoneLabel, gbc);
    
        JTextField phoneField = new JTextField(30);
        // phoneField.setColumns(30);  set the number of columns for the text field
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(phoneField, gbc);
    
        // DefaultTableModel tableModel = new DefaultTableModel();
        // JTable menuTable = new JTable(tableModel);
        // tableModel.addColumn("Nomor Menu");
        // tableModel.addColumn("Nama Menu");
        // tableModel.addColumn("Harga");
    
        // JScrollPane tableScrollPane = new JScrollPane(menuTable);
        // gbc.gridx = 0;
        // gbc.gridy = 2;
        // gbc.gridwidth = 2;
        // panel.add(tableScrollPane, gbc);
    
        JButton getPesananBaruButton = new JButton("Buat Pesanan Baru");
    getPesananBaruButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nama = nameField.getText();
                int nohp = Integer.parseInt(phoneField.getText());
                cConfig.getnewcust(nama, nohp);
                cConfig.getnewOrder(nama, nohp);

                // Membuat frame baru
                JFrame frame = new JFrame("Pesanan Baru");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Menambahkan panel yang sesuai dengan fungsi getcustmenu
                JPanel pesananBaruPanel = createPesananBaruPanel(nama);
                frame.getContentPane().add(pesananBaruPanel);

                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    });
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    panel.add(getPesananBaruButton, gbc);

    return panel;
    }

    private static JPanel createPesananBaruPanel(String namaPelanggan) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
    
    
        JLabel idPesananLabel = new JLabel("ID Pemesanan:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(idPesananLabel, gbc);
    
        JTextField idPesananField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(idPesananField, gbc);
    
        JLabel jumlahLabel = new JLabel("Jumlah:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(jumlahLabel, gbc);
    
        JTextField jumlahField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(jumlahField, gbc);
    
        JButton tambahPesananButton = new JButton("Tambah Pesanan");
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);
    
        tambahPesananButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idPemesanan = Integer.parseInt(idPesananField.getText());
                    int jumlah = Integer.parseInt(jumlahField.getText());
    
                    // Panggil fungsi getcustmenu dengan nilai yang diinputkan
                    String result = cConfig.getcustmenu(namaPelanggan, idPemesanan, jumlah);
                    textArea.setText(result);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(tambahPesananButton, gbc);
    
        return panel;
    }
       
}
