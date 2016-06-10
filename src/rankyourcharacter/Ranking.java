/*
 * Ranking
 * 
 * v1.0
 * 
 * 2016/06/11
 * 
 * Chi-En Huang
 * 
 * */
package rankyourcharacter;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Ranking implements ActionListener{
	private String[] memberName = {
			"南雲 鉄虎", "紫之 創", "真白 友也", "葵 ひなた", "高峯 翠",
			"姫宮 桃李", "仙石 忍", "天満 光", "葵 ゆうた", "朱桜 司",
			"明星 スバル", "氷鷹 北斗", "遊木 真", "神崎 颯馬", "乙狩 アドニス",
			"大神 晃牙", "朔間 凛月", "衣更 真緒", "伏見 弓弦", "鳴上 嵐",
			"天祥院 英智", "蓮巳 敬人", "羽風 薫", "瀬名 泉", "守沢 千秋",
			"鬼龍 紅郎", "日々樹 渉", "深海 奏汰", "朔間 零", "仁兎 なずな",
			"佐賀美 陣", "椚 章臣", "月永 レオ", "斎宮 宗", "影片 みか"
			};
	private String[][] relation = new String[memberName.length][memberName.length];
	private Map<String, Integer> memberNumber = new HashMap<String, Integer>();
	private List<String> currentRanking = new ArrayList<String>();
	private List<String> nextRanking = new ArrayList<String>();
	private Map<Integer, ArrayList<String>> memberList = new TreeMap<Integer, ArrayList<String>>();
	private int currentLevel = 1;
	private int nextLevel = currentLevel;
	private String leftName;
	private String rightName;
	
	private static Ranking myRanking;
	private JFrame chooseFrame;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnMiddle;
	private JFrame resultFrame;
	
	private boolean rankOver = false;
	private int count = 0;
	
	public static void main(String[] args) {
		myRanking = new Ranking();
		myRanking.init();
		myRanking.showChooseFrame();
	}
	
	private void init(){
		for(int i = 0; i < memberName.length; i++){
			memberNumber.put(memberName[i], i);
			currentRanking.add(memberName[i]);
		}
		count = currentRanking.size() * (currentRanking.size() - 1) / 2;
		leftName = currentRanking.remove(0);
		rightName = currentRanking.remove(0);
	}
	
	private void showChooseFrame(){
		chooseFrame = new JFrame();
		chooseFrame.setSize(800, 600);
		setFrameAtCenterLocation(chooseFrame);
		chooseFrame.setTitle("偶像夢幻祭角色排名");
		chooseFrame.setResizable(false);
		chooseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chooseFrame.setLayout(null);
		
		btnLeft = new JButton(leftName);
		btnLeft.setFont(new java.awt.Font("新細明體", Font.PLAIN, 24));
		btnLeft.setLocation(50, 200);
		btnLeft.setSize(200, 200);
		btnLeft.setActionCommand("ClickLeft");
		btnLeft.addActionListener(this);
		chooseFrame.add(btnLeft);
		
		btnRight = new JButton(rightName);
		btnRight.setFont(new java.awt.Font("新細明體", Font.PLAIN, 24));
		btnRight.setLocation(550, 200);
		btnRight.setSize(200, 200);
		btnRight.setActionCommand("ClickRight");
		btnRight.addActionListener(this);
		chooseFrame.add(btnRight);
		
		btnMiddle = new JButton("差不多");
		btnMiddle.setFont(new java.awt.Font("新細明體", Font.PLAIN, 24));
		btnMiddle.setLocation(325, 225);
		btnMiddle.setSize(150, 150);
		btnMiddle.setActionCommand("ClickMiddle");
		btnMiddle.addActionListener(this);
		chooseFrame.add(btnMiddle);
		
		JLabel hintText = new JLabel("請選擇您較喜歡的角色！", SwingConstants.CENTER);
		hintText.setFont(new java.awt.Font("標楷體", Font.BOLD, 24));
		hintText.setLocation(200, 50);
		hintText.setSize(400, 100);
		chooseFrame.add(hintText);
		
		chooseFrame.setVisible(true);
	}
	
	private void setFrameAtCenterLocation(JFrame frame){
		Dimension windowSize = frame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		
		int dx = centerPoint.x - windowSize.width / 2;
		int dy = centerPoint.y - windowSize.height / 2;
		frame.setLocation(dx, dy);
	}
	
	private void addMapValue(Integer key, String value){
		ArrayList<String> tempList = null;
		if(memberList.containsKey(key)){
			tempList = memberList.get(key);
			if(tempList == null){
				tempList = new ArrayList<String>();
			}
			tempList.add(value);
		}else{
			tempList = new ArrayList<String>();
			tempList.add(value);
		}
		memberList.put(key, tempList);
	}

	private void showResultFrame(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		resultFrame = new JFrame();
		resultFrame.setSize(centerPoint.x * 2, centerPoint.y * 2);
		setFrameAtCenterLocation(chooseFrame);
		resultFrame.setTitle("偶像夢幻祭角色排名結果");
		resultFrame.setResizable(false);
		resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resultFrame.setLayout(null);
		
		int halfLength = (int)(memberName.length / 2.0 + 0.5);
		int width = centerPoint.x * 2 / (halfLength + 2);
		int height = centerPoint.y * 2 / (halfLength + 2);
		int count = 0;
		for(Map.Entry<Integer, ArrayList<String>> entry : memberList.entrySet()){
			boolean isFirst = true;
			for(String name : entry.getValue()){
				count++;
				
				String result;
				if(isFirst){
					if(entry.getKey() < 10){
						result = "Rank  " + entry.getKey() + ": " + name;
					}else{
						result = "Rank " + entry.getKey() + ": " + name;
					}
					isFirst = false;
				}else{
					if(entry.getKey() < 10){
						result = "            " + name;
					}else{
						result = "             " + name;
					}
				}
				

				JLabel hintText = new JLabel(result);
				hintText.setFont(new java.awt.Font("新細明體", Font.BOLD, 18));
				if(count > halfLength){
					hintText.setLocation(centerPoint.x + width, height * (((count - 1) % halfLength) + 1));
				}else{
					hintText.setLocation(width, height * (((count - 1) % halfLength) + 1));
				}
				hintText.setSize(centerPoint.x, height);
				resultFrame.add(hintText);
			}
		}
		
		resultFrame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		int leftNumber = memberNumber.get(leftName);
		int rightNumber = memberNumber.get(rightName);
		
		count--;
		if(cmd.equals("ClickLeft")){
			relation[leftNumber][rightNumber] = "Better";
			relation[rightNumber][leftNumber] = "Worse";
			currentRanking.add(leftName);
			nextRanking.add(rightName);
		}else if(cmd.equals("ClickRight")){
			relation[leftNumber][rightNumber] = "Worse";
			relation[rightNumber][leftNumber] = "Better";
			currentRanking.add(rightName);
			nextRanking.add(leftName);
		}else if(cmd.equals("ClickMiddle")){
			relation[leftNumber][rightNumber] = "Same"; 
			relation[rightNumber][leftNumber] = "Same";
			currentRanking.add(leftName);
			currentRanking.add(rightName);
			for(int i = 0; i < memberName.length; i++){
				if(relation[leftNumber][i] != null){
					relation[rightNumber][i] = relation[leftNumber][i];
				}
				if(relation[rightNumber][i] != null){
					relation[leftNumber][i] = relation[rightNumber][i];
				}
				if(relation[i][leftNumber] != null){
					relation[i][rightNumber] = relation[i][leftNumber];
				}
				if(relation[i][rightNumber] != null){
					relation[i][leftNumber] = relation[i][rightNumber];
				}
			}
		}
		for(int i = 0; i < memberName.length; i++){
			for(int j = 0; j < memberName.length - i; j++){
				for(int k = 0; k < memberName.length; k++){
					for(int l = 0; l < memberName.length; l++){
						if(relation[j][k] != null){
							break;
						}
						if(relation[j][l] != null && relation[j][l].equals(relation[l][k])){
							relation[j][k] = relation[j][l];
						}else if(relation[j][l] != null && relation[j][l].equals("Same")){
							relation[j][k] = relation[l][k];
						}else if(relation[l][k] != null && relation[l][k].equals("Same")){
							relation[j][k] = relation[j][l];
						}
					}
				}
			}
		}
		
		if(currentRanking.size() <= 1){
			if(currentRanking.size() == 1){
				String tempName = currentRanking.remove(0);
				addMapValue(currentLevel, tempName);
				nextLevel++;
			}
			if(nextRanking.size() >= 2){
				currentRanking.addAll(nextRanking);
				count = currentRanking.size() * (currentRanking.size() - 1) / 2;
			}else{
				if(nextRanking.size() == 1){
					currentLevel = nextLevel;
					String tempName = nextRanking.remove(0);
					addMapValue(currentLevel, tempName);
					nextLevel++;
				}
					chooseFrame.setVisible(false);
					myRanking.showResultFrame();
					rankOver = true;
			}
			nextRanking.clear();
			currentLevel = nextLevel;
		}
		
		if(!rankOver){
			leftName = currentRanking.remove(0);
			rightName = currentRanking.remove(0);
			leftNumber = memberNumber.get(leftName);
			rightNumber = memberNumber.get(rightName);
			while(relation[leftNumber][rightNumber] != null){
				count--;
				if(relation[leftNumber][rightNumber].equals("Better")){
					currentRanking.add(leftName);
					nextRanking.add(rightName);
				}else if(relation[leftNumber][rightNumber].equals("Worse")){
					currentRanking.add(rightName);
					nextRanking.add(leftName);
				}else if(relation[leftNumber][rightNumber].equals("Same")){
					if(count <= 0){
						addMapValue(currentLevel, leftName);
						addMapValue(currentLevel, rightName);
						nextLevel += 2;
					}else{
						currentRanking.add(leftName);
						currentRanking.add(rightName);
					}
				}
				
				if(currentRanking.size() <= 1){
					if(currentRanking.size() == 1){
						String tempName = currentRanking.remove(0);
						addMapValue(currentLevel, tempName);
						nextLevel++;
					}
					if(nextRanking.size() >= 2){
						currentRanking.addAll(nextRanking);
						count = currentRanking.size() * (currentRanking.size() - 1) / 2;
					}else{
						if(nextRanking.size() == 1){
							currentLevel = nextLevel;
							String tempName = nextRanking.remove(0);
							addMapValue(currentLevel, tempName);
							nextLevel++;
						}
							chooseFrame.setVisible(false);
							myRanking.showResultFrame();
							rankOver = true;
							break;
					}
					nextRanking.clear();
					currentLevel = nextLevel;
				}
				leftName = currentRanking.remove(0);
				rightName = currentRanking.remove(0);
				leftNumber = memberNumber.get(leftName);
				rightNumber = memberNumber.get(rightName);
			}
			if(!rankOver){
				btnLeft.setText(leftName);
				btnRight.setText(rightName);
			}
		}
	}

}
