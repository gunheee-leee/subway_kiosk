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
		super("������� Ű����ũ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		
		adimages = new ImageIcon[3];
        adimages[0] = new ImageIcon("C:/Users/82102/Desktop/���б�/3_2/programming2/image/ad1.jpg");
        adimages[1] = new ImageIcon("C:/Users/82102/Desktop/���б�/3_2/programming2/image/�����뱤��.png");
        adimages[2] = new ImageIcon("C:/Users/82102/Desktop/���б�/3_2/programming2/image/�����뱤��2.png");

        currentImageIndex = 0;

        Image originalImage = adimages[currentImageIndex].getImage();
        Image scaledImage = originalImage.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        
        adLabel = new JLabel(scaledImageIcon);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(adLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // 1�� 2���� �׸��� ���̾ƿ��� ����Ͽ� ��ư�� ���η� ����
        JButton takeoutButton = new JButton("����");
        JButton dineInButton = new JButton("����");
        Font buttonFont = new Font("���� ���", Font.BOLD, 25);
        takeoutButton.setFont(buttonFont);
        dineInButton.setFont(buttonFont);
        buttonPanel.add(takeoutButton);
        buttonPanel.add(dineInButton);
        takeoutButton.setPreferredSize(new Dimension(600, 70));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        c.add(panel);
        setSize(600,500);
		setVisible(true);
		
        takeoutButton.addActionListener(new ActionListener() { // ���� ��ư ������ �� Ű����ũ ��������
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubwayKiosk();
            }
        });
        
        dineInButton.addActionListener(new ActionListener() { // ���� ��ư ������ �� Ű����ũ ��������
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubwayKiosk();
            }
        });
		
		Timer imageTimer = new Timer(3000, new ActionListener() { // ���� �ٲ�� Ÿ�̸�
            //@Override
            public void actionPerformed(ActionEvent e) {
                changeImage();
            }
        });
		
		imageTimer.start();
		
    }
	
    private void openSubwayKiosk() { // ����â �ݰ� �޴��� ����
    	dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SubwayKiosk().setVisible(true);
            }
        });
    }
	
	private void changeImage() { // �̹��� �ε���
        currentImageIndex = (currentImageIndex + 1) % adimages.length; // �̹��� �ε��� ���� 0, 1, 2 ��ȯ�ϰ� ����� �ڵ�
        Image originalImage = adimages[currentImageIndex].getImage();
        Image scaledImage = originalImage.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        adLabel.setIcon(scaledImageIcon);
    }
}