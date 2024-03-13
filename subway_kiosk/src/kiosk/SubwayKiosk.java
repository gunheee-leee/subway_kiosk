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

class MenuItem { // 메뉴 클래스 생성
    private String name;
    private int price;

    public MenuItem(String name, int price) { // 이름, 가격 생성자
        this.name = name;
        this.price = price;
    }

    public String getName() { // 이름 리턴 함수
        return name;
    }

    public int getPrice() { // 가격 리턴 함수
        return price;
    }
}

public class SubwayKiosk extends JFrame {

    private JTextArea cartTextArea;
    private int totalAmount;
    private Map<String, Integer> selectedItems;
    
    private JTable cartTable;
    private DefaultTableModel tableModel;
    
    // 샌드위치 메뉴, 가격은 변하지 않는 값으로 static final로 지정하면 코드의 안정성을 높임, 그리고 가격 변동이 있을 때, 이 부분만 바꿔주면 되어서 유지 보수가 쉬움
    private static final MenuItem HAM_SANDWICH_ITEM = new MenuItem("햄", 5800);
    private static final MenuItem EGG_MAYO_SANDWICH_ITEM = new MenuItem("에그마요", 4900);
    private static final MenuItem SHRIMP_SANDWICH_ITEM = new MenuItem("쉬림프", 6200);
    private static final MenuItem VEGGIE_SANDWICH_ITEM = new MenuItem("베지", 4400);

    // 샐러드 메뉴
    private static final MenuItem BBQ_SALAD_ITEM = new MenuItem("바비큐", 5700);
    private static final MenuItem CLUB_SALAD_ITEM = new MenuItem("클럽", 5300);
    private static final MenuItem BELT_SALAD_ITEM = new MenuItem("비엘티", 5200);
    private static final MenuItem TUNA_SALAD_ITEM = new MenuItem("참치", 4800);

    // 음료 메뉴
    private static final MenuItem COLA_DRINK_ITEM = new MenuItem("콜라", 2000);
    private static final MenuItem ZERO_COLA_DRINK_ITEM = new MenuItem("제로콜라", 2000);
    private static final MenuItem CIDER_DRINK_ITEM = new MenuItem("사이다", 2000);
    private static final MenuItem FANTA_DRINK_ITEM = new MenuItem("환타", 2000);

    // 쿠키 메뉴
    private static final MenuItem CHOCO_CHIP_COOKIE_ITEM = new MenuItem("초코칩", 1500);
    private static final MenuItem MACADAMIA_COOKIE_ITEM = new MenuItem("마카다미아", 1500);
    private static final MenuItem OATMEAL_COOKIE_ITEM = new MenuItem("오트밀", 1500);
    private static final MenuItem RASPBERRY_COOKIE_ITEM = new MenuItem("라즈베리", 1500);

    public SubwayKiosk() {
        setTitle("Subway Kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 700);
        
        String[] columnNames = {"상품명", "수량", "금액"};
        tableModel = new DefaultTableModel(columnNames, 0); // 장바구니 테이블 생성
        cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // 전체 레이아웃 설정
        setLayout(new BorderLayout());

        // 상표명 표시 레이블
        String logoImagePath = "C:\\Users\\82102\\Desktop\\대학교\\3_2\\programming2\\image\\마크로고.png"; // 서브웨이 로고
        ImageIcon resizedLogoIcon = createResizedImageIcon(logoImagePath, 390, 50);
        JLabel brandLabel = new JLabel(resizedLogoIcon, SwingConstants.CENTER);
        
        // 상표명과 패널
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(brandLabel, BorderLayout.NORTH);

        // 메뉴 탭 패널
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("샌드위치", createPanel(HAM_SANDWICH_ITEM, EGG_MAYO_SANDWICH_ITEM, SHRIMP_SANDWICH_ITEM, VEGGIE_SANDWICH_ITEM));
        tabbedPane.addTab("샐러드", createPanel(BBQ_SALAD_ITEM, CLUB_SALAD_ITEM, BELT_SALAD_ITEM, TUNA_SALAD_ITEM));
        tabbedPane.addTab("음료", createPanel(COLA_DRINK_ITEM, ZERO_COLA_DRINK_ITEM, CIDER_DRINK_ITEM, FANTA_DRINK_ITEM));
        tabbedPane.addTab("쿠키", createPanel(CHOCO_CHIP_COOKIE_ITEM, MACADAMIA_COOKIE_ITEM, OATMEAL_COOKIE_ITEM, RASPBERRY_COOKIE_ITEM));
        
        JPanel examplePanel = new JPanel(new FlowLayout());
        examplePanel.add(new JLabel("CHANGE COLOR"));

        JSlider colorSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
        colorSlider.setMajorTickSpacing(50);
        colorSlider.setPaintTicks(true);

        // ChangeListener를 추가하여 슬라이더 값 변경 시 동작을 처리
        colorSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = colorSlider.getValue();
                examplePanel.setBackground(new Color(value, value, value));
            }
        });

        // JSlider를 examplePanel에 직접 추가
        examplePanel.setLayout(new BorderLayout());
        examplePanel.add(new JLabel("CHANGE COLOR"), BorderLayout.NORTH);
        examplePanel.add(colorSlider, BorderLayout.CENTER);

        // 예제 탭에 examplePanel을 추가
        tabbedPane.addTab("예제", examplePanel);
        
        headerPanel.add(tabbedPane, BorderLayout.CENTER);
        JScrollPane scrollPane2 = new JScrollPane(headerPanel);
        
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane2, BorderLayout.NORTH);
		
        // 장바구니 표시 영역
        cartTextArea = new JTextArea();
        add(scrollPane, BorderLayout.CENTER);

        // 결제 및 초기화 버튼 패널
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JButton checkoutButton = new JButton("결제하기");
        JButton decreaseButton = new JButton("줄이기");
        JButton clearButton = new JButton("장바구니 초기화");
        
        buttonPanel.add(checkoutButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(decreaseButton);
        
        add(buttonPanel, BorderLayout.SOUTH);

        selectedItems = new HashMap<>(); // 키와 값 두쌍으로 저장하는 자료구조, 제품명과 가격이 키와 값으로 들어감

        // 각 카테고리 버튼에 대한 액션 설정
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 결제 로직 추가
                // 선택한 메뉴에 대한 정보를 사용하여 결제 진행
            	int result = showPaymentDialog(); // 결제하시겠습니까 창을 띄움
            	if (result == JOptionPane.YES_OPTION) { // 예스 누르면 displayReceipt로 넘어감
                    // 확인 버튼이 눌렸을 때의 동작
                    displayReceipt();}
            }
        });

        clearButton.addActionListener(new ActionListener() { // 장바구니 초기화 버튼 눌렀을 때의 액션리스너
            @Override
            public void actionPerformed(ActionEvent e) {
                // 장바구니 초기화 로직 추가
                cartTextArea.setText(""); // 장바구니를 비움
                totalAmount = 0; // 수량 0으로 설정
                selectedItems.clear(); // item 비움
                clearCart(); // 장바구니 비움
            }
        });
        
        decreaseButton.addActionListener(new ActionListener() { // 줄이기 버튼을 눌렀을 때의 액션리스너
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDecreaseButton();
            }
        });
    }
    
    private void handleDecreaseButton() { 
        int selectedRow = cartTable.getSelectedRow();

        if (selectedRow != -1) { // 행이 선택되었는지 확인
            String itemName = (String) cartTable.getValueAt(selectedRow, 0);
            int currentQuantity = selectedItems.get(itemName);

            if (currentQuantity > 1) { // 1개 이상이면 양 하나를 줄이게 함
                selectedItems.put(itemName, currentQuantity - 1);
                totalAmount -= getItemPrice(itemName);

                // decreaseButton이 클릭되었을 때 표 업데이트
                updateCartTable();
            } else {
                // 수량이 1인 경우 해당 메뉴를 장바구니에서 제거
                selectedItems.remove(itemName);
                totalAmount -= getItemPrice(itemName);

                // decreaseButton이 클릭되었을 때 표 업데이트
                updateCartTable();
            }
        }
        // 사용자에게 특별한 알림이 필요하지 않다면 이 부분을 삭제해도 됩니다.
    }
    
    private int showPaymentDialog() { // 결제하시겠습니까 창
        Object[] options = {"확인", "취소"};
        return JOptionPane.showOptionDialog(
                this,
                "결제를 진행하시겠습니까?",
                "결제 확인",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }
    
    private JPanel createPanel(MenuItem... items) { // 메뉴 아이템의 여러 인자를 한 번에 전달 가능, 버튼 만들기
        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10)); // 열은 3열로 고정, 행은 제한 없음, 가로 세로 간격 10
        for (MenuItem item : items) {
            addButton(panel, item);
        }
        return panel;
    }
    
    private void clearCart() {
        cartTextArea.setText("");
        totalAmount = 0;
        selectedItems.clear();
        
        // 추가: 표 초기화
        updateCartTable();
    }
    
    private String getImagePath(String itemName) { // 이미지 경로 리턴
        // 이미지 파일이 "C:\Users\82102\Desktop\3_2\programming2\image" 폴더 내에 있다고 가정
        return "C:\\Users\\82102\\Desktop\\대학교\\3_2\\programming2\\image\\" + itemName + ".png";
    }
    
    private ImageIcon createResizedImageIcon(String imagePath, int newWidth, int newHeight) { // 이미지 사이즈 조절 function
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image image = originalIcon.getImage();
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    
    private void addButton(JPanel panel, MenuItem menuItem) { // button 추가
    	
    	String imagePath = getImagePath(menuItem.getName()); // 이미지 경로
    	ImageIcon resizedIcon = createResizedImageIcon(imagePath, 75, 75); // 원하는 크기로 조절
    	
    	JButton button = new JButton(menuItem.getName(), resizedIcon);
    	button.setText("<html><center>" + menuItem.getName() + "<br>$" + menuItem.getPrice() + "</center></html>"); // html 형식으로 텍스트 중간에 배치
    	button.setVerticalTextPosition(SwingConstants.BOTTOM); // 글자는 아래에
        button.setHorizontalTextPosition(SwingConstants.CENTER); // 가운데 정렬


        panel.add(button);
        // 버튼에 대한 액션 설정
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 메뉴 선택 로직 추가
                handleMenuSelection(menuItem); // 눌렀을 때 선택되도록 함
            }
        });
    }

    private void handleMenuSelection(MenuItem menuItem) {
        String selectedMenu = menuItem.getName(); // 선택한 메뉴
        int price = menuItem.getPrice(); // 해당 메뉴의 가격

        // 선택한 메뉴 수량 증가
        selectedItems.put(selectedMenu, selectedItems.getOrDefault(selectedMenu, 0) + 1);

        // 장바구니에 총 가격 표시
        totalAmount += price;

        
        updateCartTable();
        
        
        // 장바구니에 메뉴 정보 추가 (수량 및 총 금액만 업데이트)
        cartTextArea.setText("");
        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) { // Map에서 아이템의 이름과 수량을 가져와서 총 가격을 계산한 후, cartTextArea에 추가
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * getItemPrice(itemName);

            cartTextArea.append(itemName + " x " + quantity + " - $" + itemTotal + "\n");
        }
        cartTextArea.append("\n총 가격: $" + totalAmount);
    }
    
    
    private void updateCartTable() {
        // 현재 장바구니 데이터를 표에 업데이트
        tableModel.setRowCount(0); // 표 초기화

        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * getItemPrice(itemName);

            // 표에 행 추가
            tableModel.addRow(new Object[]{itemName, quantity, itemTotal});
        }
     // 표의 마지막 행에 총 가격 추가
        tableModel.addRow(new Object[]{"총 가격", "", totalAmount});
    }
    

    private void displayReceipt() { // 영수증 출력 메소드
        // 결제 정보 표시 로직은 이전과 동일하게 유지
        StringBuilder receipt = new StringBuilder("주문 내역:\n");

        for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            int itemTotal = quantity * getItemPrice(itemName);

            receipt.append(itemName).append(" x ").append(quantity).append(" - $").append(itemTotal).append("\n");
        }

        //추가
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(currentDate);

        // CSV 파일에 정보를 기록
        String desktopPath = "C:/Users/82102/Desktop/";
        String filePath = desktopPath + "Receipt_.csv";
        boolean appendHeader = !new File(filePath).exists();

        try (FileWriter csvWriter = new FileWriter(filePath, true)) {
            if (appendHeader) {
                csvWriter.append("날짜,상품명,수량,금액\n");
            }

            for (Map.Entry<String, Integer> entry : selectedItems.entrySet()) {
                String itemName = entry.getKey();
                int quantity = entry.getValue();
                int itemTotal = quantity * getItemPrice(itemName);

                csvWriter.append(currentDateTime).append(",").append(itemName).append(",").append(Integer.toString(quantity)).append(",").append(Integer.toString(itemTotal)).append("\n");
            }

            System.out.println("영수증이 CSV 파일로 저장되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        receipt.append("\n총 가격: $").append(totalAmount);
        int result = JOptionPane.showOptionDialog(
                this,
                receipt.toString(),
                "주문 내역",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"OK"},
                "OK"
        );
        if (result == JOptionPane.YES_OPTION) {
            // "영수증을 출력하시겠습니까?"를 다시 묻는 다이얼로그 표시
            int printReceiptResult = JOptionPane.showConfirmDialog(
                    this,
                    "영수증을 출력하시겠습니까?",
                    "영수증 출력",
                    JOptionPane.YES_NO_OPTION
            );

            if (printReceiptResult == JOptionPane.YES_OPTION) {
                // 추가 동작: 여기에 영수증 출력 로직을 추가 (예를 들어, 파일에 저장 또는 프린터로 출력 등)
                System.out.println("영수증 출력"); // 실제 출력 로직을 여기에 추가

                // 현재 시각과 주문 정보를 나타내는 창 표시
                displayOrderInformation();
            } else {
                // No를 선택하면 "결제가 완료되었습니다" 창을 표시
                JOptionPane.showMessageDialog(
                        this,
                        "결제가 완료되었습니다",
                        "결제 완료",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            // No를 선택하면 "결제가 완료되었습니다" 창을 표시
            JOptionPane.showMessageDialog(
                    this,
                    "결제가 완료되었습니다",
                    "결제 완료",
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
            "body { font-family: 'Arial', sans-serif; }" + // 폰트 지정
            ".receipt { width: 200px; margin: 20px auto; border: 1px solid #ccc; padding: 10px; }" + // width : 영수증 너비, margin : 가운데 정렬, border : 테두리 추가, padding : 안쪽 여백 지정
            ".header { text-align: center; margin-bottom: 10px; }" + // 헤더 텍스트 가운데 정렬, margin bottom : 아래 여백
            ".item { margin-bottom: 5px; display: flex;}" + // margin bottom : 각 항목 간격, display : 항목을 가로로 나열
            ".item-info { text-align: left; }" + // 제품 왼쪽 정렬
            ".item-price { text-align: right; }" + // 가격 오른쪽 정렬
            ".total { margin-top: 10px; text-align: right; }" +
            "</style>" +
            "</head><body>" +
            "<div class='receipt'>" +
            "<div class='header'><h2>영수증</h2></div>" +
            "<div><b>주문 시각:</b> " + currentDateTime + "</div>");

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        currencyFormat.setCurrency(Currency.getInstance("KRW")); // 원화 설정
        
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
            "<div class='total'><b style='color:black;'>총 가격:</b> " +
            "<span style='font-size:10px;'>" + currencyFormat.format(totalAmount) + "</span></div>" +
            "</div></body></html>"
        );

        JOptionPane.showMessageDialog(
            this,
            orderInformation.toString(),
            "영수증 출력",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private int getItemPrice(String itemName) {
        switch (itemName) {
            case "햄":
                return HAM_SANDWICH_ITEM.getPrice();
            case "에그마요":
                return EGG_MAYO_SANDWICH_ITEM.getPrice();
            case "쉬림프":
                return SHRIMP_SANDWICH_ITEM.getPrice();
            case "베지":
                return VEGGIE_SANDWICH_ITEM.getPrice();
            case "바비큐":
                return BBQ_SALAD_ITEM.getPrice();
            case "클럽":
                return CLUB_SALAD_ITEM.getPrice();
            case "비엘티":
                return BELT_SALAD_ITEM.getPrice();
            case "참치":
                return TUNA_SALAD_ITEM.getPrice();
            case "콜라":
                return COLA_DRINK_ITEM.getPrice();
            case "제로콜라":
                return ZERO_COLA_DRINK_ITEM.getPrice();
            case "사이다":
                return CIDER_DRINK_ITEM.getPrice();
            case "환타":
                return FANTA_DRINK_ITEM.getPrice();
            case "초코칩":
                return CHOCO_CHIP_COOKIE_ITEM.getPrice();
            case "마카다미아":
                return MACADAMIA_COOKIE_ITEM.getPrice();
            case "오트밀":
                return OATMEAL_COOKIE_ITEM.getPrice();
            case "라즈베리":
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