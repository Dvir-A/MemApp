package mmn14_2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MemFrame extends JFrame {
	private JPanel _textAndSave;
	private JButton _saveButton,_dateButton,_cleanButton;
	private File _currFile;
	private ArrayList<JComboBox<Integer>> _dateBoxes;
	private JTextArea _memArea;
	private JLabel _statusBar;
	private MemTable _memTable;
	private MemTime _time;
	private JSlider _red,_green,_blue,_size;
	private Font _font;
	JRadioButton _boldR , _italicR, _plainR; 
	
	private final int MaxYear = 2070;
	private final int MinYear = 1990;	
	private final int MaxMonth = 12;
	private final int MaxDay = 31;
	private final int MinMonth_Day = 1;	
	private final String dir = System.getProperty("user.dir");
	private final Font _italic = new Font("Italic", Font.ITALIC, 50);
	private final Font _bold = new Font("Bold", Font.BOLD, 50);
	private final Font _plain = new Font("Plain",Font.PLAIN, 50);
	
 	public static Integer[] createArr(int from,int to) {
		int len=to-from+1;
		if(len<=0) {
			return null;
		}
		Integer[] toReturn= new Integer[len];
		for(int i=from;i<=to;i++) {
			toReturn[i-from] = i;
		}
		return toReturn;
	}
	
	public MemFrame()  {
		super("MemApp");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this._memTable = new MemTable();
		this._time = new MemTime(0, 0, 0);
		
		MyListener listener = new MyListener();
		
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenu sys = new JMenu("System");
		
		JMenuItem open = new JMenuItem("Open File");
		JMenuItem _new = new JMenuItem("New File");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem help = new JMenuItem("Help");
		
		open.addActionListener(listener);
		help.addActionListener(listener);
		_new.addActionListener(listener);
		exit.addActionListener(listener);
		file.add(_new);
		file.add(open);
		sys.add(help);
		sys.add(exit);
		bar.add(file);
		bar.add(sys);
		this.setJMenuBar(bar);

		this._statusBar = new JLabel(" ");
		
		this._memArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(this._memArea);
		this._saveButton = new JButton("Save Mem");
		this._saveButton.addActionListener(listener);
		this._cleanButton = new JButton("Clean Mem");
		this._cleanButton.addActionListener(listener);
		JPanel save_cleanBP = new JPanel();
		save_cleanBP.add(this._cleanButton);
		save_cleanBP.add(this._saveButton);
		this._textAndSave = new JPanel(new BorderLayout(10,10));
		this._textAndSave.add(scroll, BorderLayout.CENTER);
		this._textAndSave.add(save_cleanBP, BorderLayout.SOUTH);
		
		_font = _italic;
		changeFontSize(12);
		_boldR = new JRadioButton("Bold");
		_italicR = new JRadioButton("Italic",true);
		_plainR = new JRadioButton("Plain");
		JPanel formatPanel = new JPanel(new GridLayout(4, 1, 10, 10));
		formatPanel.add(_plainR);
		formatPanel.add(_italicR);
		formatPanel.add(_boldR);
		ActionListener otherLis = new MyOtherLis();
		_boldR.addActionListener(otherLis);
		_italicR.addActionListener(otherLis);
		_plainR.addActionListener(otherLis);
		ButtonGroup group = new ButtonGroup();
		group.add(_boldR);
		group.add(_italicR);
		group.add(_plainR);
		ChangeListener cListener = new SliderLis();
		this._size = new JSlider(SwingConstants.VERTICAL, 12, 80, 12);
		_size.setToolTipText("Size");
		this._blue = new JSlider(SwingConstants.VERTICAL, 0, 200, 0);
		_blue.setToolTipText("Blue");
		this._red = new JSlider(SwingConstants.VERTICAL, 0, 200, 0);
		_red.setToolTipText("Red");
		this._green =new JSlider(SwingConstants.VERTICAL, 0, 200,0);
		_green.setToolTipText("Green");
		this._size.addChangeListener(cListener);
		this._blue.addChangeListener(cListener);
		this._green.addChangeListener(cListener);
		this._red.addChangeListener(cListener);
		JPanel slidersPanel = new JPanel(new GridLayout(2, 3));
		slidersPanel.add(new JLabel("Blue"));
		slidersPanel.add(new JLabel("Red"));
		slidersPanel.add(new JLabel("Grean"));
		slidersPanel.add(_blue);
		slidersPanel.add(_red);
		slidersPanel.add(_green);
		JPanel txtP = new JPanel(new GridLayout(2, 1));
		txtP.add(formatPanel);
		txtP.add(_size);
		this._textAndSave.add(slidersPanel, BorderLayout.EAST);
		this._textAndSave.add(txtP, BorderLayout.WEST);
		
		
		JPanel memPanel = new JPanel();
		this._dateBoxes = new ArrayList<JComboBox<Integer>>();
		ArrayList<JLabel> lbl = new ArrayList<JLabel>(3);
		String sArr[] = {"day :","month :","year :"};
		for(int i=0;i<3;i++) {
			lbl.add(new JLabel(sArr[i]));
			memPanel.add(lbl.get(i));
			if(i==0) {
				this._dateBoxes.add(new JComboBox<Integer>(createArr(MinMonth_Day, MaxDay)));
			}else if(i==1) {
				this._dateBoxes.add(new JComboBox<Integer>(createArr(MinMonth_Day, MaxMonth)));
			}else {
				this._dateBoxes.add(new JComboBox<Integer>(createArr(MinYear, MaxYear)));
			}
			memPanel.add(this._dateBoxes.get(i));
		}
		
		
		JPanel datePanel = new JPanel(new BorderLayout(10, 10));
		this._dateButton = new JButton("Choose Date Mem");
		datePanel.add(memPanel, BorderLayout.CENTER);
		this._dateButton.addActionListener(listener);
		datePanel.add(this._dateButton, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout(10,10));
		this.add(datePanel,BorderLayout.NORTH);
		this.add(this._textAndSave,BorderLayout.CENTER);
		this.add(this._statusBar,BorderLayout.SOUTH);
		this.setSize(new Dimension(600, 600));
		this._dateButton.setSize(20, this._dateButton.getHeight());
		this.setVisible(true);
	}
	
	
	private class SliderLis implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent chengeEv) {
			changeFontSize(_size.getValue());
			_memArea.setFont(_font);
			_memArea.setForeground(new Color(_red.getValue(), _green.getValue(), _blue.getValue()));
		}
	}
	
	private void changeFontSize(int newSize) {
		this._font = new Font(_font.getName(),_font.getStyle(),newSize);
		this._memArea.setFont(_font);
	}
	
	private class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.compareTo("Save Mem")==0) {
				save();
				_statusBar.setText("This mem saved at : "+_currFile.getAbsolutePath());
				return;
			}else if(cmd.compareTo("Exit")==0) {
				System.exit(0);
			}else if(cmd.compareTo("Clean Mem")==0 ) {
				_memArea.setText(" \n");
				return;
			}else if(cmd.compareTo("Open File")==0){
				final JFileChooser fileChooser = new JFileChooser(dir);
				fileChooser.setVisible(true);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int res = fileChooser.showOpenDialog(null);
				if(res==JFileChooser.CANCEL_OPTION) {
					_statusBar.setText("No mem choosen, working on this mem.");
					return;
				}
				_currFile = fileChooser.getSelectedFile();
				if(read()) {
					_statusBar.setText("The file "+_currFile.getName()+"  open succesfully");
				}
				return;
			}else if(cmd.compareTo("Help")==0) {
				//pop up help from file help.txt
				
			}else if(cmd.compareTo("Choose Date Mem")==0) {
				_time=new MemTime((int)_dateBoxes.get(0).getSelectedItem(), (int)_dateBoxes.get(1).getSelectedItem(),(int) _dateBoxes.get(2).getSelectedItem());
				if(_memTable.containsKey(_time)) {
					_memArea.setText(_memTable.get(_time));
					_statusBar.setText("The date "+_time.toString()+" loaded");
				}else {
					_memArea.setText("");
					_statusBar.setText("NO record of "+_time.toString()+" starting a new one.");
				}
			}else if(cmd.compareTo("New File")==0) {
				_currFile = new File(dir+"/"+Math.random()+".txt");
				_time = new MemTime(0, 0, 0);
				_memTable = new MemTable();
				_memArea.setText(" ");
				_statusBar.setText("New file has been opened.");
			}
			
		}
		
	}

	private class MyOtherLis implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int size = _font.getSize();
			if(e.getActionCommand().compareTo("Plain")==0) {
				_font = _plain;
			}else if(e.getActionCommand().compareTo("Bold")==0){
				_font = _bold;
			}else if(e.getActionCommand().compareTo("Italic")==0){
				_font = _italic;
			}
			changeFontSize(size);	
		}
				
	}

 	private void save() {
		ObjectOutputStream out;
		if(this._currFile==null || !this._currFile.exists()) {
			this._currFile = new File(dir+"/memFile"+Math.random()+".txt");
		}
		if(_memTable.containsKey(_time)) {
			_memTable.remove(_time);
		}
		this._memTable.put(new MemTime(_time.get_day(), _time.get_month(), _time.get_year()), new String(this._memArea.getText()));
		try {
			out = new ObjectOutputStream(new FileOutputStream(_currFile));
			out.writeObject(new MemTable(_memTable));
			out.close();
		} catch (IOException iOe) {
			JOptionPane.showMessageDialog(null, "Can't save file,terminating", "Error in attemp to open file", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	private boolean read() {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream(_currFile));
			_memTable = new MemTable((MemTable) in.readObject());
			in.close();
		} catch (IOException | ClassNotFoundException   e) {
			JOptionPane.showMessageDialog(null, "Error ,can't read from file\nPlease Choose another", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} 
		return true;
	}
	
	


	public static void main(String args[]) {
		MemFrame mem= new MemFrame();
		mem.setPreferredSize(new Dimension(600, 600));
	}
	
	
}
