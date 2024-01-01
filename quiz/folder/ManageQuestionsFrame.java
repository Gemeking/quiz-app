package folder;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageQuestionsFrame extends JFrame {
    public ManageQuestionsFrame() {
        setTitle("Manage Questions");
        setSize(400, 300);

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(null);

        JButton editButton = new JButton("Edit Question");
        editButton.setBounds(50, 50, 150, 30);
        panel.add(editButton);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the EditQuestionPage when "Edit Question" button is clicked
                EditQuestionPage editQuestionPage = new EditQuestionPage(1);
                editQuestionPage.setVisible(true);
            }
        });

        JButton deleteButton = new JButton("Delete Question");
        deleteButton.setBounds(50, 100, 150, 30);
        panel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the DeleteQuestionPage when "Delete Question" button is clicked
                DeleteQuestionPage deleteQuestionPage = new DeleteQuestionPage();
                deleteQuestionPage.setVisible(true);
            }
        });

//        JButton viewButton = new JButton("View Question");
//        viewButton.setBounds(50, 150, 150, 30);
//        panel.add(viewButton);
//        viewButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Open the ViewQuestionPage when "View Question" button is clicked
//                ViewQuestionPage viewQuestionPage = new ViewQuestionPage();
//                viewQuestionPage.setVisible(true);
//            }
//        });

        JButton addButton = new JButton("Add Question");
        addButton.setBounds(50, 200, 150, 30);
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the AddQuestionPage when "Add Question" button is clicked
               // AddQuestionPage addQuestionPage = new AddQuestionPage();
                //addQuestionPage.setVisible(true);
            }
        });
    }
}

