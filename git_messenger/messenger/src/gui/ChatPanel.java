package to.uk.gagandeepbali.swing.messenger.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class ChatPanel extends JPanel
{
	private JButton backButton;
	private JButton exitButton;
	private JButton sendButton;
	
	private JTextPane chatPane;
	private JTextPane namePane;
	private JTextField chatField;
	
	private GridBagConstraints gbc;
	
	private final int GAP = 10;
	private final int SMALLGAP = 1;
	
	public ChatPanel()
	{
		gbc = new GridBagConstraints();
	}
	
	protected void createGUI()
	{
		setOpaque(true);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(5, 5));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(true);
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, 0, GAP));
		centerPanel.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 5;
		gbc.weightx = 0.8;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		chatPane = new JTextPane();
		JScrollPane scrollerChat = new JScrollPane();
		scrollerChat.setBorder(BorderFactory.createTitledBorder("Chat"));
		scrollerChat.setViewportView(chatPane);
		centerPanel.add(scrollerChat, gbc);
		
		gbc.gridx = 5;
		gbc.gridwidth = 2;
		gbc.weightx = 0.2;
		namePane = new JTextPane();
		JScrollPane scrollerName = new JScrollPane(namePane);
		scrollerName.setBorder(BorderFactory.createTitledBorder("Names"));
		centerPanel.add(scrollerName, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 5;
		gbc.weightx = 0.8;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		chatField = new JTextField();
		chatField.setName("Chat");
		chatField.setText("Enter Chat Messages here...");
		chatField.setOpaque(true);
		chatField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("")
				, BorderFactory.createEmptyBorder(SMALLGAP, SMALLGAP, SMALLGAP, SMALLGAP)));
		centerPanel.add(chatField, gbc);
		
		gbc.gridx = 5;
		gbc.gridwidth = 2;
		gbc.weightx = 0.2;
		sendButton = new JButton("Send");
		sendButton.setBorder(BorderFactory.createTitledBorder(""));
		centerPanel.add(sendButton, gbc);		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(true);
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setBorder(
				BorderFactory.createTitledBorder(""));
		bottomPanel.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setOpaque(true);
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, 0, GAP));
		backButton = new JButton("Back");
		exitButton = new JButton("Exit");
		buttonPanel.add(backButton);
		buttonPanel.add(exitButton);
		bottomPanel.add(buttonPanel, BorderLayout.CENTER);
		
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	public JTextPane getChatPane()
	{
		return chatPane;
	}
	
	public JTextPane getNamePane()
	{
		return namePane;
	}
	
	public JTextField getChatField()
	{
		return chatField;
	}
	
	public JButton getExitButton()
	{
		return exitButton;
	}	
	
	public JButton getBackButton()
	{
		return backButton;
	}
	
	public JButton getSendButton()
	{
		return sendButton;
	}
}