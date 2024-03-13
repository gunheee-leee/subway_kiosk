package kiosk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class adPanel extends JFrame{
	
	private ImageIcon[] adimages;
    private int currentImageIndex;
    private JLabel adLabel;
	
	public adPanel() {
		super("서브웨이 키오스크");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		adimages = new ImageIcon[3];
        adimages[0] = new ImageIcon("C:/Users/82102/Desktop/대학교/3_2/programming2/image/ad1.jpg");
        adimages[1] = new ImageIcon("C:/Users/82102/Desktop/대학교/3_2/programming2/image/동국대광고.png");
        adimages[2] = new ImageIcon("C:/Users/82102/Desktop/대학교/3_2/programming2/image/동국대광고2.png");

        currentImageIndex = 0;

        Image originalImage = adimages[currentImageIndex].getImage();
        Image scaledImage = originalImage.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        
        adLabel = new JLabel(scaledImageIcon);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(adLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // 1행 2열의 그리드 레이아웃을 사용하여 버튼을 가로로 나열
        JButton takeoutButton = new JButton("포장");
        JButton dineInButton = new JButton("매장");
        Font buttonFont = new Font("맑은 고딕", Font.BOLD, 25);
        takeoutButton.setFont(buttonFont);
        dineInButton.setFont(buttonFont);
        buttonPanel.add(takeoutButton);
        buttonPanel.add(dineInButton);
        takeoutButton.setPreferredSize(new Dimension(600, 70));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        c.add(panel);
        setSize(600,500);
		setVisible(true);
		
        takeoutButton.addActionListener(new ActionListener() { // 포장 버튼 눌렀을 때 키오스크 열리도록
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubwayKiosk();
            }
        });
        
        dineInButton.addActionListener(new ActionListener() { // 포장 버튼 눌렀을 때 키오스크 열리도록
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubwayKiosk();
            }
        });
		
		Timer imageTimer = new Timer(3000, new ActionListener() { // 광고 바뀌는 타이머
            //@Override
            public void actionPerformed(ActionEvent e) {
                changeImage();
            }
        });
		
		imageTimer.start();
		
    }
	
    private void openSubwayKiosk() { // 광고창 닫고 메뉴판 띄우기
    	dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SubwayKiosk().setVisible(true);
            }
        });
    }
	
	private void changeImage() { // 이미지 인덱스
        currentImageIndex = (currentImageIndex + 1) % adimages.length; // 이미지 인덱스 값을 0, 1, 2 순환하게 만드는 코드
        Image originalImage = adimages[currentImageIndex].getImage();
        Image scaledImage = originalImage.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        adLabel.setIcon(scaledImageIcon);
    }
}