package Store;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

public class Menu extends JFrame implements ActionListener
{
	Connect db = new Connect();
	
	JLabel idWarning, note1, note2;
	JLabel idLabel, nameLabel, priceLabel, stockLabel;
	JTextField idField, nameField, priceField, stockField;
	JButton addButton, updateButton, deleteButton;
	
	// table
	JTable menuTable;
	DefaultTableModel dtm;
	JScrollPane jsp;
	
	Vector <String> columnName = new Vector<>();
	Vector <Vector <Object>> data = new Vector<Vector<Object>>();
	
	public Menu() 
	{
		// TODO Auto-generated constructor stub
		setFrame();
		getData();
		// id
		idLabel = new JLabel("Id: ");
		idLabel.setBounds(20, 50, 150, 30); //(margin-x, margin-y, width, height)
		this.add(idLabel);
		
		idField = new JTextField();
		idField.setBounds(100, 50, 200, 30);
		this.add(idField);
	
		idWarning = new JLabel("Format PD-XXX");
		idWarning.setBounds(320, 50, 150, 30); //(margin-x, margin-y, width, height)
		this.add(idWarning);
		
		// name
		nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(20, 100, 150, 30); //(margin-x, margin-y, width, height)
		this.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(100, 100, 200, 30);
		this.add(nameField);
		
		// price
		priceLabel = new JLabel("Price: ");
		priceLabel.setBounds(20, 150, 150, 30); //(margin-x, margin-y, width, height)
		this.add(priceLabel);
		
		priceField = new JTextField();
		priceField.setBounds(100, 150, 200, 30);
		this.add(priceField);
		
		// stock
		stockLabel = new JLabel("Stock: ");
		stockLabel.setBounds(20, 200, 150, 30); //(margin-x, margin-y, width, height)
		this.add(stockLabel);
		
		stockField = new JTextField();
		stockField.setBounds(100, 200, 200, 30);
		this.add(stockField);
		
		// button
		addButton = new JButton("Add");
		addButton.setBounds(20, 250, 100, 30);
		addButton.addActionListener(this);
		this.add(addButton);
		
		updateButton = new JButton("Update");
		updateButton.setBounds(150, 250, 100, 30);
		updateButton.addActionListener(this);
		this.add(updateButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(280, 250, 100, 30);
		deleteButton.addActionListener(this);
		this.add(deleteButton);
		
		// note
		note1 = new JLabel("ID field is not required to be filled when adding new menu");
		note1.setBounds(20, 300, 500, 30); //(margin-x, margin-y, width, height)
		this.add(note1);
		
		note2 = new JLabel("ID field required to be filled to know where the item should be edited");
		note2.setBounds(20, 330, 500, 30); //(margin-x, margin-y, width, height)
		this.add(note2);
		
		// table
		columnName.add("ID");
		columnName.add("Name");
		columnName.add("Price");
		columnName.add("Stock");
		
		dtm = new DefaultTableModel(data, columnName);
		menuTable = new JTable(dtm);
		jsp = new JScrollPane(menuTable);
		jsp.setBounds(20, 380, 400, 200);
		this.add(jsp);
	}
	
	public void setFrame()
	{
		this.setVisible(true);
		this.setSize(500, 500);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Registrasi");
		this.setLocationRelativeTo(null);
	}
	
	public void getData()
	{
		db.rs = db.getMenu();
		
		try 
		{
			while(db.rs.next())
			{
				Vector<Object>newRow = new Vector<>();
				newRow.add(db.rs.getString("MenuId"));
				newRow.add(db.rs.getString("MenuName"));
				newRow.add(db.rs.getInt("MenuPrice"));
				newRow.add(db.rs.getInt("MenuStock"));
				
				data.add(newRow);
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getSource() == addButton)
		{
			String id = generateRandomId();
			String name = nameField.getText();
			int price = Integer.parseInt(priceField.getText());
			int stock = Integer.parseInt(stockField.getText());
			
			db.executeInsert(id, name, price, stock);
			
			for(int i = data.size() - 1; i >= 0; i--)
			{
				dtm.removeRow(i);
			}
			
			getData();
						
			JOptionPane.showMessageDialog(this, "new menu added!");
		}
		else if(e.getSource() == updateButton)
		{
			String id = idField.getText();
			String name = nameField.getText();
			int price = Integer.parseInt(priceField.getText());
			int stock = Integer.parseInt(stockField.getText());
			
			db.executeUpdate(id, name, price, stock);
			
			for(int i = data.size() - 1; i >= 0; i--)
			{
				dtm.removeRow(i);
			}
			
			getData();
						
			JOptionPane.showMessageDialog(this, "menu updated!");
		}
		else if(e.getSource() == deleteButton)
		{
			int index = menuTable.getSelectedRow();
			
			System.out.println(index);
			
			String id = String.valueOf(dtm.getValueAt(index, 0));
			
			db.executeDelete(id);
			
			JOptionPane.showMessageDialog(this, "menu deleted!");
			
			dtm.removeRow(index);
		}
	}
	
	public String generateRandomId()
	{
		// TODO Auto-generated constructor stub
		Random rand = new Random();
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numbers = "1234567890";
		String uniqueId = "";
				
		int idLength = 6;
		
		char[] id = new char[7];
				
		id[0] = 'P';
		id[1] = 'D';
		id[2] = '-';
		for(int j=3; j<6; j++)
		{
			id[j] = numbers.charAt(rand.nextInt(numbers.length()));
		}
				
		for(int k=0; k<idLength; k++)
		{
			uniqueId += id[k];
		}
		
		return uniqueId;
	}

}
