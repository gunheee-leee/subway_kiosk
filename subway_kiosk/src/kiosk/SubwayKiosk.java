package kiosk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

class MenuItem { // �޴� Ŭ���� ����
    private String name;
    private int price;

    public MenuItem(String name, int price) { // �̸�, ���� ������
        this.name = name;
        this.price = price;
    }

    public String getName() { // �̸� ���� �Լ�
        return name;
    }

    public int getPrice() { // ���� ���� �Լ�
        return price;
    }
}

public class SubwayKiosk extends JFrame {

    private JTextArea cartTextArea;
    private int totalAmount;
    private Map<String, Integer> selectedItems;
    
    private JTable cartTable;
    private DefaultTableModel tableModel;
    
    // ������ġ �޴�, ������ ������ �ʴ� ������ static final�� �����ϸ� �ڵ��� �������� ����, �׸��� ���� ������ ���� ��, �� �κи� �ٲ��ָ� �Ǿ ���� ������ ����
    private static final MenuItem HAM_SANDWICH_ITEM = new MenuItem("��", 5800);
    private static final MenuItem EGG_MAYO_SANDWICH_ITEM = new MenuItem("���׸���", 4900);
    private static final MenuItem SHRIMP_SANDWICH_ITEM = new MenuItem("������", 6200);
    private static final MenuItem VEGGIE_SANDWICH_ITEM = new MenuItem("����", 4400);

    // ������ �޴�
    private static final MenuItem BBQ_SALAD_ITEM = new MenuItem("�ٺ�ť", 5700);
    private static final MenuItem CLUB_SALAD_ITEM = new MenuItem("Ŭ��", 5300);
    private static final MenuItem BELT_SALAD_ITEM = new MenuItem("��Ƽ", 5200);
    private static final MenuItem TUNA_SALAD_ITEM = new MenuItem("��ġ", 4800);

    // ���� �޴�
    private static final MenuItem COLA_DRINK_ITEM = new MenuItem("�ݶ�", 2000);
    private static final MenuItem ZERO_COLA_DRINK_ITEM = new MenuItem("�����ݶ�", 2000);
    private static final MenuItem CIDER_DRINK_ITEM = new MenuItem("���̴�", 2000);
    private static final MenuItem FANTA_DRINK_ITEM = new MenuItem("ȯŸ", 2000);

    // ��Ű �޴�
    private static final MenuItem CHOCO_CHIP_COOKIE_ITEM = new MenuItem("����Ĩ", 1500);
    private static final MenuItem MACADAMIA_COOKIE_ITEM = new MenuItem("��ī�ٹ̾�", 1500);
    private static final MenuItem OATMEAL_COOKIE_ITEM = new MenuItem("��Ʈ��", 1500);
    private static final MenuItem RASPBERRY_COOKIE_ITEM = new MenuItem("�����", 1500);

    public SubwayKiosk() {
        setTitle("Subway Kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 700);
        
        String[] columnNames = {"��ǰ��", "����", "�ݾ�"};
        tableModel = new DefaultTableModel(columnNames, 0); // ��ٱ��� ���̺� ����
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // ��ü ���̾ƿ� ����
        setLayout(new BorderLayout());

        // ��ǥ�� ǥ�� ���̺�
        String logoImagePath = "C:\\Users\\82102\\Desktop\\���б�\\3_2\\programming2\\image\\��ũ�ΰ�.png"; // ������� �ΰ�
        ImageIcon resizedLogoIcon = createResizedImageIcon(logoImagePath, 390, 50);
        JLabel brandLabel = new JLabel(resizedLogoIcon, SwingConstants.CENTER);
        
        // ��ǥ��� �г�
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(brandLabel, BorderLayout.NORTH);

        // �޴� �� �г�
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("������ġ", createPanel(HAM_SANDWICH_ITEM, EGG_MAYO_SANDWICH_ITEM, SHRIMP_SANDWICH_ITEM, VEGGIE_SANDWICH_ITEM));
        tabbedPane.addTab("������", createPanel(BBQ_SALAD_ITEM, CLUB_SALAD_ITEM, BELT_SALAD_ITEM, TUNA_SALAD_ITEM));
        tabbedPane.addTab("����", createPanel(COLA_DRINK_ITEM, ZERO_COLA_DRINK_ITEM, CIDER_DRINK_ITEM, FANTA_DRINK_ITEM));
        tabbedPane.addTab("��Ű", createPanel(CHOCO_CHIP_COOKIE_ITEM, MACADAMIA_COOKIE_ITEM, OATMEAL_COOKIE_ITEM, RASPBERRY_COOKIE_ITEM));
        
        JPanel examplePanel = new JPanel(new FlowLayout());
        examplePanel.add(new JLabel("CHANGE COLOR"));

        JSlider colorSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
        colorSlider.setMajorTickSpacing(50);
        colorSlider.setPaintTicks(true);

        // ChangeListener�� �߰��Ͽ� �����̴� �� ���� �� ������ ó��
        colorSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = colorSlider.getValue();
                examplePanel.setBackground(new Color(value, value, value));
            }
        });

        // JSlider�� examplePanel�� ���� �߰�
        examplePanel.setLayout(new BorderLayout());
        examplePanel.add(new JLabel("CHANGE COLOR"), BorderLayout.NORTH);
        examplePanel.add(colorSlider, BorderLayout.CENTER);

        // ���� �ǿ� examplePanel�� �߰�
        tabbedPane.addTab("����", examplePanel);
        
        headerPanel.add(tabbedPane, BorderLayout.CENTER);
        JScrollPane scrollPane2 = new JScrollPane(headerPanel);
        
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane2, BorderLayout.NORTH);
		
        // ��ٱ��� ǥ�� ����
        cartTextArea = new JTextArea();
        add(scrollPane, BorderLayout.CENTER);

        // ���� �� �ʱ�ȭ ��ư �г�
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton checkoutButton = new JButton("�����ϱ�");
        JButton decreaseButton = new JButton("���̱�");
        JButton clearButton = new JButton("��ٱ��� �ʱ�ȭ");
        
        buttonPanel.add(checkoutButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(decreaseButton);
        
        add(buttonPanel, BorderLayout.SOUTH);

        selectedItems = new HashMap<>(); // Ű�� �� �ν����� �����ϴ� �ڷᱸ��, ��ǰ��� ������ Ű�� ������ ��

        // �� ī�װ� ��ư�� ���� �׼� ����
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ���� ���� �߰�
                // ������ �޴��� ���� ������ ����Ͽ� ���� ����
            	int result = showPaymentDialog(); // �����Ͻðڽ��ϱ� â�� ���
            	if (result == JOptionPane.YES_OPTION) { // ���� ������ displayReceipt�� �Ѿ
                    // Ȯ�� ��ư�� ������ ���� ����
                    displayReceipt();}
            }
        });

        clearButton.addActionListener(new ActionListener() { // ��ٱ��� �ʱ�ȭ ��ư ������ ���� �׼Ǹ�����
            @Override
            public void actionPerformed(ActionEvent e) {
                // ��ٱ��� �ʱ�ȭ ���� �߰�
                cartTextArea.setText(""); // ��ٱ��ϸ� ���
                totalAmount = 0; // ���� 0���� ����
                selectedItems.clear(); // item ���
                clearCart(); // ��ٱ��� ���
            }
        });
        
        decreaseButton.addActionListener(new ActionListener() { // ���̱� ��ư�� ������ ���� �׼Ǹ�����
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDecreaseButton();
            }
        });
    }
    
    private void handleDecreaseButton() { 
        int selectedRow = cartTable.getSelectedRow();

        if (selectedRow != -1) { // ���� ���õǾ����� Ȯ��
            String itemName = (String) cartTable.getValueAt(selectedRow, 0);
            int currentQuantity = selectedItems.get(itemName);

            if (currentQuantity > 1) { // 1�� �̻��̸� �� �ϳ��� ���̰� ��
                selectedItems.put(itemName, currentQuantity - 1);
                totalAmount -= getItemPrice(itemName);

                // decreaseButton�� Ŭ���Ǿ��� �� ǥ ������Ʈ
                updateCartTable();
            } else {
                // ������ 1�� ��� �ش� �޴��� ��ٱ��Ͽ��� ����
                selectedItems.remove(itemName);
                totalAmount -= getItemPrice(itemName);

                // decreaseButton�� Ŭ���Ǿ��� �� ǥ ������Ʈ
                updateCartTable();
            }
        }
        // ����ڿ��� Ư���� �˸��� �ʿ����� �ʴٸ� �� �κ��� �����ص� �˴ϴ�.
    }
    
    private int showPaymentDialog() { // �����Ͻðڽ��ϱ� â
        Object[] options = {"Ȯ��", "���"};
        return JOptionPane.showOptionDialog(
                this,
                "������ �����Ͻðڽ��ϱ�?",
                "���� Ȯ��",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }
    
    private JPanel createPanel(MenuItem... items) { // �޴� �������� ���� ���ڸ� �� ���� ���� ����, ��ư �����
        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10)); // ���� 3���� ����, ���� ���� ����, ���� ���� ���� 10
        for (MenuItem item : items) {
            addButton(panel, item);
        }
        return panel;
    }
    
    private void clearCart() {
        cartTextArea.setText("");
        totalAmount = 0;
        selectedItems.clear();
        
        // �߰�: ǥ �ʱ�ȭ
        updateCartTable();
    }
    
    private String getImagePath(String itemName) { // �̹��� ��� ����
        // �̹��� ������ "C:\Users\82102\Desktop\3_2\programming2\image" ���� ���� �ִٰ� ����
        return "C:\\Users\\82102\\Desktop\\���б�\\3_2\\programming2\\image\\" + itemName + ".png";
    }
    
    private ImageIcon createResizedImageIcon(String imagePath, int newWidth, int newHeight) { // �̹��� ������ ���� function
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    
    private void addButton(JPanel panel, MenuItem menuItem) { // button �߰�
    	
    	String imagePath = getImagePath(menuItem.getName()); // �̹��� ���
    	ImageIcon resizedIcon = createResizedImageIcon(imagePath, 75, 75); // ���ϴ� ũ��� ����
    	
    	JButton button = new JButton(menuItem.getName(), resizedIcon);
    	button.setText("<html><center>" + menuItem.getName() + "<br>$" + menuItem.getPrice() + "</center></html>"); // html �������� �ؽ�Ʈ �߰��� ��ġ
    	button.setVerticalTextPosition(SwingConstants.BOTTOM); // ���ڴ� �Ʒ���
        button.setHorizontalTextPosition(SwingConstants.CENTER); // ��� ����


        panel.add(button);
        // ��ư�� ���� �׼� ����
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // �޴� ���� ���� �߰�
                handleMenuSelection(menuItem); // ������ �� ���õǵ��� ��
            }
        });
    }

    private void handleMenuSelection(MenuItem menuItem) {
        String selectedMenu = menuItem.getName(); // ������ �޴�
        int price = menuItem.getPrice(); // �ش� �޴��� ����

        // ������ �޴� ���� ����
        selectedItems.put(selectedMenu, selectedItems.getOrDefault(selectedMenu, 0) + 1);

        // ��ٱ��Ͽ� �� ���� ǥ��
        totalAmount += price;

        
        updateCartTable();
        
        
        // ��ٱ��Ͽ� �޴� ���� �߰� (���� �� �� �ݾ׸� ������Ʈ)
        cartTextArea.setText("");
        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) { // Map���� �������� �̸��� ������ �����ͼ� �� ������ ����� ��, cartTextArea�� �߰�
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * getItemPrice(itemName);

            cartTextArea.append(itemName + " x " + quantity + " - $" + itemTotal + "\n");
        }
        cartTextArea.append("\n�� ����: $" + totalAmount);
    }
    
    
    private void updateCartTable() {
        // ���� ��ٱ��� �����͸� ǥ�� ������Ʈ
        tableModel.setRowCount(0); // ǥ �ʱ�ȭ

        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * getItemPrice(itemName);

            // ǥ�� �� �߰�
            tableModel.addRow(new Object[]{itemName, quantity, itemTotal});
        }
     // ǥ�� ������ �࿡ �� ���� �߰�
        tableModel.addRow(new Object[]{"�� ����", "", totalAmount});
    }
    

    private void displayReceipt() { // ������ ��� �޼ҵ�
        // ���� ���� ǥ�� ������ ������ �����ϰ� ����
        StringBuilder receipt = new StringBuilder("�ֹ� ����:\n");

        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * getItemPrice(itemName);

            receipt.append(itemName).append(" x ").append(quantity).append(" - $").append(itemTotal).append("\n");
        }

        //�߰�
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(currentDate);

        // CSV ���Ͽ� ������ ���
        String desktopPath = "C:/Users/82102/Desktop/";
        String filePath = desktopPath + "Receipt_.csv";
        boolean appendHeader = !new File(filePath).exists();

        try (FileWriter csvWriter = new FileWriter(filePath, true)) {
            if (appendHeader) {
                csvWriter.append("��¥,��ǰ��,����,�ݾ�\n");
            }

            for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
                String itemName = entry.getKey();
                int quantity = entry.getValue();
                int itemTotal = quantity * getItemPrice(itemName);

                csvWriter.append(currentDateTime).append(",").append(itemName).append(",").append(Integer.toString(quantity)).append(",").append(Integer.toString(itemTotal)).append("\n");
            }

            System.out.println("�������� CSV ���Ϸ� ����Ǿ����ϴ�.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        receipt.append("\n�� ����: $").append(totalAmount);
        int result = JOptionPane.showOptionDialog(
                this,
                receipt.toString(),
                "�ֹ� ����",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"OK"},
                "OK"
        );
        if (result == JOptionPane.YES_OPTION) {
            // "�������� ����Ͻðڽ��ϱ�?"�� �ٽ� ���� ���̾�α� ǥ��
            int printReceiptResult = JOptionPane.showConfirmDialog(
                    this,
                    "�������� ����Ͻðڽ��ϱ�?",
                    "������ ���",
                    JOptionPane.YES_NO_OPTION
            );

            if (printReceiptResult == JOptionPane.YES_OPTION) {
                // �߰� ����: ���⿡ ������ ��� ������ �߰� (���� ���, ���Ͽ� ���� �Ǵ� �����ͷ� ��� ��)
                System.out.println("������ ���"); // ���� ��� ������ ���⿡ �߰�

                // ���� �ð��� �ֹ� ������ ��Ÿ���� â ǥ��
                displayOrderInformation();
            } else {
                // No�� �����ϸ� "������ �Ϸ�Ǿ����ϴ�" â�� ǥ��
                JOptionPane.showMessageDialog(
                        this,
                        "������ �Ϸ�Ǿ����ϴ�",
                        "���� �Ϸ�",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            // No�� �����ϸ� "������ �Ϸ�Ǿ����ϴ�" â�� ǥ��
            JOptionPane.showMessageDialog(
                    this,
                    "������ �Ϸ�Ǿ����ϴ�",
                    "���� �Ϸ�",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    private void displayOrderInformation() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        StringBuilder orderInformation = new StringBuilder(
            "<html><head>" +
            "<style>" +
            "body { font-family: 'Arial', sans-serif; }" + // ��Ʈ ����
            ".receipt { width: 200px; margin: 20px auto; border: 1px solid #ccc; padding: 10px; }" + // width : ������ �ʺ�, margin : ��� ����, border : �׵θ� �߰�, padding : ���� ���� ����
            ".header { text-align: center; margin-bottom: 10px; }" + // ��� �ؽ�Ʈ ��� ����, margin bottom : �Ʒ� ����
            ".item { margin-bottom: 5px; display: flex;}" + // margin bottom : �� �׸� ����, display : �׸��� ���η� ����
            ".item-info { text-align: left; }" + // ��ǰ ���� ����
            ".item-price { text-align: right; }" + // ���� ������ ����
            ".total { margin-top: 10px; text-align: right; }" +
            "</style>" +
            "</head><body>" +
            "<div class='receipt'>" +
            "<div class='header'><h2>������</h2></div>" +
            "<div><b>�ֹ� �ð�:</b> " + currentDateTime + "</div>");

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        currencyFormat.setCurrency(Currency.getInstance("KRW")); // ��ȭ ����
        
        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * getItemPrice(itemName);

            orderInformation.append(
                "<div class='item'>" +
                "<div class='item-info'><b>" + itemName + "</b> x " + quantity + "</div>" +
                "<div class='item-price'><span>" + currencyFormat.format(itemTotal) + "</span></div>" +
                "</div>"
            );
        }

        orderInformation.append(
            "<div class='total'><b style='color:black;'>�� ����:</b> " +
            "<span style='font-size:10px;'>" + currencyFormat.format(totalAmount) + "</span></div>" +
            "</div></body></html>"
        );

        JOptionPane.showMessageDialog(
            this,
            orderInformation.toString(),
            "������ ���",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private int getItemPrice(String itemName) {
        switch (itemName) {
            case "��":
                return HAM_SANDWICH_ITEM.getPrice();
            case "���׸���":
                return EGG_MAYO_SANDWICH_ITEM.getPrice();
            case "������":
                return SHRIMP_SANDWICH_ITEM.getPrice();
            case "����":
                return VEGGIE_SANDWICH_ITEM.getPrice();
            case "�ٺ�ť":
                return BBQ_SALAD_ITEM.getPrice();
            case "Ŭ��":
                return CLUB_SALAD_ITEM.getPrice();
            case "��Ƽ":
                return BELT_SALAD_ITEM.getPrice();
            case "��ġ":
                return TUNA_SALAD_ITEM.getPrice();
            case "�ݶ�":
                return COLA_DRINK_ITEM.getPrice();
            case "�����ݶ�":
                return ZERO_COLA_DRINK_ITEM.getPrice();
            case "���̴�":
                return CIDER_DRINK_ITEM.getPrice();
            case "ȯŸ":
                return FANTA_DRINK_ITEM.getPrice();
            case "����Ĩ":
                return CHOCO_CHIP_COOKIE_ITEM.getPrice();
            case "��ī�ٹ̾�":
                return MACADAMIA_COOKIE_ITEM.getPrice();
            case "��Ʈ��":
                return OATMEAL_COOKIE_ITEM.getPrice();
            case "�����":
                return RASPBERRY_COOKIE_ITEM.getPrice();
            default:
                return 0;
        }
    }
    
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(new Runnable() {
          //  @Override
            //public void run() {
            	adPanel ad = new adPanel();
            //}
        //});
    }
}