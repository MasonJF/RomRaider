package enginuity.NewGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

import enginuity.NewGUI.data.DataManager;
import enginuity.NewGUI.interfaces.TuningEntity;
import enginuity.NewGUI.interfaces.TuningEntityListener;
import enginuity.NewGUI.tree.ETree;
import enginuity.NewGUI.tree.ETreeCellRenderer;
import enginuity.NewGUI.tree.ETreeNode;
import enginuity.logger.utec.impl.UtecTuningEntityImpl;
import enginuity.swing.LookAndFeelManager;

public class NewGUI extends JFrame implements ActionListener, TreeSelectionListener, TuningEntityListener{
	private static NewGUI instance;
	
	private JPanel mainJPanel = new JPanel();
	
	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu tuningEntitiesJMenu = new JMenu("Tuning Entities");
	

	private JSplitPane splitPane = new JSplitPane();
	private JDesktopPane rightDesktopPane = new JDesktopPane();
	
	private ETreeNode rootNode = new ETreeNode(ETreeNode.RESERVED_ROOT, "Enginuity");
	private ETree leftJTree = new ETree(rootNode);
	
	private NewGUI(){
		// Define which tuning entities are available
		initData();
		
		// Initialize the GUI elements
		initGui();
	}
	
	public static NewGUI getInstance(){
		if(instance == null){
			instance = new NewGUI();
		}
		
		return instance;
	}
	
	private void initData(){
		// Add supported tuning entities
		UtecTuningEntityImpl utei = new UtecTuningEntityImpl();
		
		DataManager.addTuningEntity(utei);
	}
	
	private void initGui(){
		System.out.println("Initializing GUI.");
		
		
		// Set main JFrame size
		this.setSize(800,600);
		
		
		// Setup the look and feel
		LookAndFeelManager.initLookAndFeel();
		
		
		// Setup JMenu
		Iterator tuningEntities = DataManager.getTuningEntities().iterator();
		while(tuningEntities.hasNext()){
			TuningEntity theTuningEntity = (TuningEntity)tuningEntities.next();
			JMenuItem tempItem = new JMenuItem(theTuningEntity.getName());
			tempItem.addActionListener(this);
			tuningEntitiesJMenu.add(tempItem);
		}
		this.jMenuBar.add(this.tuningEntitiesJMenu);
		this.setJMenuBar(this.jMenuBar);
		
		
		// Test internalFrames
		JInternalFrame internalTest = new JInternalFrame("Test Internal", true, true, true, true);
		internalTest.setSize(300,300);
		internalTest.setVisible(true);
		
		
		// Setup desktop pane
		rightDesktopPane.setBackground(Color.BLACK);
		rightDesktopPane.add(internalTest);
		
		
		// Setup split pane
		splitPane.setDividerLocation(200);
		splitPane.setLeftComponent(leftJTree);
		splitPane.setRightComponent(rightDesktopPane);
		
		
		// Setup main JPanel
		mainJPanel.setLayout(new BorderLayout());
		mainJPanel.add(splitPane, BorderLayout.CENTER);
		
		
		// Add everything to JFrame
		this.add(mainJPanel);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("UTEC Tuning Entity")){
			String theCommand = e.getActionCommand();
			
			
			DataManager.setCurrentTuningEntity(theCommand, this);
		}
	}
	
	private void rebuildJTree(ETreeNode treeRootNode){
		this.rootNode.removeAllChildren();
		this.rootNode.add(treeRootNode);
		this.leftJTree.updateUI();
		this.splitPane.repaint();
		
		System.out.println("Changed the tree model");
	}
	

	public void rebuildJMenuBar(Vector<JMenu> items) {
		Iterator iterator = items.iterator();
		
		this.jMenuBar.removeAll();
		jMenuBar.add(this.tuningEntitiesJMenu);
		while(iterator.hasNext()){
			JMenu tempMenu = (JMenu)iterator.next();
			jMenuBar.add(tempMenu);
		}
		this.jMenuBar.revalidate();
	}

	public void valueChanged(TreeSelectionEvent arg0) {
		
		System.out.println("Tree Node selected.");
		
	}

	public void TreeStructureChanged(ETreeNode newTreeModel) {
		this.rebuildJTree(newTreeModel);
		
	}
	
}
